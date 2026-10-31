package com.product.api.service;

import java.util.List;

<<<<<<< Updated upstream
import org.springframework.http.ResponseEntity;

=======
import com.product.api.dto.in.DtoCategoryIn;
>>>>>>> Stashed changes
import com.product.api.entity.Category;

public interface SvcCategory {

  public ResponseEntity<List<Category>> getCategories();

}