package nts.uk.ctx.at.shared.infra.repository.scherec.dailyattendanceitem;

import java.util.List;
import java.util.Optional;
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
	private final String SELECT_BY_AUTHORITY_DAILY_ID = "SELECT c FROM KshstDailyAttdItemAuth c"
			+ " WHERE c.kshstDailyAttdItemAuthPK.companyID = :companyID"
			+ " AND c.kshstDailyAttdItemAuthPK.authorityDailyID = :authorityDailyID";
	@Override
	public List<DailyAttendanceItemAuthority> getListDailyAttendanceItemAuthority(String companyID) {
		List<DailyAttendanceItemAuthority> data = this.queryProxy().query(SELECT_BY_AUTHORITYID_AND_COMPANYID,KshstDailyAttdItemAuth.class)
				.setParameter("companyID", companyID).getList().stream()
				.map(c->c.toDomain()).collect(Collectors.toList());
		return data;
	}
	@Override
	public Optional<DailyAttendanceItemAuthority> getDailyAttdItem(String companyID, String authorityDailyId) {
		Optional<DailyAttendanceItemAuthority> data = this.queryProxy().query(SELECT_BY_AUTHORITY_DAILY_ID,KshstDailyAttdItemAuth.class)
				.setParameter("companyID", companyID)
				.setParameter("authorityDailyID", authorityDailyId)
				.getSingle(c->c.toDomain());
		return data;
	}



}
