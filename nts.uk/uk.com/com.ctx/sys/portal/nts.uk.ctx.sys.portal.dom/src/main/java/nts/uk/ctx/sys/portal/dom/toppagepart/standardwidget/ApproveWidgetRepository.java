package nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget;

public interface ApproveWidgetRepository {

	StandardWidget findByWidgetTypeAndCompanyId(int standardWidgetType, String companyId);

	void updateApproveStatus(StandardWidget standardWidget, String companyId);
}
