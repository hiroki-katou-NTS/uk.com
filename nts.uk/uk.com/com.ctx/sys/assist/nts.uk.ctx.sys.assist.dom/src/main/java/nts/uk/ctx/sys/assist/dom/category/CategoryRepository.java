package nts.uk.ctx.sys.assist.dom.category;

import java.util.Optional;
import java.util.List;

/**
* カテゴリ
*/
public interface CategoryRepository
{

    List<Category> getAllCategory();

    Optional<Category> getCategoryById();

    void add(Category domain);

    void update(Category domain);

    void remove();

	Optional<Category> getCategoryById(String categoryId);

	void remove(String categoryId);
	
	List<Category> findByAttendanceSystem(int systemType);
	List<Category> findByPaymentAvailability(int systemType);
	List<Category> findByPossibilitySystem(int systemType);
	List<Category> findBySchelperSystem(int systemType);

}
