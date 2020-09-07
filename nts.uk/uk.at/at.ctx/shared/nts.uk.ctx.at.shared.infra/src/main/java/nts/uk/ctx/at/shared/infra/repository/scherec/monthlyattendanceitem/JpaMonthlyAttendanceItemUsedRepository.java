package nts.uk.ctx.at.shared.infra.repository.scherec.monthlyattendanceitem;

import java.util.List;
import java.util.stream.Collectors;

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

	private static final String SELECT_MONTHLY_ATTENDANCE_ITEM = "SELECT d FROM KfnctAtdIdRptMon"
			+ " WHERE d.KfnctAtdIdRptMonPK.companyId = :companyId"
			+ "		AND (d.atdWorkAttendance = :reportId"
			+ "			OR d.workMonthly = :reportId"
			+ "			OR d.workYearly = :reportId"
			+ "			OR d.workPeriod = :reportId)"
			+ "			OR d.atdWorkAttendance = :reportId)"
			+ "			OR d.atdWorkYearly = :reportId)";

	@Override
	public List<Integer> getAllMonthlyItemId(String companyId, int reportId) {		
		return this.queryProxy().query(SELECT_MONTHLY_ATTENDANCE_ITEM, KfnctAtdIdRptMon.class)
				.setParameter("companyId", companyId)
				.setParameter("reportId", reportId)
				.getList()
				.stream()
				.map(x -> x.getKfnctAtdIdRptMonPK().getAttendanceItemId())
				.collect(Collectors.toList());
	}

}
