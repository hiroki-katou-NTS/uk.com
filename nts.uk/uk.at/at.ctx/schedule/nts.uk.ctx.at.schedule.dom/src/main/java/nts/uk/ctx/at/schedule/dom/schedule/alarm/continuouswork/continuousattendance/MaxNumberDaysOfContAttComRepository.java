package nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.continuousattendance;

import java.util.Optional;

/**
 * 会社の連続出勤できる上限日数Repository
 * @author hiroko_miura
 *
 */
public interface MaxNumberDaysOfContAttComRepository {

	void insert (MaxNumberDaysOfContinuousAttendanceCom maxContAttCom);
	
	void update (MaxNumberDaysOfContinuousAttendanceCom maxContAttCom);
	
	void delete(String companyId);
	
	boolean exists(String companyId);
	
	Optional<MaxNumberDaysOfContinuousAttendanceCom> get (String companyId);
}
