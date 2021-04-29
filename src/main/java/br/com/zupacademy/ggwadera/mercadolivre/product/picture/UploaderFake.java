package br.com.zupacademy.ggwadera.mercadolivre.product.picture;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UploaderFake implements Uploader {

    @Override
    public Set<String> upload(Collection<MultipartFile> pictures) {
        return pictures.stream()
            .map(picture -> "https://fake.site/" + UUID.randomUUID() + "_" + picture.getOriginalFilename())
            .collect(Collectors.toSet());
    }
}
