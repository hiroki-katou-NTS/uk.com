package nts.uk.ctx.at.function.infra.repository.dailyattendanceitemused;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.infra.entity.dailyattendanceitemused.KfnctAtdIdRptDai;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyAttendanceItemUsedRepository;

/**
 * The Class JpaDailyAttendanceItemUsedRepository.
 * @author LienPTK
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaDailyAttendanceItemUsedRepository extends JpaRepository implements DailyAttendanceItemUsedRepository {

	private static final String SELECT_DAILY_ATTENDANCE_ITEM = "SELECT d FROM KfnctAtdIdRptDai d"
			+ " WHERE d.kfnctAtdIdRptDaiPK.companyId = :companyId"
			+ "		AND (d.workDaily = :reportId"
			+ "			OR d.workAttendance = :reportId"
			+ "			OR d.atdWorkDaily = :reportId"
			+ "			OR d.atdWorkAttendance = :reportId)";

	@Override
	public List<Integer> getAllDailyItemId(String companyId, BigDecimal reportId) {
		return this.queryProxy().query(SELECT_DAILY_ATTENDANCE_ITEM, KfnctAtdIdRptDai.class)
				.setParameter("companyId", companyId)
				.setParameter("reportId", reportId)
				.getList().stream().map(x -> x.getKfnctAtdIdRptDaiPK().getAttendanceItemId().intValue())
				.collect(Collectors.toList());
	}

}
