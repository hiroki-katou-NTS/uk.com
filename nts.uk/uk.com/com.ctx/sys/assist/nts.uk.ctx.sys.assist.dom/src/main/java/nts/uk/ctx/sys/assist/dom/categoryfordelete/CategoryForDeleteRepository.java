package nts.uk.ctx.sys.assist.dom.categoryfordelete;

import java.util.List;
import java.util.Optional;

/**
* データ削除カテゴリ
*/
public interface CategoryForDeleteRepository
{

    List<CategoryForDelete> getAllCategory();

    Optional<CategoryForDelete> getCategoryById();

    void add(CategoryForDelete domain);

    void update(CategoryForDelete domain);

    void remove();

	Optional<CategoryForDelete> getCategoryById(String categoryId);
	
	List<CategoryForDelete> getCategoryByListId(List<String> categoryIds);
	
	void remove(String categoryId);
	
	List<CategoryForDelete> findByAttendanceSystem();
	List<CategoryForDelete> findByPaymentAvailability();
	List<CategoryForDelete> findByPossibilitySystem();
	List<CategoryForDelete> findBySchelperSystem();
	List<CategoryForDelete> findByAttendanceSystemAndCodeName(String keySearch,List<String> categoriesIgnore);
	List<CategoryForDelete> findByPaymentAvailabilityAndCodeName(String keySearch,List<String> categoriesIgnore);
	List<CategoryForDelete> findByPossibilitySystemAndCodeName(String keySearch,List<String> categoriesIgnore);
	List<CategoryForDelete> findBySchelperSystemAndCodeName(String keySearch,List<String> categoriesIgnore);
	List<CategoryForDelete> findById(String storeProcessingId, int selectionTargetForRes);
}
