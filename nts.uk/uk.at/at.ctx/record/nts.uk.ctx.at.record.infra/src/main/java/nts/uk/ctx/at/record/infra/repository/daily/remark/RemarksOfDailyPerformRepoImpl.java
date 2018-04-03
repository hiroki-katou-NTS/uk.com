package nts.uk.ctx.at.record.infra.repository.daily.remark;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.remarks.RemarksOfDailyPerform;
import nts.uk.ctx.at.record.dom.daily.remarks.RemarksOfDailyPerformRepo;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class RemarksOfDailyPerformRepoImpl extends JpaRepository implements RemarksOfDailyPerformRepo {

	@Override
	public List<RemarksOfDailyPerform> getRemarks(String employeeId, GeneralDate workingDate) {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	@Override
	public List<RemarksOfDailyPerform> getRemarks(List<String> employeeId, DatePeriod baseDate) {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	@Override
	public List<RemarksOfDailyPerform> getRemarks(String employeeId, List<GeneralDate> baseDate) {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	@Override
	public void update(RemarksOfDailyPerform domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(RemarksOfDailyPerform domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String employeeId, GeneralDate workingDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(List<RemarksOfDailyPerform> domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(List<RemarksOfDailyPerform> domain) {
		// TODO Auto-generated method stub
		
	}

}
