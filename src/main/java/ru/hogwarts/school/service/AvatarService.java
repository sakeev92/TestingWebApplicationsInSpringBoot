package ru.hogwarts.school.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;



@Service
@RequiredArgsConstructor
public class AvatarService {
    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    AvatarRepository avatarRepository;
    StudentRepository studentRepository;


    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        Student student = studentRepository.findById(studentId).get();
        Path filePath = Path.of(avatarsDir, "ID =" + student.getId());
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, StandardOpenOption.CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 2048);
                BufferedOutputStream bos = new BufferedOutputStream(os, 2048);
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = avatarRepository.findByStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setData(avatarFile.getBytes());
        avatar.setMediaType(avatarFile.getContentType());
        student.setAvatar(avatar);
        studentRepository.save(student);
        avatarRepository.save(avatar);
    }

    public byte[] getAvatarFromDB(Long id) {

        Avatar avatar = getAvatar(id);
        return avatar.getData();
    }
    public String  getMediaType(Long id)
    {
        Avatar avatar = getAvatar(id);
        return avatar.getMediaType();
    }
    public Avatar getAvatar(Long id)
    {
        return avatarRepository.getReferenceById(id);
    }
}
