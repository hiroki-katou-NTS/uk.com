package nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget;

import java.util.List;
import java.util.Optional;

public interface OptionalWidgetRepository {

	List<OptionalWidget> findByCompanyId(String companyId);

	void add(OptionalWidget widget);

	void update(OptionalWidget widget);

	void remove(String companyID, String topPagePartID);

	Optional<OptionalWidget> findByCode(String companyID, String topPagePartID);

	List<WidgetDisplay> findAllWidget(String companyID, String widgetCode);

	void add(WidgetDisplay widget);

	void update(WidgetDisplay display);

	void removeWidget(String companyID, String widgetCode);


}
