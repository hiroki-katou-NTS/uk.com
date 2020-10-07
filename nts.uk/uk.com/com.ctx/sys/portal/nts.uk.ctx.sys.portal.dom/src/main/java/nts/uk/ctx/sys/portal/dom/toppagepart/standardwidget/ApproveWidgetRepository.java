package nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget;

public interface ApproveWidgetRepository {

	StandardWidget findByCompanyId(String companyId);

	void update(StandardWidget standardWidget, String companyId);
}
