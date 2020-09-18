package nts.uk.ctx.at.schedule.dom.shift.management.workexpect;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

public interface WorkExpectationOfOneDayRepository {
	
	/*
	 * get
	 * 	勤務希望を取得する
	 */
	public Optional<WorkExpectationOfOneDay> get(String employeeID, GeneralDate expectingDate);
	
	/*
	 * get*
	 * 	期間内の勤務希望を取得する	
	 */
	public List<WorkExpectationOfOneDay> getList(String employeeID, DatePeriod period);
	
	/*
	 * insert
	 */
	public void add(WorkExpectationOfOneDay expectation);
	
	/*
	 * update 
	 */
	public void update(WorkExpectationOfOneDay expectation);
	
	/*
	 * exists 
	 */
	public boolean exists(String employeeID, GeneralDate expectingDate);
	
}
