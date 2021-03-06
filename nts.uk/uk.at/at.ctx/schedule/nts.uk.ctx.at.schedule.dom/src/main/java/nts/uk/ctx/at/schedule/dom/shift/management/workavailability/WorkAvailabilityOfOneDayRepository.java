package nts.uk.ctx.at.schedule.dom.shift.management.workavailability;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

public interface WorkAvailabilityOfOneDayRepository {
	
	/*
	 * get
	 * 	勤務希望を取得する
	 */
	public Optional<WorkAvailabilityOfOneDay> get(String employeeID, GeneralDate availabilityDate);
	
	/*
	 * get*
	 * 	期間内の勤務希望を取得する	
	 */
	public List<WorkAvailabilityOfOneDay> getList(String employeeID, DatePeriod period);
	
	/*
	 * insert
	 */
	public void add(WorkAvailabilityOfOneDay workAvailability);
	
	/*
	 * update 
	 */
	public void update(WorkAvailabilityOfOneDay workAvailability);
	
	/*
	 * exists 
	 */
	public boolean exists(String employeeID, GeneralDate availabilityDate);
	
	/*
	 * [6] insertAll (一日分の勤務希望リスト)
	 */
	public void insertAll(List<WorkAvailabilityOfOneDay> lstWorkAvailabilityOfOneDay);
	/*
	 * [7] deleteAll (社員ID, 期間)
	 */
	public void deleteAll(String empID , DatePeriod datePeriod);
}
