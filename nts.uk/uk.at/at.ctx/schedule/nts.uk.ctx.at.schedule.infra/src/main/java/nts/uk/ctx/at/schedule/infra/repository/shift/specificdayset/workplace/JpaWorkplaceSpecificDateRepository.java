package nts.uk.ctx.at.schedule.infra.repository.shift.specificdayset.workplace;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace.WorkplaceSpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace.WorkplaceSpecificDateRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.specificdayset.workplace.KscmtSpecDateWkp;
import nts.uk.ctx.at.schedule.infra.entity.shift.specificdayset.workplace.KscmtSpecDateWkpPK;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaWorkplaceSpecificDateRepository extends JpaRepository implements WorkplaceSpecificDateRepository {

	private static final String SELECT_NO_WHERE = "SELECT s FROM KscmtSpecDateWkp s";

	private static final String GET_BY_DATE = SELECT_NO_WHERE 
			+ " WHERE s.kscmtSpecDateWkpPK.workplaceId = :workplaceId"
			+ " AND s.kscmtSpecDateWkpPK.specificDate = :specificDate";

	// get List With Name of Specific
	private static final String GET_BY_USE_WITH_NAME = "SELECT p.name,p.useAtr, s FROM KscmtSpecDateWkp s"
			+ " LEFT JOIN KscmtSpecDateItem p ON s.kscmtSpecDateWkpPK.specificDateItemNo = p.kscmtSpecDateItemPK.itemNo "
			+ " WHERE s.kscmtSpecDateWkpPK.workplaceId = :workplaceId"
			+ " AND p.kscmtSpecDateItemPK.companyId = :companyID"
			+ " AND s.kscmtSpecDateWkpPK.specificDate >= :startYm"
			+ " AND s.kscmtSpecDateWkpPK.specificDate <= :endYm";
	
	//Delete by Month 
	
	private static final String DELETE_BY_YEAR_MONTH = "DELETE from KscmtSpecDateWkp c "
			+ " WHERE c.kscmtSpecDateWkpPK.workplaceId = :workplaceId"
			+ " AND c.kscmtSpecDateWkpPK.specificDate >= :startYm"
			+ " AND c.kscmtSpecDateWkpPK.specificDate <= :endYm";
	
	private static final String DELETE_BY_DATE = "DELETE FROM KscmtSpecDateWkp c"
			+ " WHERE c.kscmtSpecDateWkpPK.workplaceId = :workplaceId"
			+ " AND c.kscmtSpecDateWkpPK.specificDate = :specificDate";

	// No WITH name
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<WorkplaceSpecificDateItem> getWorkplaceSpecByDate(String workplaceId, GeneralDate specificDate) {
		return this.queryProxy().query(GET_BY_DATE, KscmtSpecDateWkp.class)
				.setParameter("workplaceId", workplaceId)
				.setParameter("specificDate", specificDate)
				.getList(x -> toDomain(x));
	}

	// WITH name
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<WorkplaceSpecificDateItem> getWpSpecByDateWithName(String workplaceId, GeneralDate startDate, GeneralDate endDate) {
		String companyID = AppContexts.user().companyId();
		return this.queryProxy().query(GET_BY_USE_WITH_NAME, Object[].class)
				.setParameter("workplaceId", workplaceId)
				.setParameter("companyID", companyID)
				.setParameter("startYm", startDate)
				.setParameter("endYm", endDate)
				.getList(x -> toDomainWithName(x));
	}

	// No with name
	private static WorkplaceSpecificDateItem toDomain(KscmtSpecDateWkp entity) {
		WorkplaceSpecificDateItem domain = WorkplaceSpecificDateItem.createFromJavaType(
				entity.kscmtSpecDateWkpPK.workplaceId, entity.kscmtSpecDateWkpPK.specificDate,
				entity.kscmtSpecDateWkpPK.specificDateItemNo, "");
		return domain;
	}

	// With NAME
	private static WorkplaceSpecificDateItem toDomainWithName(Object[] object) {
		String specificDateItemName = (String) object[0];
		KscmtSpecDateWkp entity = (KscmtSpecDateWkp) object[2];
		WorkplaceSpecificDateItem domain = WorkplaceSpecificDateItem.createFromJavaType(
				entity.kscmtSpecDateWkpPK.workplaceId, 
				entity.kscmtSpecDateWkpPK.specificDate,
				entity.kscmtSpecDateWkpPK.specificDateItemNo, 
				specificDateItemName);
		return domain;
	}
	
	//No with Name 
	private static KscmtSpecDateWkp toEntity(WorkplaceSpecificDateItem domain){
		val entity = new KscmtSpecDateWkp();
		entity.kscmtSpecDateWkpPK = new KscmtSpecDateWkpPK(
				domain.getWorkplaceId(),
				domain.getSpecificDate(),
				domain.getSpecificDateItemNo().v());
		return entity;
	}
	// No with name 
	@Override
	public void InsertWpSpecDate(List<WorkplaceSpecificDateItem> lstWpSpecDateItem) {
		List<KscmtSpecDateWkp> lstEntity = new ArrayList<>();
		for(WorkplaceSpecificDateItem wpSpecDateItem : lstWpSpecDateItem){
			lstEntity.add(toEntity(wpSpecDateItem));
		}
		this.commandProxy().insertAll(lstEntity);
	}

	
	@Override
	public void DeleteWpSpecDate(String workplaceId, GeneralDate startDate, GeneralDate endDate) {
		this.getEntityManager().createQuery(DELETE_BY_YEAR_MONTH)
			.setParameter("workplaceId", workplaceId)
			.setParameter("startYm", startDate)
			.setParameter("endYm", endDate)
			.executeUpdate();
	}

	/**
	 * delete WorkplaceSpec
	 * @param workplaceId
	 * @param specificDate
	 */
	@Override
	public void deleteWorkplaceSpec(String workplaceId, GeneralDate specificDate) {
		this.getEntityManager().createQuery(DELETE_BY_DATE)
				.setParameter("workplaceId", workplaceId)
				.setParameter("specificDate", specificDate)
				.executeUpdate();
	}
	
}
