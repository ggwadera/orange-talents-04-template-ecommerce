package br.com.zupacademy.ggwadera.mercadolivre.product.picture;

import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Set;

public interface Uploader {

    Set<String> upload(Collection<MultipartFile> pictures);

}
