package nts.uk.ctx.at.record.infra.repository.monthly.information.childnursing;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.information.childnursing.MonChildHdRemain;
import nts.uk.ctx.at.record.dom.monthly.information.childnursing.MonChildHdRemainRepository;
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
public class JpaMonChildHdRemainRepository extends JpaRepository implements MonChildHdRemainRepository{

	@Override
	public Optional<MonChildHdRemain> find(String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MonChildHdRemain> findByYearMonthOrderByStartYmd(String employeeId, YearMonth yearMonth) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MonChildHdRemain> findByYMAndClosureIdOrderByStartYmd(String employeeId, YearMonth yearMonth,
			ClosureId closureId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MonChildHdRemain> findByEmployees(List<String> employeeIds, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MonChildHdRemain> findBySidsAndYearMonths(List<String> employeeIds, List<YearMonth> yearMonths) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MonChildHdRemain> findByDate(String employeeId, GeneralDate criteriaDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MonChildHdRemain> findByPeriodIntoEndYmd(String employeeId, DatePeriod period) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void persistAndUpdate(RemarksMonthlyRecord remarksMonthlyRecord) {
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
