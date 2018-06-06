package nts.uk.ctx.sys.assist.dom.storage;

import java.util.Optional;
import java.util.List;

/**
* 対象カテゴリ
*/
public interface TargetCategoryRepository
{

    List<TargetCategory> getAllTargetCategory();

    Optional<TargetCategory> getTargetCategoryById(String storeProcessingId, String categoryId);
    
    List<TargetCategory> getTargetCategoryListById(String storeProcessingId);

    void add(List<TargetCategory> domain);

    void update(TargetCategory domain);

    void remove(String storeProcessingId, String categoryId);
}
