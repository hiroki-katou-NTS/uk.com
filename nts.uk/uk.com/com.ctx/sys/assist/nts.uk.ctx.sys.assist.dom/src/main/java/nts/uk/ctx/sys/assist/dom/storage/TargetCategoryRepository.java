package nts.uk.ctx.sys.assist.dom.storage;

import java.util.List;
import java.util.Optional;

/**
* 対象カテゴリ
*/
public interface TargetCategoryRepository
{

    List<TargetCategory> getAllTargetCategory();

    Optional<TargetCategory> getTargetCategoryById(String storeProcessingId, String categoryId);
    
    List<TargetCategoryDto> getTargetCategoryListById(String storeProcessingId);

    void add(TargetCategory domain);

    void update(TargetCategory domain);

    void remove(String storeProcessingId, String categoryId);
}
