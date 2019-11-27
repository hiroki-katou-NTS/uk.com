package nts.uk.ctx.at.function.infra.repository.dailyperformanceformat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.BusinessTypeSFormatMonthly;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.BusinessTypeSFormatMonthlyRepository;
import nts.uk.ctx.at.function.infra.entity.dailyperformanceformat.KrcmtBusinessTypeSMonthly;
import nts.uk.ctx.at.function.infra.entity.dailyperformanceformat.KrcmtBusinessTypeSMonthlyPK;

@Stateless
public class JpaBusinessTypeFormatSMonthlyRepository extends JpaRepository
		implements BusinessTypeSFormatMonthlyRepository {

	private static final String FIND;
	
	private static final String FIND_BY_LIST_CODE;

	private static final String UPDATE_BY_KEY;

	private static final String REMOVE_EXIST_DATA;
	
	private static final String IS_EXIST_DATA;
	
	private static final String FIND_BY_COMPANYID;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcmtBusinessTypeSMonthly a ");
		builderString.append("WHERE a.krcmtBusinessTypeSMonthlyPK.companyId = :companyId ");
		builderString.append("AND a.krcmtBusinessTypeSMonthlyPK.businessTypeCode = :businessTypeCode ");
		FIND = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcmtBusinessTypeSMonthly a ");
		builderString.append("WHERE a.krcmtBusinessTypeSMonthlyPK.companyId = :companyId ");
		builderString.append("AND a.krcmtBusinessTypeSMonthlyPK.businessTypeCode IN :listBusinessTypeCode ");
		builderString.append("ORDER BY a.order ASC ");
		FIND_BY_LIST_CODE = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("UPDATE KrcmtBusinessTypeSMonthly a ");
		builderString.append("SET a.order = :order ");
		builderString.append("WHERE a.krcmtBusinessTypeSMonthlyPK.companyId = :companyId ");
		builderString.append("AND a.krcmtBusinessTypeSMonthlyPK.businessTypeCode = :businessTypeCode ");
		builderString.append("AND a.krcmtBusinessTypeSMonthlyPK.attendanceItemId = :attendanceItemId ");
		UPDATE_BY_KEY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcmtBusinessTypeSMonthly a ");
		builderString.append("WHERE a.krcmtBusinessTypeSMonthlyPK.companyId = :companyId ");
		builderString.append("AND a.krcmtBusinessTypeSMonthlyPK.businessTypeCode = :businessTypeCode ");
		builderString.append("AND a.krcmtBusinessTypeSMonthlyPK.attendanceItemId IN :attendanceItemIds ");
		REMOVE_EXIST_DATA = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT COUNT(a) ");
		builderString.append("FROM KrcmtBusinessTypeSMonthly a ");
		builderString.append("WHERE a.krcmtBusinessTypeSMonthlyPK.companyId = :companyId ");
		builderString.append("AND a.krcmtBusinessTypeSMonthlyPK.businessTypeCode = :businessTypeCode ");
		IS_EXIST_DATA = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcmtBusinessTypeSMonthly a ");
		builderString.append("WHERE a.krcmtBusinessTypeSMonthlyPK.companyId = :companyId ");
		FIND_BY_COMPANYID = builderString.toString();
	}

	@Override
	public List<BusinessTypeSFormatMonthly> getMonthlyDetail(String companyId, String businessTypeCode) {
		return this.queryProxy().query(FIND, KrcmtBusinessTypeSMonthly.class)
				.setParameter("companyId", companyId)
				.setParameter("businessTypeCode", businessTypeCode)
				.getList(f -> toDomain(f));
	}

	@Override
	public void update(BusinessTypeSFormatMonthly businessTypeFormatMonthly) {
		this.getEntityManager().createQuery(UPDATE_BY_KEY)
				.setParameter("companyId", businessTypeFormatMonthly.getCompanyId())
				.setParameter("businessTypeCode", businessTypeFormatMonthly.getBusinessTypeCode().v())
				.setParameter("attendanceItemId", businessTypeFormatMonthly.getAttendanceItemId())
				.setParameter("order", businessTypeFormatMonthly.getOrder()).executeUpdate();
	}

	/*
	 * Remove attendanceItemId not exist in list that need update
	 */
	@Override
	public void deleteExistData(String companyId, String businessTypeCode, List<Integer> attendanceItemIds) {
		CollectionUtil.split(attendanceItemIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			this.getEntityManager().createQuery(REMOVE_EXIST_DATA)
				.setParameter("companyId", companyId)
				.setParameter("businessTypeCode", businessTypeCode)
				.setParameter("attendanceItemIds", subList)
				.executeUpdate();
		});
		this.getEntityManager().flush();
	}

	@Override
	public void add(List<BusinessTypeSFormatMonthly> businessTypeFormatMonthlyAdds) {
		businessTypeFormatMonthlyAdds.forEach(f -> this.commandProxy().insert(toEntity(f)));
	}

	@Override
	public boolean checkExistData(String companyId, String businessTypeCode) {
		return this.queryProxy().query(IS_EXIST_DATA, long.class).setParameter("companyId", companyId)
				.setParameter("businessTypeCode", businessTypeCode).getSingle().get() > 0;
	}

	private static BusinessTypeSFormatMonthly toDomain(KrcmtBusinessTypeSMonthly entity) {
		BusinessTypeSFormatMonthly workTypeFormatMonthly = BusinessTypeSFormatMonthly.createFromJavaType(
				entity.krcmtBusinessTypeSMonthlyPK.companyId,
				entity.krcmtBusinessTypeSMonthlyPK.businessTypeCode,
				entity.krcmtBusinessTypeSMonthlyPK.attendanceItemId, 
				entity.order);
		return workTypeFormatMonthly;
	}
	
	private KrcmtBusinessTypeSMonthly toEntity(BusinessTypeSFormatMonthly businessTypeFormatMonthly){
		val entity = new KrcmtBusinessTypeSMonthly();
		
		entity.krcmtBusinessTypeSMonthlyPK = new KrcmtBusinessTypeSMonthlyPK();
		entity.krcmtBusinessTypeSMonthlyPK.companyId = businessTypeFormatMonthly.getCompanyId();
		entity.krcmtBusinessTypeSMonthlyPK.attendanceItemId = businessTypeFormatMonthly.getAttendanceItemId();
		entity.krcmtBusinessTypeSMonthlyPK.businessTypeCode = businessTypeFormatMonthly.getBusinessTypeCode().v();
		entity.order = businessTypeFormatMonthly.getOrder();
		
		return entity;
	}

	@Override
	public List<BusinessTypeSFormatMonthly> getMonthlyDetailByCompanyId(String companyId) {
		return this.queryProxy()
				.query(FIND_BY_COMPANYID, KrcmtBusinessTypeSMonthly.class)
				.setParameter("companyId", companyId)
				.getList(f -> toDomain(f));
	}
	
	@Override
	public List<BusinessTypeSFormatMonthly> getListBusinessTypeFormat(String companyId, Collection<String> listBusinessTypeCode) {
		List<BusinessTypeSFormatMonthly> resultList = new ArrayList<>();
		CollectionUtil.split(new ArrayList<String>(listBusinessTypeCode), DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			resultList.addAll(this.queryProxy().query(FIND_BY_LIST_CODE, KrcmtBusinessTypeSMonthly.class)
					.setParameter("companyId", companyId)
					.setParameter("listBusinessTypeCode", subList)
					.getList(f -> toDomain(f)));
		});
		resultList.sort(Comparator.comparing(BusinessTypeSFormatMonthly::getOrder));
		return resultList;
	}
}
