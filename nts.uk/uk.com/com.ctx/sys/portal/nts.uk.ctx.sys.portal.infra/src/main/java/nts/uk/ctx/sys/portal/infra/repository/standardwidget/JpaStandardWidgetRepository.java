package nts.uk.ctx.sys.portal.infra.repository.standardwidget;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.StandardWidget;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.StandardWidgetRepository;
import nts.uk.ctx.sys.portal.infra.entity.standardwidget.SptstStandardWidget;
import nts.uk.ctx.sys.portal.infra.entity.toppagepart.CcgmtTopPagePart;
@Stateless
public class JpaStandardWidgetRepository extends JpaRepository implements StandardWidgetRepository {

	
	private final String  SELECT_ALL = " SELECT s, t FROM SptstStandardWidget s, CcgmtTopPagePart t " 
										+ " WHERE s.sptstStandardWidgetPK.toppagePartID = t.ccgmtTopPagePartPK.topPagePartID";

	private final String  SELECT_BY_ID = SELECT_ALL + "AND s.sptstStandardWidgetPK.toppagePartID =:toppagePartID";
	
	@Override
	public List<StandardWidget> getAll(){
		return this.queryProxy().query(SELECT_ALL , Object[].class)
				.getList(c -> joinObjectToDomain(c));
	}

	@Override
	public Optional<StandardWidget> getByID(String ToppagePartID) {
		return this.queryProxy().query(SELECT_BY_ID , Object[].class)
				.setParameter("toppagePartID", ToppagePartID)
				.getSingle(c -> joinObjectToDomain(c));
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
		SptstStandardWidget standardWidget = (SptstStandardWidget) entity[0];
		CcgmtTopPagePart toppagePart = (CcgmtTopPagePart) entity[1];
		return  StandardWidget.createFromJavaType(
				toppagePart.ccgmtTopPagePartPK.companyID,
				standardWidget.sptstStandardWidgetPK.toppagePartID,
				toppagePart.code,
				toppagePart.name,
				toppagePart.topPagePartType,
				toppagePart.width,
				toppagePart.height);
	}



	


}
