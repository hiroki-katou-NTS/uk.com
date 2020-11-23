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

	private static final String SELECT_BY_WORK_DAILY = "SELECT d FROM KfnctAtdIdRptDai d"
			+ " WHERE d.kfnctAtdIdRptDaiPK.companyId = :companyId AND d.workDaily = 1";
	
	private static final String SELECT_BY_WORK_ATTENDANCE = "SELECT d FROM KfnctAtdIdRptDai d"
			+ " WHERE d.kfnctAtdIdRptDaiPK.companyId = :companyId AND d.workAttendance = 1";
	
	private static final String SELECT_BY_ATD_WORK_DAILY = "SELECT d FROM KfnctAtdIdRptDai d"
			+ " WHERE d.kfnctAtdIdRptDaiPK.companyId = :companyId AND d.atdWorkDaily = 1";
	
	private static final String SELECT_BY_ATD_WORK_ATTENDANCE = "SELECT d FROM KfnctAtdIdRptDai d"
			+ " WHERE d.kfnctAtdIdRptDaiPK.companyId = :companyId AND d.atdWorkAttendance = 1";
	
	@Override
	public List<Integer> getAllDailyItemId(String companyId, BigDecimal reportId) {
		String query = "";
		switch (reportId.intValue()) {
			case 1:
				query = SELECT_BY_WORK_DAILY;
				break;
			case 2:
				query = SELECT_BY_WORK_ATTENDANCE;
				break;
			case 6:
				query = SELECT_BY_ATD_WORK_DAILY;
				break;
			case 7:
				query = SELECT_BY_ATD_WORK_ATTENDANCE;
				break;
			default:
				break;
		}
		return this.queryProxy().query(query, KfnctAtdIdRptDai.class)
				.setParameter("companyId", companyId)
				.getList().stream().map(x -> x.getKfnctAtdIdRptDaiPK().getAttendanceItemId().intValue())
				.collect(Collectors.toList());
	}

}
