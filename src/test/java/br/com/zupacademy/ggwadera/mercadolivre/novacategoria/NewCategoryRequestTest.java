package br.com.zupacademy.ggwadera.mercadolivre.novacategoria;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class NewCategoryRequestTest {

    private final EntityManager manager = mock(EntityManager.class);

    @Test
    @DisplayName("deve criar categoria somente com o nome")
    void toModelOnlyWithNameTest() {
        NewCategoryRequest request = new NewCategoryRequest();
        request.setName("Celular");
        request.toModel(manager);
        verify(manager, never()).find(eq(Category.class), anyLong());
    }

    @Test
    @DisplayName("deve criar categoria com o nome e categoria mãe")
    void toModelOnlyNameAndParentTest() {
        NewCategoryRequest request = new NewCategoryRequest();
        request.setName("Celular");
        request.setParentCategoryId(1L);

        when(manager.find(Category.class, 1L)).thenReturn(new Category("Mãe"));

        request.toModel(manager);
        verify(manager, times(1)).find(eq(Category.class), anyLong());
    }
}