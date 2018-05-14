package nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget;

import java.util.List;
import java.util.Optional;

public interface OptionalWidgetRepository {

	List<OptionalWidget> findByCompanyId(String companyId);

	void add(OptionalWidget widget);

	void update(OptionalWidget widget);

	void remove(String companyID, String topPagePartID, List<Integer> displayItemTypes);

	Optional<OptionalWidget> findByCode(String companyID, String topPagePartID);

	boolean isExist(String companyId, String code, int type);
	
	List<OptionalWidget> findByCode(String companyId, List<String> listOptionalWidgetID);

	Optional<OptionalWidget> getSelectedWidget(String companyId, String topPagePartCode);
}
