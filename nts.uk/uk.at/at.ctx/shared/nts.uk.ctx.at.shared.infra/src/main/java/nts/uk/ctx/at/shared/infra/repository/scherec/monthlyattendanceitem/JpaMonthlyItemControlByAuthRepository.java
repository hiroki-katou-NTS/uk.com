package nts.uk.ctx.at.shared.infra.repository.scherec.monthlyattendanceitem;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.MonthlyItemControlByAuthRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.MonthlyItemControlByAuthority;
import nts.uk.ctx.at.shared.infra.entity.scherec.monthlyattendanceitem.KrcmtMonthlyItemControlByAuth;

@Stateless
public class JpaMonthlyItemControlByAuthRepository  extends JpaRepository implements MonthlyItemControlByAuthRepository {

	private final String SELECT_BY_AUTHORITY_MONTHLY_ID = "SELECT c FROM KrcmtMonthlyItemControlByAuth c"
			+ " WHERE c.krcntMonthlyItemControlByAuthPK.companyID = :companyID"
			+ " AND c.krcntMonthlyItemControlByAuthPK.authorityMonthlyID = :authorityMonthlyID";
	@Override
	public List<MonthlyItemControlByAuthority> getListMonthlyAttendanceItemAuthority(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<MonthlyItemControlByAuthority> getMonthlyAttdItem(String companyID, String authorityMonthlyId) {
		Optional<MonthlyItemControlByAuthority> data = this.queryProxy().query(SELECT_BY_AUTHORITY_MONTHLY_ID,KrcmtMonthlyItemControlByAuth.class)
				.setParameter("companyID", companyID)
				.setParameter("authorityMonthlyID", authorityMonthlyId)
				.getSingle(c->c.toDomain());
		return data;
	}

	@Override
	public void updateMonthlyAttdItemAuth(MonthlyItemControlByAuthority monthlyItemControlByAuthority) {
		KrcmtMonthlyItemControlByAuth newEntity =KrcmtMonthlyItemControlByAuth.toEntity(monthlyItemControlByAuthority.getCompanyId(), 
				monthlyItemControlByAuthority.getAuthorityMonthlyId(), monthlyItemControlByAuthority);
		KrcmtMonthlyItemControlByAuth updateEntity = this.queryProxy().find(newEntity.getKrcntMonthlyItemControlByAuthPK(), KrcmtMonthlyItemControlByAuth.class).get();
			updateEntity.monthlyServiceTypeControls =newEntity.monthlyServiceTypeControls;
			this.commandProxy().update(updateEntity);
		
	}

	@Override
	public void addMonthlyAttdItemAuth(MonthlyItemControlByAuthority monthlyItemControlByAuthority) {
		KrcmtMonthlyItemControlByAuth newEntity =KrcmtMonthlyItemControlByAuth.toEntity(monthlyItemControlByAuthority.getCompanyId(), 
				monthlyItemControlByAuthority.getAuthorityMonthlyId(), monthlyItemControlByAuthority);
		this.commandProxy().insert(newEntity); 
	}

}
