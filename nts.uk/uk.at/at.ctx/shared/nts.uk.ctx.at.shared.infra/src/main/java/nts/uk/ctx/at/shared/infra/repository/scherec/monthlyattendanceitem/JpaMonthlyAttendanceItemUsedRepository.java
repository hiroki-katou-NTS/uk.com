package nts.uk.ctx.at.shared.infra.repository.scherec.monthlyattendanceitem;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.MonthlyAttendanceItemUsedRepository;
import nts.uk.ctx.at.shared.infra.entity.monthlyattendanceitemused.KfnctAtdIdRptMon;

/**
 * The Class JpaMonthlyAttendanceItemUsedRepository.
 * @author DungDV
 */
@Stateless
public class JpaMonthlyAttendanceItemUsedRepository extends JpaRepository implements MonthlyAttendanceItemUsedRepository {

	private static final String SELECT_BY_WORK_ATTENDANCE = "SELECT d FROM KfnctAtdIdRptMon d"
			+ " WHERE d.kfnctAtdIdRptMonPK.companyId = :companyId AND d.workAttendance = 1";
	
	private static final String SELECT_BY_WORK_MONTHLY = "SELECT d FROM KfnctAtdIdRptMon d"
			+ " WHERE d.kfnctAtdIdRptMonPK.companyId = :companyId AND d.workMonthly = 1";
	
	private static final String SELECT_BY_WORK_YEARLY = "SELECT d FROM KfnctAtdIdRptMon d"
			+ " WHERE d.kfnctAtdIdRptMonPK.companyId = :companyId AND d.workYearly = 1";
	
	private static final String SELECT_BY_WORK_PERIOD = "SELECT d FROM KfnctAtdIdRptMon d"
			+ " WHERE d.kfnctAtdIdRptMonPK.companyId = :companyId AND d.workPeriod = 1)";
	
	private static final String SELECT_BY_ATD_WORK_ATTENDANCE = "SELECT d FROM KfnctAtdIdRptMon d"
			+ " WHERE d.kfnctAtdIdRptMonPK.companyId = :companyId AND d.atdWorkAttendance = 1)";
	
	private static final String SELECT_BY_ATD_WORK_YEARLY = "SELECT d FROM KfnctAtdIdRptMon d"
			+ " WHERE d.kfnctAtdIdRptMonPK.companyId = :companyId AND d.atdWorkYearly = 1)";

	@Override
	public List<Integer> getAllMonthlyItemId(String companyId, int reportId) {
		String query = "";
		switch (reportId) {
			case 2:
				query = SELECT_BY_WORK_ATTENDANCE;
				break;
			case 3:
				query = SELECT_BY_WORK_MONTHLY;
				break;
			case 4:
				query = SELECT_BY_WORK_YEARLY;
				break;
			case 5:
				query = SELECT_BY_WORK_PERIOD;
				break;
			case 7:
				query = SELECT_BY_ATD_WORK_ATTENDANCE;
				break;
			case 8:
				query = SELECT_BY_ATD_WORK_YEARLY;
				break;
			default:
				break;
		}

		if (query.isEmpty()) {
			return Arrays.asList();
		}

		return this.queryProxy().query(query, KfnctAtdIdRptMon.class)
				.setParameter("companyId", companyId)
				.getList(x -> x.getKfnctAtdIdRptMonPK().getAttendanceItemId());
	}

}