package nts.uk.ctx.sys.assist.dom.deletedata;

import java.util.List;
import java.util.Optional;

/**
 * 対象カテゴリ
 */
public interface CategoryDeletionRepository {

	List<CategoryDeletion> getAllCategoryDeletion();

	Optional<CategoryDeletion> getCategoryDeletionById(String delId, String categoryId);

	void add(CategoryDeletion domain);

	void update(CategoryDeletion domain);

	void remove(String delId, String categoryId);

	/**
	 * @param Category
	 * @author hiep.th
	 */
	void addAll(List<CategoryDeletion> Category);

	List<CategoryDeletion> getCategoryDeletionListById(String delId);
}
