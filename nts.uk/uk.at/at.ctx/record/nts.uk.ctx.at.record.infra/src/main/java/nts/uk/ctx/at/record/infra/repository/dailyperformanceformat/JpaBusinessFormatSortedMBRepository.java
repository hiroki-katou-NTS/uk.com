package nts.uk.ctx.at.record.infra.repository.dailyperformanceformat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessTypeSortedMobile;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessFormatSortedMBRepository;
import nts.uk.ctx.at.record.infra.entity.dailyperformanceformat.KfnmtDayFormSBusSort;
import nts.uk.ctx.at.record.infra.entity.dailyperformanceformat.KfnmtDayFormSBusSortPK;

/**
 * 
 * @author anhdt
 *
 */
@Stateless
public class JpaBusinessFormatSortedMBRepository extends JpaRepository implements BusinessFormatSortedMBRepository {

	private static final String FIND;

	private static final String UPDATE_BY_KEY;

	private static final String FIND_ALL;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KfnmtDayFormSBusSort a ");
		builderString.append("WHERE a.kfnmtDayFormSBusSortPK.companyId = :companyId ");
		builderString.append("AND a.kfnmtDayFormSBusSortPK.attendanceItemId IN :attendanceItemId ");
		FIND = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("UPDATE KfnmtDayFormSBusSort a ");
		builderString.append("SET a.order = :order ");
		builderString.append("WHERE a.kfnmtDayFormSBusSortPK.companyId = :companyId ");
		builderString.append("AND a.kfnmtDayFormSBusSortPK.attendanceItemId = :attendanceItemId ");
		UPDATE_BY_KEY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KfnmtDayFormSBusSort a ");
		builderString.append("WHERE a.kfnmtDayFormSBusSortPK.companyId = :companyId ");
		FIND_ALL = builderString.toString();
	}
	
	/**
	 * add list Business Type Sorted Mobile
	 */
	@Override
	public void add(List<BusinessTypeSortedMobile> businessTypeSorteds) {
		businessTypeSorteds.forEach(f -> this.commandProxy().insert(toEntity(f)));
		
	}

	/**
	 * update Business Type Sorted Mobile
	 */
	@Override
	public void update(BusinessTypeSortedMobile businessTypeSorted) {
		this.getEntityManager().createQuery(UPDATE_BY_KEY).setParameter("companyId", businessTypeSorted.getCompanyId())
		.setParameter("attendanceItemId", businessTypeSorted.getAttendanceItemId())
		.setParameter("order",new BigDecimal(businessTypeSorted.getOrder().v())).executeUpdate();
		
	}

	/**
	 * find Business Type Sorted Mobile
	 */
	@Override
	public List<BusinessTypeSortedMobile> find(String companyId, List<Integer> attendanceItemIds) {

		List<KfnmtDayFormSBusSort> resultList = new ArrayList<>();
		CollectionUtil.split(attendanceItemIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			resultList.addAll(this.queryProxy().query(FIND, KfnmtDayFormSBusSort.class)
					.setParameter("companyId", companyId)
					.setParameter("attendanceItemId", subList)
					.getList());
		});
		return resultList.stream().map(f -> toDomain(f)).collect(Collectors.toList());
	}

	/**
	 * find all Business Type Sorted Mobile
	 */
	@Override
	public List<BusinessTypeSortedMobile> findAll(String companyId) {
		return this.queryProxy().query(FIND_ALL, KfnmtDayFormSBusSort.class).setParameter("companyId", companyId)
				.getList(f -> toDomain(f));
	}


	/**
	 * Convert to domain Business Type Sorted Mobile
	 * @param krcstBusinessTypeSorted
	 * @return
	 */
	private static BusinessTypeSortedMobile toDomain(KfnmtDayFormSBusSort krcstBusinessTypeSorted) {
		BusinessTypeSortedMobile businessTypeSorted = BusinessTypeSortedMobile.createFromJavaType(
				krcstBusinessTypeSorted.kfnmtDayFormSBusSortPK.companyId,
				krcstBusinessTypeSorted.kfnmtDayFormSBusSortPK.attendanceItemId.intValue(),
				krcstBusinessTypeSorted.order.intValue());
		return businessTypeSorted;
	}

	/**
	 * Convert to domain Entity Type Sorted Mobile
	 * @param businessTypeSorted
	 * @return
	 */
	private KfnmtDayFormSBusSort toEntity(BusinessTypeSortedMobile businessTypeSorted) {
		val entity = new KfnmtDayFormSBusSort();

		entity.kfnmtDayFormSBusSortPK = new KfnmtDayFormSBusSortPK();
		entity.kfnmtDayFormSBusSortPK.companyId = businessTypeSorted.getCompanyId();
		entity.kfnmtDayFormSBusSortPK.attendanceItemId = new BigDecimal(businessTypeSorted.getAttendanceItemId());
		entity.order = new BigDecimal(businessTypeSorted.getOrder().v());

		return entity;
	}

}
