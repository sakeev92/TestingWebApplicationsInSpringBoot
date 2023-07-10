package ru.hogwarts.school.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Target;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;


@RestController
@RequestMapping("/avatar")
@AllArgsConstructor
@RequiredArgsConstructor
public class AvatarController {

    private AvatarService service;

    @PostMapping(value = "/{studentId}/uploadAvatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long studentId, @RequestParam MultipartFile avatarFile) throws IOException
    {
        service.uploadAvatar(studentId, avatarFile);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{id}/getAvatarFromDB")
    public ResponseEntity<byte[]> getAvatarFromDB(@PathVariable Long id)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(service.getMediaType(id)));
        headers.setContentLength(service.getAvatarFromDB(id).length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(service.getAvatarFromDB(id));
    }
    @GetMapping(value = "/{id}/avatar-from-file")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException{
        Avatar avatar = service.getAvatar(id);
        Path path = Path.of(avatar.getFilePath());
        try(InputStream is = Files.newInputStream(path);
            OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }
}
