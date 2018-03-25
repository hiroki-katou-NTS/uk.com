package nts.uk.ctx.sys.portal.infra.repository.standardwidget;

import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.StandardWidget;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.StandardWidgetRepository;
import nts.uk.ctx.sys.portal.infra.entity.standardwidget.CcgtmtStandardWidget;
import nts.uk.ctx.sys.portal.infra.entity.toppagepart.CcgmtTopPagePart;
@Stateless
public class JpaStandardWidgetRepository extends JpaRepository implements StandardWidgetRepository {

	
	private final String  SELECT_SINGLE = " SELECT d FROM CcgmtStandardWidget " 
										+ " WHERE d.ccgmtStandardWidgetPK.toppagePartID = :toppagePartID ";
	@Override
	public Optional<StandardWidget> getByID(String toppagePartID) {
		return this.queryProxy().query(SELECT_SINGLE , Object[].class)
				.setParameter("toppagePartID", toppagePartID)
				.getSingle( d -> joinObjectToDomain(d));
	}

	

/*	private final String  SELECT_ALL = " SELECT d FROM CcgmtStandardWidget " 
			+ " WHERE d.ccgmtStandardWidgetPK.toppagePartID = :toppagePartID ";
	@Override
	public List<StandardWidget> getByListID(String listToppagePartID) {
		// TODO Auto-generated method stub
		return this.queryProxy().query(SELECT_ALL , Object[].class)
				.setParameter("toppagePartID", listToppagePartID)
				.getList( c -> joinObjectToDomain(c));
	}
	*/
	private StandardWidget joinObjectToDomain(Object[] entity) {
		CcgtmtStandardWidget standardWidget = (CcgtmtStandardWidget) entity[0];
		CcgmtTopPagePart toppagePart = (CcgmtTopPagePart) entity[1];
		return  StandardWidget.createFromJavaType(
				toppagePart.ccgmtTopPagePartPK.companyID,
				standardWidget.ccgmtStandardWidgetPK.toppagePartID,
				toppagePart.code,
				toppagePart.name,
				toppagePart.topPagePartType,
				toppagePart.width,
				toppagePart.height);
	}


}
