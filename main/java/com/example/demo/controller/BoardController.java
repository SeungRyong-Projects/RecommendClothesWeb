package com.example.demo.controller;

import com.example.demo.file.FileMapService;
import com.example.demo.file.UploadFileService;
import com.example.demo.model.*;
import com.example.demo.payload.response.ApiResponse;
import com.example.demo.service.BoardService;
import com.example.demo.util.MediaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@Slf4j
@RequestMapping("/board")
public class BoardController {
    private BoardService boardService;
    private UploadFileService uploadFileService;
    private FileMapService fileMapService;

    public BoardController(BoardService boardService, UploadFileService uploadFileService, FileMapService fileMapService) {
        this.boardService = boardService;
        this.uploadFileService = uploadFileService;
        this.fileMapService = fileMapService;
    }

    @RequestMapping("/list")
    public String boardList(
            @ModelAttribute SearchHelper searchHelper,
            Model model
    ) throws Exception {


        HashMap<String, Object> result = boardService.selectBoardVO(searchHelper);
        log.info(searchHelper.toString());
        model.addAttribute("searchHelper", result.get("searchHelper"));
        model.addAttribute("result", result.get("list"));

        log.info("result -{}", result);
        return "board/list";
    }

    @RequestMapping("/view")
    public String boardView(
            @RequestParam(value = "id", defaultValue = "") int id,
            @ModelAttribute SearchHelper searchHelper,
            Model model
    ) throws Exception {

        if (id > 0) {
            //게시물 조회
            Optional<BoardVO> boardVO = Optional.ofNullable(boardService.selectBoardVOById(id));
            model.addAttribute("searchHelper", searchHelper);
            model.addAttribute("boardVO", boardVO.get());

            if (boardVO.isPresent()) {
                List<UploadFileVO> fileList = uploadFileService.selectFileByBoardId(boardVO.get().getId());
                model.addAttribute("uploadFileVO", fileList);
            } else {
                model.addAttribute("uploadFileVO", null);
            }


        } else {
            Message message = new Message();
            message.setMessage("게시물이 없습니다.");
            message.setHref("/board/list");
            model.addAttribute("data", message);
            model.addAttribute("searchHelper", searchHelper);
            return "message/message";
        }

        return "board/view";
    }

    @RequestMapping("/write")
    public String boardWrite(
            @ModelAttribute SearchHelper searchHelper,
            Model model
    ) {
        BoardVO boardVO = new BoardVO();
        boardVO.setCode(1000);
        model.addAttribute("boardVO", boardVO);
        model.addAttribute("searchHelper", searchHelper);
        return "board/write";
    }

    @RequestMapping("/modify")
    public String boardModify(
            @RequestParam(value = "id", defaultValue = "") int id,
            @ModelAttribute SearchHelper searchHelper,
            Model model
    ) throws Exception {

        if (id > 0) {
            //게시물 조회
            Optional<BoardVO> boardVO = Optional.ofNullable(boardService.selectBoardVOById(id));
            model.addAttribute("searchHelper", searchHelper);
            model.addAttribute("boardVO", boardVO.get());

            if (boardVO.isPresent()) {
                List<UploadFileVO> fileList = uploadFileService.selectFileByBoardId(boardVO.get().getId());
                model.addAttribute("uploadFileVO", fileList);
            } else {
                model.addAttribute("uploadFileVO", null);
            }
        } else {
            Message message = new Message();
            message.setMessage("게시물이 없습니다.");
            message.setHref("/board/list?srchCode=" + searchHelper.getSrchCode() + "&srchType=" + searchHelper.getSrchType() + "&srchKeyword=" + searchHelper.getSrchKeyword());
            model.addAttribute("data", message);
            return "message/message";
        }

        return "board/write";
    }

