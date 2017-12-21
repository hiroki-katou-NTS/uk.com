package nts.uk.ctx.at.shared.infra.repository.calculation.holiday;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.calculation.holiday.roundingmonth.RoundingMonth;
import nts.uk.ctx.at.shared.dom.calculation.holiday.roundingmonth.RoundingMonthRepository;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstRoundingMonthSet;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstRoundingMonthSetPK;

@Stateless
public class JpaRoudingMonthRepository extends JpaRepository implements RoundingMonthRepository{
	private static final String SELECT_BY_CID;
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KshstRoundingMonthSet e");
		builderString.append(" WHERE e.kshstRoundingMonthSetPK.companyId = :companyId");
		SELECT_BY_CID = builderString.toString();
	}

	private RoundingMonth convertToDomain(KshstRoundingMonthSet monthSet) {
		RoundingMonth month = RoundingMonth.createFromJavaType(monthSet.kshstRoundingMonthSetPK.companyId, monthSet.kshstRoundingMonthSetPK.timeItemId, monthSet.unit, monthSet.rounding);
		
		return month;
	}
	
	private KshstRoundingMonthSet convertToDbType(RoundingMonth month) {
		KshstRoundingMonthSet monthSet = new KshstRoundingMonthSet();
		KshstRoundingMonthSetPK monthSetPK = new KshstRoundingMonthSetPK(month.getCompanyId(),month.getTimeItemId().v());
				monthSet.unit = month.getUnit().value;
				monthSet.rounding = month.getRounding().value;
				monthSet.kshstRoundingMonthSetPK = monthSetPK;
		return monthSet;
	}
	
	@Override
	public List<RoundingMonth> findByCompanyId(String companyId, String itemTimeId) {
		return this.queryProxy().query(SELECT_BY_CID, KshstRoundingMonthSet.class).setParameter("companyId", companyId)
				.getList(c -> convertToDomain(c));
	}
	
	@Override
	public void add(RoundingMonth month) {
		this.commandProxy().insert(convertToDbType(month));
	}
	
	@Override
	public void update(RoundingMonth month) {
		KshstRoundingMonthSetPK primaryKey = new KshstRoundingMonthSetPK(month.getCompanyId(), month.getTimeItemId().v());
		KshstRoundingMonthSet entity = this.queryProxy().find(primaryKey, KshstRoundingMonthSet.class).get();
				entity.unit = month.getUnit().value;
				entity.rounding = month.getRounding().value;
				
				entity.kshstRoundingMonthSetPK = primaryKey;
		this.commandProxy().update(entity);
	}
	
	@Override
	public Optional<RoundingMonth> findByCId(String companyId, String timeItemId) {
		return this.queryProxy().find(new KshstRoundingMonthSetPK(companyId,timeItemId),KshstRoundingMonthSet.class)
				.map(c->convertToDomain(c));
	}

}
