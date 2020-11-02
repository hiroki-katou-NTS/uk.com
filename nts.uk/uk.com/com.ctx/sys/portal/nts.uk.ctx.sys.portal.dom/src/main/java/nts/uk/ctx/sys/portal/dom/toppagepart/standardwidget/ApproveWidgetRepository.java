package nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget;

import java.util.Optional;

public interface ApproveWidgetRepository {

	//指定するウィジェットの設定を取得する
	Optional<StandardWidget> findByWidgetTypeAndCompanyId(StandardWidgetType standardWidgetType, String companyId);

	void updateApproveStatus(StandardWidget standardWidget, String companyId);
}
