package nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget;

import java.util.Optional;

public interface ApproveWidgetRepository {

	// 指定するウィジェットの設定を取得する
	StandardWidget findByWidgetTypeAndCompanyId(int standardWidgetType, String companyId);

	void updateApproveStatus(StandardWidget standardWidget, String companyId);

	void updateAppStatus(StandardWidget standardWidget);

	Optional<StandardWidget> findByWidgetType(int standardWidgetType, String companyId);
	
	void addAppStatus(StandardWidget standardWidget);
}
