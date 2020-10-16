package nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget;

public interface ApproveWidgetRepository {

	//指定するウィジェットの設定を取得する
	StandardWidget findByWidgetTypeAndCompanyId(int standardWidgetType, String companyId);

	void updateApproveStatus(StandardWidget standardWidget, String companyId);
}
