package nts.uk.ctx.at.record.infra.repository.dailyperformanceformat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
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
		builderString.append("AND a.krcstBusinessTypeSortedPK.attendanceItemId = :attendanceItemId ");
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
				.setParameter("order", businessTypeSorted.getOrder()).executeUpdate();
	}

	@Override
	public Optional<BusinessTypeSorted> find(String companyId, int attendanceItemId) {
		return this.queryProxy().query(FIND, KrcstBusinessTypeSorted.class).setParameter("companyId", companyId)
				.setParameter("attendanceItemId", attendanceItemId).getSingle(f -> toDomain(f));
	}

	@Override
	public List<BusinessTypeSorted> findAll(String companyId) {
		return this.queryProxy().query(FIND_ALL, KrcstBusinessTypeSorted.class).setParameter("companyId", companyId)
				.getList(f -> toDomain(f));
	}

	private static BusinessTypeSorted toDomain(KrcstBusinessTypeSorted krcstBusinessTypeSorted) {
		BusinessTypeSorted businessTypeSorted = BusinessTypeSorted.createFromJavaType(
				krcstBusinessTypeSorted.krcstBusinessTypeSortedPK.companyId,
				krcstBusinessTypeSorted.krcstBusinessTypeSortedPK.attendanceItemId.intValue(), krcstBusinessTypeSorted.order.intValue());
		return businessTypeSorted;
	}

	private KrcstBusinessTypeSorted toEntity(BusinessTypeSorted businessTypeSorted) {
		val entity = new KrcstBusinessTypeSorted();

		entity.krcstBusinessTypeSortedPK = new KrcstBusinessTypeSortedPK();
		entity.krcstBusinessTypeSortedPK.companyId = businessTypeSorted.getCompanyId();
		entity.krcstBusinessTypeSortedPK.attendanceItemId = new BigDecimal(businessTypeSorted.getAttendanceItemId());
		entity.order = new BigDecimal(businessTypeSorted.getOrder());

		return entity;
	}

}
