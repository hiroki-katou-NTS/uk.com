package nts.uk.ctx.at.schedule.infra.repository.shift.specificdayset.company;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.specificdayset.company.KscmtSpecDateCom;
import nts.uk.ctx.at.schedule.infra.entity.shift.specificdayset.company.KscmtSpecDateComPK;

@Stateless
public class JpaCompanySpecificDateRepository extends JpaRepository implements CompanySpecificDateRepository {
	
	/**
	 * select a KscmtSpecDateCom ALL
	 */
	private static final String SELECT_NO_WHERE = "SELECT s FROM KscmtSpecDateCom s";
	
	/**
	 * select KscmtSpecDateCom by date
	 */
	private static final String GET_BY_DATE = SELECT_NO_WHERE 
			+ " WHERE s.kscmtSpecDateComPK.companyId = :companyId"
			+ " AND s.kscmtSpecDateComPK.specificDate = :specificDate";

	/**
	 * get List With Name of Specific
	 */
	private static final String GET_BY_USE_WITH_NAME = "SELECT p.name,p.useAtr, s FROM KscmtSpecDateCom s"
			+ " INNER JOIN KscmtSpecDateItem p ON p.kscmtSpecDateItemPK.itemNo = s.kscmtSpecDateComPK.specificDateItemNo AND p.kscmtSpecDateItemPK.companyId = s.kscmtSpecDateComPK.companyId "
			+ " WHERE s.kscmtSpecDateComPK.companyId = :companyId"
			+ " AND s.kscmtSpecDateComPK.specificDate >= :startYm"
			+ " AND s.kscmtSpecDateComPK.specificDate <= :endYm";
	
	/**
	 *Delete by Month 
	 */
	private static final String DELETE_BY_YEAR_MONTH = "DELETE from KscmtSpecDateCom c "
			+ " WHERE c.kscmtSpecDateComPK.companyId = :companyId"
			+ " AND c.kscmtSpecDateComPK.specificDate >= :startYm"
			+ " AND c.kscmtSpecDateComPK.specificDate <= :endYm";
	
	private static final String DELETE_BY_DATE = "DELETE FROM KscmtSpecDateCom c"
			+ " WHERE c.kscmtSpecDateComPK.companyId = :companyId"
			+ " AND c.kscmtSpecDateComPK.specificDate = :specificDate";
	

	/**
	 * Get list Company Specific Date NO with name
	 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<CompanySpecificDateItem> getComSpecByDate(String companyId, GeneralDate specificDate) {
		return this.queryProxy().query(GET_BY_DATE, KscmtSpecDateCom.class)
				.setParameter("companyId", companyId)
				.setParameter("specificDate", specificDate).getList(x -> toDomain(x));
	}

	/**
	 * Get list Company Specific Date  WITH name
	 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<CompanySpecificDateItem> getComSpecByDateWithName(String companyId, GeneralDate startDate, GeneralDate endDate) {
		return this.queryProxy().query(GET_BY_USE_WITH_NAME, Object[].class)
				.setParameter("companyId", companyId)
				.setParameter("startYm", startDate)
				.setParameter("endYm", endDate)
				.getList(x -> toDomainWithName(x));
	}

	/**
	 * convert entity to domain NO with Name
	 * @param entity
	 * @return
	 */
	private static CompanySpecificDateItem toDomain(KscmtSpecDateCom entity) {
		CompanySpecificDateItem domain = CompanySpecificDateItem.createFromJavaType(
				entity.kscmtSpecDateComPK.companyId, entity.kscmtSpecDateComPK.specificDate,
				entity.kscmtSpecDateComPK.specificDateItemNo, "");
		return domain;
	}

	/**
	 * convert entity to domain with Name
	 * @param object
	 * @return
	 */
	private static CompanySpecificDateItem toDomainWithName(Object[] object) {
		String specificDateItemName = (String) object[0];
		KscmtSpecDateCom entity = (KscmtSpecDateCom) object[2];
		CompanySpecificDateItem domain = CompanySpecificDateItem.createFromJavaType(
				entity.kscmtSpecDateComPK.companyId, entity.kscmtSpecDateComPK.specificDate,
				entity.kscmtSpecDateComPK.specificDateItemNo, specificDateItemName);
		return domain;
	}
	
	/**
	 * convert domain to entity NO with name
	 * @param domain
	 * @return
	 */
	private static KscmtSpecDateCom toEntity(CompanySpecificDateItem domain){
		val entity = new KscmtSpecDateCom();
		entity.kscmtSpecDateComPK = new KscmtSpecDateComPK(
				domain.getCompanyId(),
				domain.getSpecificDate(),
				domain.getSpecificDateItemNo().v());
		return entity;
	}
	/**
	 * INSERT process
	 */
	@Override
	public void InsertComSpecDate(List<CompanySpecificDateItem> lstComSpecDateItem) {
		List<KscmtSpecDateCom> lstEntity = new ArrayList<>();
		for(CompanySpecificDateItem comSpecDateItem : lstComSpecDateItem){
			lstEntity.add(toEntity(comSpecDateItem));
		}
		this.commandProxy().insertAll(lstEntity);
	}
	/**
	 * DELETE process
	 */
	@Override
	public void DeleteComSpecDate(String companyId, GeneralDate startDate, GeneralDate endDate) {
		this.getEntityManager().createQuery(DELETE_BY_YEAR_MONTH)
			.setParameter("companyId", companyId)
			.setParameter("startYm", startDate)
			.setParameter("endYm", endDate)
			.executeUpdate();
	}
	/**
	 * add List ComSpecDate
	 * @param lstComSpecDateItem
	 */
	@Override
	public void addListComSpecDate(List<CompanySpecificDateItem> lstComSpecDateItem) {
		List<KscmtSpecDateCom> lstEntity = new ArrayList<>();
		for (CompanySpecificDateItem specificDateItem : lstComSpecDateItem) {
			lstEntity.add(toEntity(specificDateItem));
		}
		this.commandProxy().insertAll(lstEntity);
	}
	/**
	 * delete ComSpecByDate
	 * @param companyId
	 * @param specificDate
	 */
	@Override
	public void deleteComSpecByDate(String companyId, GeneralDate specificDate) {
		this.getEntityManager().createQuery(DELETE_BY_DATE)
		.setParameter("companyId", companyId)
		.setParameter("specificDate", specificDate)
		.executeUpdate();
	}
}