    @RequestMapping("/save")
    public String boardSave(
            @ModelAttribute BoardVO boardVO,
            @RequestParam("file") List<MultipartFile> multipartFile,
            HttpServletRequest request,
            Model model
    ) throws Exception {

        HttpSession session = request.getSession();
        MemberVO sessionResult = (MemberVO) session.getAttribute("memberVO");
        log.info("boardVO123 -{}", boardVO);

        if(sessionResult != null) {
            //저장
            String userId = sessionResult.getUserId();
            // code, title, content, userId
            boardVO.setRegId(userId);

            if(boardVO.getId() > 0) {
                //수정
                String regId = boardVO.getRegId();
                boardService.updateBoardVO(boardVO);
            } else {
                //저장
                boardService.insertBoardVO(boardVO);
//                int insertedId = boardVO.getId();
            }

            for(int i = 0; i < multipartFile.size(); i++) {
                UploadFileVO uploadFileVO = uploadFileService.store(multipartFile.get(i));
                log.info("uploadFileVO -{}", uploadFileVO);
                FileMapVO fileMapVO = new FileMapVO();
                if(uploadFileVO != null) {
                    fileMapVO.setFileId(uploadFileVO.getId());
                    fileMapVO.setBoardId(boardVO.getId());
                    fileMapService.insertFileMap(fileMapVO);
                }
            }
        } else {
            //세션 없음
            model.addAttribute("data", new Message("로그인 후 이용하세요.", "/member/login"));
            return "message/message";
        }


        return "redirect:/board/list";
    }

    /*
     * 배열 형태로 게시물 삭제
     * */
    @PostMapping("/delete")
    public String delete(
            @RequestParam(value = "del[]", defaultValue = "") int[] del,
            Model model
    ) throws Exception {

        log.info("삭제 배열 -{}", del);
        Message message = new Message();
        message.setHref("/board/list");

        if(del.length > 0) {
            for (int i = 0; i < del.length; i++) {
                boardService.deleteById(del[i]);
            }
            message.setMessage("삭제되었습니다");
        } else {
            message.setMessage("삭제할 게시물을 선택하세요.");
        }

        model.addAttribute("data", message);
        return "message/message";
    }

    @PostMapping("/deleteView")
    public String deleteView(
            @PathVariable int id,
            Model model
    ) throws Exception {
        Message message = new Message();
        message.setHref("/board/list");

        boardService.deleteById(id);
        message.setMessage("삭제되었습니다.");
        return "message/message";
    }

    /*파일 업로드*/
    @PostMapping("/boardSaveTest")
    public String boardSaveTest(@RequestParam("file")MultipartFile multipartFile) throws IOException {

        log.info(multipartFile.getName());
        log.info(multipartFile.getContentType());
        log.info(multipartFile.getOriginalFilename());
        log.info(String.valueOf(multipartFile.getSize()));

        Path path = Paths.get("C:/upload_file/").toAbsolutePath().normalize();
        Files.createDirectories(path);

        String generateFileName = UUID.randomUUID().toString();
        generateFileName = generateFileName.replace("-","");
        log.info(generateFileName);

        int dot = multipartFile.getOriginalFilename().lastIndexOf(".");
        log.info("dot {}", dot);

        String extention = "";
        if(dot != -1 & multipartFile.getOriginalFilename().length() - 1 > dot) {
            extention = multipartFile.getOriginalFilename().substring(dot + 1);
        }

        log.info("확장명 -{}", extention);

        File file = new File("C:/upload_file/" + generateFileName + "." + extention);
        multipartFile.transferTo(file); //저장

        return "/message/test";
    }


    /*파일 다운로드*/
    @GetMapping("/file/download")
    @ResponseBody
    public ResponseEntity fileDownload(
            @RequestParam(value = "name", defaultValue = "") String name
    ) throws UnsupportedEncodingException, MalformedURLException {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + new String(name.getBytes("UTF-8"), "ISO-8859-1") + "\"");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        Path path = Paths.get("C:/upload_file/" + name).toAbsolutePath().normalize();
        log.info(String.valueOf(path));
        Resource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok().headers(headers).body(resource);
    }

    /*파일 보기*/
    @GetMapping("/file/{fileId}")
    @ResponseBody
    public ResponseEntity showFile(
            @PathVariable int fileId
    ) {
        try {
            UploadFileVO uploadFileVO = uploadFileService.load(fileId);

            if (uploadFileVO == null) return ResponseEntity.badRequest().build();

            String fileName = uploadFileVO.getFileName();

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");

            if (MediaUtil.containsMediaType(uploadFileVO.getContentType())) {
                headers.setContentType(MediaType.valueOf(uploadFileVO.getContentType()));
            } else {
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            }

            Resource resource = uploadFileService.loadAsResource(uploadFileVO.getSaveFileName());

            return ResponseEntity.ok().headers(headers).body(resource);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /*파일삭제*/
    @GetMapping("/deleteFile/{fileId}")
    @ResponseBody
    public ApiResponse deleteFile(@PathVariable int fileId) {
        Boolean result = uploadFileService.deleteFileById(fileId);
        if(result) {
            return new ApiResponse(true, "삭제 완료");
        } else {
            return new ApiResponse(false, "삭제 오류");
        }

    }
}
