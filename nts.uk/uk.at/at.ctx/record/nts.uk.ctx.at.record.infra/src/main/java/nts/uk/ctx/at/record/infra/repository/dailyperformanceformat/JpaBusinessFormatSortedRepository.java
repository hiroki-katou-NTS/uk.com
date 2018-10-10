package nts.uk.ctx.at.record.infra.repository.dailyperformanceformat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessTypeSorted;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessFormatSortedRepository;
import nts.uk.ctx.at.record.infra.entity.dailyperformanceformat.KrcstBusinessTypeSorted;
import nts.uk.ctx.at.record.infra.entity.dailyperformanceformat.KrcstBusinessTypeSortedPK;

@Stateless
public class JpaBusinessFormatSortedRepository extends JpaRepository implements BusinessFormatSortedRepository {

	private static final String FIND;

	private static final String UPDATE_BY_KEY;

	private static final String FIND_ALL;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcstBusinessTypeSorted a ");
		builderString.append("WHERE a.krcstBusinessTypeSortedPK.companyId = :companyId ");
		builderString.append("AND a.krcstBusinessTypeSortedPK.attendanceItemId IN :attendanceItemId ");
		FIND = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("UPDATE KrcstBusinessTypeSorted a ");
		builderString.append("SET a.order = :order ");
		builderString.append("WHERE a.krcstBusinessTypeSortedPK.companyId = :companyId ");
		builderString.append("AND a.krcstBusinessTypeSortedPK.attendanceItemId = :attendanceItemId ");
		UPDATE_BY_KEY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcstBusinessTypeSorted a ");
		builderString.append("WHERE a.krcstBusinessTypeSortedPK.companyId = :companyId ");
		FIND_ALL = builderString.toString();
	}

	@Override
	public void add(List<BusinessTypeSorted> businessTypeSorteds) {
		businessTypeSorteds.forEach(f -> this.commandProxy().insert(toEntity(f)));
	}

	@Override
	public void update(BusinessTypeSorted businessTypeSorted) {
		this.getEntityManager().createQuery(UPDATE_BY_KEY).setParameter("companyId", businessTypeSorted.getCompanyId())
				.setParameter("attendanceItemId", businessTypeSorted.getAttendanceItemId())
				.setParameter("order",new BigDecimal(businessTypeSorted.getOrder().v())).executeUpdate();
	}

	@Override
	public List<BusinessTypeSorted> find(String companyId, List<Integer> attendanceItemIds) {
//		if (attendanceItemIds.size() > 1000) {
//			Map<Integer, List<Integer>> result = new HashMap<>();
//			splitParas(result, attendanceItemIds, 0);
//			List<BusinessTypeSorted> results = new ArrayList<>();
//			Iterator<Entry<Integer, List<Integer>>> it = result.entrySet().iterator();
//			while (it.hasNext()) {
//				Entry<Integer, List<Integer>> item = it.next();
//				results.addAll(this.queryProxy().query(FIND, KrcstBusinessTypeSorted.class)
//						.setParameter("companyId", companyId).setParameter("attendanceItemId", item.getValue())
//						.getList(f -> toDomain(f)));
//				it.remove();
//			}
//			return results;
//		}
		List<BusinessTypeSorted> resultList = new ArrayList<>();
		CollectionUtil.split(attendanceItemIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			resultList.addAll(this.queryProxy().query(FIND, KrcstBusinessTypeSorted.class)
					.setParameter("companyId", companyId)
					.setParameter("attendanceItemId", subList)
					.getList(f -> toDomain(f)));
		});
		return resultList;
	}

	@Override
	public List<BusinessTypeSorted> findAll(String companyId) {
		return this.queryProxy().query(FIND_ALL, KrcstBusinessTypeSorted.class).setParameter("companyId", companyId)
				.getList(f -> toDomain(f));
	}

//	private void splitParas(Map<Integer, List<Integer>> result, List<Integer> attendanceItemIds, int i) {
//		if (attendanceItemIds.size() > 1000) {
//			result.put(i + 1, attendanceItemIds.subList(0, 999));
//			attendanceItemIds = attendanceItemIds.subList(999, attendanceItemIds.size());
//		} else {
//			result.put(i + 1, attendanceItemIds.subList(0, attendanceItemIds.size()));
//			attendanceItemIds = attendanceItemIds.subList(attendanceItemIds.size(), attendanceItemIds.size());
//		}
//		if (attendanceItemIds.size() > 0) {
//			splitParas(result, attendanceItemIds, i + 1);
//		}
//	}

	private static BusinessTypeSorted toDomain(KrcstBusinessTypeSorted krcstBusinessTypeSorted) {
		BusinessTypeSorted businessTypeSorted = BusinessTypeSorted.createFromJavaType(
				krcstBusinessTypeSorted.krcstBusinessTypeSortedPK.companyId,
				krcstBusinessTypeSorted.krcstBusinessTypeSortedPK.attendanceItemId.intValue(),
				krcstBusinessTypeSorted.order.intValue());
		return businessTypeSorted;
	}

	private KrcstBusinessTypeSorted toEntity(BusinessTypeSorted businessTypeSorted) {
		val entity = new KrcstBusinessTypeSorted();

		entity.krcstBusinessTypeSortedPK = new KrcstBusinessTypeSortedPK();
		entity.krcstBusinessTypeSortedPK.companyId = businessTypeSorted.getCompanyId();
		entity.krcstBusinessTypeSortedPK.attendanceItemId = new BigDecimal(businessTypeSorted.getAttendanceItemId());
		entity.order = new BigDecimal(businessTypeSorted.getOrder().v());

		return entity;
	}

}
