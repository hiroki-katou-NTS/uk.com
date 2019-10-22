package nts.uk.ctx.at.function.infra.repository.dailyperformanceformat;

import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.BusinessTypeSFormatDaily;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.BusinessTypeSFormatDailyRepository;
import nts.uk.ctx.at.function.infra.entity.dailyperformanceformat.KrcmtBusinessTypeSDaily;
import nts.uk.ctx.at.function.infra.entity.dailyperformanceformat.KrcmtBusinessTypeSDailyPK;
/**
 * 
 * @author anhdt
 *
 */

@Stateless
public class JpaBusinessTypeSFormatDailyRepository extends JpaRepository implements BusinessTypeSFormatDailyRepository {

	private static final String FIND;

	private static final String FIND_DETAIl;

	private static final String UPDATE_BY_KEY;

	private static final String REMOVE_EXIST_DATA;
	
	private static final String REMOVE_EXIST_DATA_BY_CODE;
	
	private static final String IS_EXIST_DATA;

	private static final String FIND_BY_COMPANYID;
	
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcmtBusinessTypeSDaily a ");
		builderString.append("WHERE a.krcmtBusinessTypeMobileDailyPK.companyId = :companyId ");
		builderString.append("AND a.krcmtBusinessTypeMobileDailyPK.businessTypeCode = :businessTypeCode ");
		FIND = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcmtBusinessTypeSDaily a ");
		builderString.append("WHERE a.krcmtBusinessTypeMobileDailyPK.companyId = :companyId ");
		builderString.append("AND a.krcmtBusinessTypeMobileDailyPK.businessTypeCode = :businessTypeCode ");
		FIND_DETAIl = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("UPDATE KrcmtBusinessTypeSDaily a ");
		builderString.append("SET a.order = :order ");
		builderString.append("WHERE a.krcmtBusinessTypeMobileDailyPK.companyId = :companyId ");
		builderString.append("AND a.krcmtBusinessTypeMobileDailyPK.businessTypeCode = :businessTypeCode ");
		builderString.append("AND a.krcmtBusinessTypeMobileDailyPK.attendanceItemId = :attendanceItemId ");
		UPDATE_BY_KEY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcmtBusinessTypeSDaily a ");
		builderString.append("WHERE a.krcmtBusinessTypeMobileDailyPK.companyId = :companyId ");
		builderString.append("AND a.krcmtBusinessTypeMobileDailyPK.businessTypeCode = :businessTypeCode ");
		builderString.append("AND a.krcmtBusinessTypeMobileDailyPK.attendanceItemId IN :attendanceItemIds ");
		REMOVE_EXIST_DATA_BY_CODE = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcmtBusinessTypeSDaily a ");
		builderString.append("WHERE a.krcmtBusinessTypeMobileDailyPK.attendanceItemId IN :attendanceItemIds ");
		REMOVE_EXIST_DATA = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT COUNT(a) ");
		builderString.append("FROM KrcmtBusinessTypeSDaily a ");
		builderString.append("WHERE a.krcmtBusinessTypeMobileDailyPK.companyId = :companyId ");
		builderString.append("AND a.krcmtBusinessTypeMobileDailyPK.businessTypeCode = :businessTypeCode ");
		IS_EXIST_DATA = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcmtBusinessTypeSDaily a ");
		builderString.append("WHERE a.krcmtBusinessTypeMobileDailyPK.companyId = :companyId ");
		FIND_BY_COMPANYID = builderString.toString();
	}
	/**
	 * Add BusinessTypeMobileFormatDaily
	 */
	@Override
	public List<BusinessTypeSFormatDaily> getBusinessTypeFormat(String companyId, String businessTypeCode) {
		return this.queryProxy().query(FIND, KrcmtBusinessTypeSDaily.class)
				.setParameter("companyId", companyId)
				.setParameter("businessTypeCode", businessTypeCode)
				.getList(f -> toDomain(f));
	}
	
	/**
	 * Get BusinessTypeMobileFormatDaily
	 */
	@Override
	public List<BusinessTypeSFormatDaily> getBusinessTypeFormatDailyDetail(String companyId, String businessTypeCode) {
		return this.queryProxy().query(FIND_DETAIl, KrcmtBusinessTypeSDaily.class)
				.setParameter("companyId", companyId)
				.setParameter("businessTypeCode", businessTypeCode)
				.getList(f -> toDomain(f));
	}
	
	/**
	 * Delete  BusinessTypeMobileFormatDaily
	 */
	@Override
	public void deleteExistData(List<Integer> attendanceItemIds) {
		CollectionUtil.split(attendanceItemIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			this.getEntityManager().createQuery(REMOVE_EXIST_DATA)
				.setParameter("attendanceItemIds", subList)
				.executeUpdate();
		});
	}
	/**
	 * Add  BusinessTypeMobileFormatDaily
	 */
	@Override
	public void add(List<BusinessTypeSFormatDaily> businessTypeFormatDailies) {
		businessTypeFormatDailies.forEach(f -> this.commandProxy().insert(toEntity(f)));
	}
	
	/**
	 * Update  BusinessTypeMobileFormatDaily
	 */
	@Override
	public void update(BusinessTypeSFormatDaily businessTypeFormatDaily) {
		this.getEntityManager().createQuery(UPDATE_BY_KEY)
				.setParameter("companyId", businessTypeFormatDaily.getCompanyId())
				.setParameter("businessTypeCode", businessTypeFormatDaily.getBusinessTypeCode().v())
				.setParameter("attendanceItemId", businessTypeFormatDaily.getAttendanceItemId())
				.setParameter("order", businessTypeFormatDaily.getOrder())
				.executeUpdate();
	}
	
	/**
	 * Check  BusinessTypeMobileFormatDaily
	 */
	@Override
	public boolean checkExistData(String companyId, String businessTypeCode) {
		return this.queryProxy().query(IS_EXIST_DATA, long.class).setParameter("companyId", companyId)
				.setParameter("businessTypeCode", businessTypeCode)
				.getSingle()
				.get() > 0;
	}

	private static BusinessTypeSFormatDaily toDomain(KrcmtBusinessTypeSDaily krcmtBusinessTypeDaily) {
		BusinessTypeSFormatDaily workTypeFormatDaily = BusinessTypeSFormatDaily.createFromJavaType(
				krcmtBusinessTypeDaily.krcmtBusinessTypeMobileDailyPK.companyId,
				krcmtBusinessTypeDaily.krcmtBusinessTypeMobileDailyPK.businessTypeCode,
				krcmtBusinessTypeDaily.krcmtBusinessTypeMobileDailyPK.attendanceItemId,
				krcmtBusinessTypeDaily.order);
		return workTypeFormatDaily;
	}
	
	private KrcmtBusinessTypeSDaily toEntity(BusinessTypeSFormatDaily businessTypeFormatDaily){
		val entity = new KrcmtBusinessTypeSDaily();
		
		entity.krcmtBusinessTypeMobileDailyPK = new KrcmtBusinessTypeSDailyPK();
		entity.krcmtBusinessTypeMobileDailyPK.attendanceItemId = businessTypeFormatDaily.getAttendanceItemId();
		entity.krcmtBusinessTypeMobileDailyPK.businessTypeCode = businessTypeFormatDaily.getBusinessTypeCode().v();
		entity.krcmtBusinessTypeMobileDailyPK.companyId = businessTypeFormatDaily.getCompanyId();
		entity.order = businessTypeFormatDaily.getOrder();
		
		return entity;
	}
	
	@Override
	public List<BusinessTypeSFormatDaily> getBusinessTypeFormatByCompanyId(String companyId) {
		return this.queryProxy()
				.query(FIND_BY_COMPANYID, KrcmtBusinessTypeSDaily.class)
				.setParameter("companyId", companyId)
				.getList(f -> toDomain(f));
	}

	@Override
	public void deleteExistDataByCode(String businesstypeCode, String companyId,
			List<Integer> attendanceItemIds) {
		CollectionUtil.split(attendanceItemIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			this.getEntityManager().createQuery(REMOVE_EXIST_DATA_BY_CODE)
				.setParameter("companyId", companyId)
				.setParameter("businessTypeCode", businesstypeCode)
				.setParameter("attendanceItemIds", subList)
				.executeUpdate();
		});
	}
	
}
