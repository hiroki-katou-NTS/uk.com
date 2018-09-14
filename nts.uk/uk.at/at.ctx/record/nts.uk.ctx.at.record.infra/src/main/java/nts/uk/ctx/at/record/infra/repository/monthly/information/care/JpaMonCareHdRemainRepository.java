package nts.uk.ctx.at.record.infra.repository.monthly.information.care;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.information.care.MonCareHdRemain;
import nts.uk.ctx.at.record.dom.monthly.information.care.MonCareHdRemainRepository;
import nts.uk.ctx.at.record.dom.monthly.remarks.RemarksMonthlyRecord;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class JpaMonCareHdRemainRepository extends JpaRepository implements MonCareHdRemainRepository{

	@Override
	public Optional<MonCareHdRemain> find(String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<MonCareHdRemain> findByYearMonthOrderByStartYmd(String employeeId, YearMonth yearMonth) {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	@Override
	public List<MonCareHdRemain> findByYMAndClosureIdOrderByStartYmd(String employeeId, YearMonth yearMonth,
			ClosureId closureId) {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	@Override
	public List<MonCareHdRemain> findByEmployees(List<String> employeeIds, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate) {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	@Override
	public List<MonCareHdRemain> findBySidsAndYearMonths(List<String> employeeIds, List<YearMonth> yearMonths) {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	@Override
	public List<MonCareHdRemain> findByDate(String employeeId, GeneralDate criteriaDate) {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	@Override
	public List<MonCareHdRemain> findByPeriodIntoEndYmd(String employeeId, DatePeriod period) {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	@Override
	public void persistAndUpdate(MonCareHdRemain monCare) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeByYearMonth(String employeeId, YearMonth yearMonth) {
		// TODO Auto-generated method stub
		
	}

}
