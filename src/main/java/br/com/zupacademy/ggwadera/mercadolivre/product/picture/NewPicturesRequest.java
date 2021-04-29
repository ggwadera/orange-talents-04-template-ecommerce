package br.com.zupacademy.ggwadera.mercadolivre.product.picture;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class NewPicturesRequest {

    @NotEmpty
    private List<MultipartFile> pictures;

    public List<MultipartFile> getPictures() {
        return pictures;
    }

    public void setPictures(List<MultipartFile> pictures) {
        this.pictures = pictures;
    }
}
