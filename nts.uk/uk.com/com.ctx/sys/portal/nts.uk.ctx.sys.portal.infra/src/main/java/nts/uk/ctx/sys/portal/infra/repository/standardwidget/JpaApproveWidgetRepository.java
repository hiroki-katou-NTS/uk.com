package nts.uk.ctx.sys.portal.infra.repository.standardwidget;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApproveWidgetRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.StandardWidget;
import nts.uk.ctx.sys.portal.infra.entity.standardwidget.SptmtApproveWidget;

@Stateless
public class JpaApproveWidgetRepository extends JpaRepository implements ApproveWidgetRepository {

	private static final String FIND_BY_COMPANY = "SELECT a from SptmtApproveWidget a WHERE a.companyId =:companyId";

	@Override
	public StandardWidget findByCompanyId(String companyId) {
		return this.queryProxy().query(FIND_BY_COMPANY, SptmtApproveWidget.class).setParameter("companyId", companyId)
				.getSingle().map(SptmtApproveWidget::toDomain).orElse(null);
	}

}
