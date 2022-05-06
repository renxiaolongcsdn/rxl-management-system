package com.rxl.controller;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author ren.xiaolong
 * @date 2022/4/23
 * @Description 文件上传
 */
@RestController
@RequestMapping("/file")
@Api(tags="文件相关controller")
public class FileController {


    @Value("${file.upload}")
    String uploadPath;


    @PostMapping("/upload")
    @ApiOperation(value = "上传", notes = "上传", produces = MediaType.APPLICATION_JSON_VALUE)
    public R<String> upload(@RequestParam("file") MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        // 提取文件后缀名
        String extension = FilenameUtils.getExtension(originalFilename);
        // 重命名
        String newFileName = IdWorker.getIdStr();
        String objectName = newFileName + "." + extension;
        // 上传文件保存到的路径，根据实际情况修改，也可能是从配置文件获取到的文件存储路径
        String filePath = uploadPath;
        try {
            String destPath = filePath + File.separator + objectName;
            File dest = new File(destPath);
            file.transferTo(dest);
            return R.ok(objectName);
        } catch (Exception e) {
            throw new RuntimeException("上传失败");
        }
    }


    @PostMapping("/uploadFile")
    public void uploadFile(@RequestParam(value = "file",required = true) MultipartFile uploadFile, HttpServletRequest request) {
        File folder = new File("/usr/local/config/images");
        if (!folder.isDirectory()) {
            folder.mkdirs();
        }
        // 对上传的文件重命名，避免文件重名
        String oldName = uploadFile.getOriginalFilename();
        String newName = UUID.randomUUID().toString()
                + oldName.substring(oldName.lastIndexOf("."), oldName.length());
        try {
            // 文件保存
            uploadFile.transferTo(new File(folder, newName));

            // 返回上传文件的访问路径
            String filePath = request.getScheme() + "://" + request.getServerName()
                    + ":" + request.getServerPort() +uploadFile+ newName;

        } catch (IOException e) {
            throw new RuntimeException("错误");
        }

    }


    @PostMapping("/uploadImage")
    public String upload(MultipartFile uploadFile,
                         HttpServletRequest request) {

        // 在 uploadPath 文件夹中通过日期对上传的文件归类保存
        // 比如：/2019/06/06/cf13891e-4b95-4000-81eb-b6d70ae44930.png
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        String format = sdf.format(new Date());
        File folder = new File(uploadPath + format);
        if (!folder.isDirectory()) {
            folder.mkdirs();
        }

        // 对上传的文件重命名，避免文件重名
        String oldName = uploadFile.getOriginalFilename();
        String newName = UUID.randomUUID().toString()
                + oldName.substring(oldName.lastIndexOf("."), oldName.length());
        try {
            // 文件保存
            uploadFile.transferTo(new File(folder, newName));

            // 返回上传文件的访问路径
            String filePath = request.getScheme() + "://" + request.getServerName()
                    + ":" + request.getServerPort()  + format + newName;
            return filePath;
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }


}
