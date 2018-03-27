package nts.uk.ctx.at.shared.infra.repository.scherec.dailyattendanceitem;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItemAuthority;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyAttdItemAuthRepository;
import nts.uk.ctx.at.shared.infra.entity.scherec.dailyattendanceitem.KshstDailyAttdItemAuth;

@Stateless
public class JpaDailyAttdItemAuthRepository extends JpaRepository implements DailyAttdItemAuthRepository {
	private final String SELECT_BY_AUTHORITYID_AND_COMPANYID = "SELECT c, k.userCanSet, k.krcmtDailyAttendanceItemPK.attendanceItemId "
			+ "FROM KrcmtDailyAttendanceItem k LEFT JOIN KshstDailyAttdItemAuth c "
			+ "ON c.kshstDailyAttdItemAuthPK.attendanceItemId = k.krcmtDailyAttendanceItemPK.attendanceItemId  "
			+ "AND c.kshstDailyAttdItemAuthPK.authorityId = :authorityId"
			+ " WHERE k.krcmtDailyAttendanceItemPK.companyId = :companyId";

	@Override
	public List<DailyAttendanceItemAuthority> getListDailyAttendanceItemAuthority(String companyID) {
		List<DailyAttendanceItemAuthority> data = this.queryProxy().query(SELECT_BY_AUTHORITYID_AND_COMPANYID,KshstDailyAttdItemAuth.class)
				.setParameter("companyID", companyID).getList().stream()
				.map(c->c.toDomain()).collect(Collectors.toList());
		return data;
	}



}
