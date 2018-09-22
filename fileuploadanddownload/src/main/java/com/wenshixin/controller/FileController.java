package com.wenshixin.controller;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: wzw
 * Date: 2018/9/20
 * Time: 19:05
 * To change this template use File | Settings | File Templates.
 * Description:
 */
@Controller
public class FileController {

    @GetMapping(value = "/fileupload")
    public String toFileUpload() {
        return "fileupload";
    }

    @PostMapping(value = "/fileupload")
    public String fileUpload(@RequestParam(value = "file") List<MultipartFile> files, HttpServletRequest request) {
        String msg = "";
        // 判断文件是否上传
        if (!files.isEmpty()) {
            // 设置上传文件的保存目录
            String basePath = request.getServletContext().getRealPath("/upload/");
            // 判断文件目录是否存在
            File uploadFile = new File(basePath);
            if (!uploadFile.exists()) {
                uploadFile.mkdirs();
            }
            for (MultipartFile file : files) {
                String originalFilename = file.getOriginalFilename();
                if (originalFilename != null && !originalFilename.equals("")) {
                    try {
                        // 对文件名做加UUID值处理
                        originalFilename = UUID.randomUUID() + "_" + originalFilename;
                        file.transferTo(new File(basePath + originalFilename));
                    } catch (IOException e) {
                        e.printStackTrace();
                        msg = "文件上传失败！";
                    }
                } else {
                    msg = "上传的文件为空！";
                }
            }
            msg = "文件上传成功！";
        } else {
            msg = "没有文件被上传！";
        }
        request.setAttribute("msg", msg);
        return "fileupload";
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handException(MaxUploadSizeExceededException e, HttpServletRequest request) {
//        System.out.println("我捕获了异常");
        request.setAttribute("msg", "文件超过了指定大小，上传失败！");
        return "fileupload";
    }


    @RequestMapping(value = "/filedownload")
    public String toFileDownload(HttpServletRequest request) {
        return "filedownload";
    }

    @RequestMapping(value = "/fileList")
    @ResponseBody
    public String fileList(HttpServletRequest request) {
        String baseDir = request.getServletContext().getRealPath("/upload");
        File baseFile = new File(baseDir);
        List<String> fileList = null;
        if (baseFile.exists()) {
            File[] files = baseFile.listFiles();
            fileList = new ArrayList<>(files.length);
            for (File file : files) {
                fileList.add(file.getName());
            }
        }
//        System.out.println(fileList.toString());
        String json = JSON.toJSONString(fileList);
        return json;
    }


    @RequestMapping(value = "/download")
    public ResponseEntity<byte[]> fileDownload(String filename, HttpServletRequest request) throws IOException {
        String path = request.getServletContext().getRealPath("/upload/");
        File file = new File(path + filename);
        System.out.println("转码前" + filename);
        filename = this.getFilename(request, filename);
        System.out.println("转码后" + filename);
        // 设置响应头通知浏览器下载
        HttpHeaders headers = new HttpHeaders();
        // 将对文件做的特殊处理还原
        filename = filename.substring(filename.indexOf("_") + 1);
        headers.setContentDispositionFormData("attachment", filename);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
    }

    // 根据不同的浏览器进行编码设置，返回编码后的文件名
    public String getFilename(HttpServletRequest request, String filename) throws UnsupportedEncodingException {
        String[] IEBrowerKeyWords = {"MSIE", "Trident", "Edge"};
        String userAgent = request.getHeader("User-Agent");
        for (String keyword : IEBrowerKeyWords) {
            if (userAgent.contains(keyword)) {
                return URLEncoder.encode(filename, "UTF-8");
            }
        }
        return new String(filename.getBytes("UTF-8"), "ISO-8859-1");
    }
}
