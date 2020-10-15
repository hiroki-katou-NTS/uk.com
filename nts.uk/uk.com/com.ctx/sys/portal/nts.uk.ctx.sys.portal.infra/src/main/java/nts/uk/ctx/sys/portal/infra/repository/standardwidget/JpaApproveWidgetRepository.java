package nts.uk.ctx.sys.portal.infra.repository.standardwidget;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApproveWidgetRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.StandardWidget;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.StandardWidgetType;
import nts.uk.ctx.sys.portal.infra.entity.standardwidget.SptmtApproveWidget;

@Stateless
public class JpaApproveWidgetRepository extends JpaRepository implements ApproveWidgetRepository {

	private static final String FIND_BY_APPROVE_STATUS = "SELECT a from SptmtApproveWidget a WHERE a.companyId =:companyId";

	@Override
	public StandardWidget findByWidgetTypeAndCompanyId(int standardWidgetType, String companyId) {
		
		if (standardWidgetType == StandardWidgetType.APPROVE_STATUS.value) {
			return this.queryProxy().query(FIND_BY_APPROVE_STATUS, SptmtApproveWidget.class)
					.setParameter("companyId", companyId).getSingle().map(SptmtApproveWidget::toDomain).orElse(null);
		} else if (standardWidgetType == StandardWidgetType.APPLICATION_STATUS.value) {

		} else if (standardWidgetType == StandardWidgetType.WORK_STATUS.value) {

		}
		return null;
	}

	@Override
	public void updateApproveStatus(StandardWidget standardWidget, String companyId) {

		Optional<SptmtApproveWidget> sptmtApproveWidget = this.queryProxy()
				.query(FIND_BY_APPROVE_STATUS, SptmtApproveWidget.class).setParameter("companyId", companyId).getSingle();

		if (sptmtApproveWidget.isPresent()) {
			SptmtApproveWidget approveWidgetEntity = sptmtApproveWidget.get();

			SptmtApproveWidget.toEntity(approveWidgetEntity, standardWidget);
			
			this.commandProxy().update(approveWidgetEntity);
		}
	}
}
