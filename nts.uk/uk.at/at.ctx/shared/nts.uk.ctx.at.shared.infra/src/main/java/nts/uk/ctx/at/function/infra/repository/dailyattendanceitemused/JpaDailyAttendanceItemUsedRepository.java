package nts.uk.ctx.at.function.infra.repository.dailyattendanceitemused;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyAttendanceItemUsedRepository;
import nts.uk.ctx.at.shared.infra.entity.scherec.dailyattendanceitem.KfnctAtdIdRptDai;

/**
 * The Class JpaDailyAttendanceItemUsedRepository.
 * @author LienPTK
 */
@Stateless
public class JpaDailyAttendanceItemUsedRepository extends JpaRepository implements DailyAttendanceItemUsedRepository {

	private static final String SELECT_DAILY_ATTENDANCE_ITEM = "SELECT d FROM KfnctAtdIdRptDai"
			+ " WHERE d.kfnctAtdIdRptDaiPK.attendanceItemId = :reportId"
			+ "		AND d.kfnctAtdIdRptDaiPK.attendanceItemId = :companyId";

	@Override
	public List<Integer> getAllDailyItemId(String companyId, String reportId) {
		return this.queryProxy().query(SELECT_DAILY_ATTENDANCE_ITEM, KfnctAtdIdRptDai.class)
				.setParameter("companyId", companyId)
				.setParameter("reportId", reportId)
				.getList().stream().map(x -> x.getKfnctAtdIdRptDaiPK().getAttendanceItemId().intValue())
				.collect(Collectors.toList());
	}

}
