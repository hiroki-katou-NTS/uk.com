package nts.uk.ctx.at.shared.infra.repository.scherec.monthlyattendanceitem;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.DisplayAndInputMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.MonthlyItemControlByAuthRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.MonthlyItemControlByAuthority;
import nts.uk.ctx.at.shared.infra.entity.scherec.monthlyattendanceitem.KrcstDisplayAndInputMonthly;

@Stateless
public class JpaMonthlyItemControlByAuthRepository  extends JpaRepository implements MonthlyItemControlByAuthRepository {

	private final String SELECT_BY_AUTHORITY_MONTHLY_ID = "SELECT c FROM KrcstDisplayAndInputMonthly c"
			+ " WHERE c.krcstDisplayAndInputMonthlyPK.companyID = :companyID"
			+ " AND c.krcstDisplayAndInputMonthlyPK.authorityMonthlyID = :authorityMonthlyID"
			+ " ORDER BY c.krcstDisplayAndInputMonthlyPK.itemMonthlyID";
	@Override
	public List<MonthlyItemControlByAuthority> getListMonthlyAttendanceItemAuthority(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<MonthlyItemControlByAuthority> getMonthlyAttdItem(String companyID, String authorityMonthlyId) {
		List<DisplayAndInputMonthly> data = this.queryProxy().query(SELECT_BY_AUTHORITY_MONTHLY_ID,KrcstDisplayAndInputMonthly.class)
				.setParameter("companyID", companyID)
				.setParameter("authorityMonthlyID", authorityMonthlyId)
				.getList(c->c.toDomain());
		if(data.isEmpty())
			return Optional.empty();
		MonthlyItemControlByAuthority monthlyItemControlByAuthority = new MonthlyItemControlByAuthority(
				companyID,authorityMonthlyId,data
				);
		return Optional.of(monthlyItemControlByAuthority);
	}

	@Override
	public void updateMonthlyAttdItemAuth(MonthlyItemControlByAuthority monthlyItemControlByAuthority) {
		List<KrcstDisplayAndInputMonthly> newEntity = monthlyItemControlByAuthority.getListDisplayAndInputMonthly().stream()
				.map(c->KrcstDisplayAndInputMonthly.toEntity(
						monthlyItemControlByAuthority.getCompanyId(),
						monthlyItemControlByAuthority.getAuthorityMonthlyId(), c))
				.collect(Collectors.toList());
			
		for(int i=0;i<newEntity.size();i++) {
			this.commandProxy().update(newEntity.get(i));
		}
		
	}

	@Override
	public void addMonthlyAttdItemAuth(MonthlyItemControlByAuthority monthlyItemControlByAuthority) {
		List<KrcstDisplayAndInputMonthly> newEntity = monthlyItemControlByAuthority.getListDisplayAndInputMonthly().stream()
				.map(c->KrcstDisplayAndInputMonthly.toEntity(
						monthlyItemControlByAuthority.getCompanyId(),
						monthlyItemControlByAuthority.getAuthorityMonthlyId(), c))
				.collect(Collectors.toList());
		for(KrcstDisplayAndInputMonthly krcstDisplayAndInputMonthly:newEntity) {
			this.commandProxy().insert(krcstDisplayAndInputMonthly);
		} 
	}

}
