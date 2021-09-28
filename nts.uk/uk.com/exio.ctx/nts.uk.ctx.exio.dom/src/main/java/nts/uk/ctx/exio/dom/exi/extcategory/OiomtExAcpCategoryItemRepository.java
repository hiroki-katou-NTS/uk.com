package nts.uk.ctx.exio.dom.exi.extcategory;

import java.util.List;

public interface OiomtExAcpCategoryItemRepository {
	List<ExternalAcceptCategoryItem> getByCategory(int categoryId);
}
