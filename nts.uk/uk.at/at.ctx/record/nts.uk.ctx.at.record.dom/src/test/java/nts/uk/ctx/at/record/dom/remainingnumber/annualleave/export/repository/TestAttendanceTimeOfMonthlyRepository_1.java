package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.repository;

import java.util.List;
import java.util.Optional;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.affiliation.AffiliationInfoOfMonthly;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * 月別実績の勤怠時間
 * @author masaaki_jinno
 *
 */
public class TestAttendanceTimeOfMonthlyRepository_1 extends JpaRepository implements AttendanceTimeOfMonthlyRepository {
	
//	@Inject
//	private TimeOfMonthlyRepository mergeRepo;

	/** 検索 */
	@Override
	public Optional<AttendanceTimeOfMonthly> find(String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate) {
//		return mergeRepo.find(employeeId, yearMonth, closureId, closureDate).map(c -> c.getAttendanceTime().orElse(null));
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
        return null;
	}

	/** 検索　（年月） */
	@Override
	public List<AttendanceTimeOfMonthly> findByYearMonthOrderByStartYmd(String employeeId, YearMonth yearMonth) {
//		return mergeRepo.findByYearMonthOrderByStartYmd(employeeId, yearMonth).stream()
//				.map(c -> c.getAttendanceTime().orElse(null)).filter(c -> c != null).collect(Collectors.toList());
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
        return null;
	}
	
	/** 検索　（年月と締めID） */
	@Override
	public List<AttendanceTimeOfMonthly> findByYMAndClosureIdOrderByStartYmd(String employeeId, YearMonth yearMonth,
			ClosureId closureId) {
//		return mergeRepo.findByYMAndClosureIdOrderByStartYmd(employeeId, yearMonth, closureId).stream()
//				.map(c -> c.getAttendanceTime().orElse(null)).filter(c -> c != null).collect(Collectors.toList());
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
        return null;
	}

	/** 検索　（社員IDリスト） */
	@Override
	public List<AttendanceTimeOfMonthly> findByEmployees(List<String> employeeIds, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate) {
		
//		return mergeRepo.findByEmployees(employeeIds, yearMonth, closureId, closureDate).stream()
//				.map(c -> c.getAttendanceTime().orElse(null)).filter(c -> c != null).collect(Collectors.toList());
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
        return null;
	}
	
	/** 検索　（社員IDリストと年月リスト） */
	@Override
	public List<AttendanceTimeOfMonthly> findBySidsAndYearMonths(List<String> employeeIds, List<YearMonth> yearMonths) {
		
//		return mergeRepo.findBySidsAndYearMonths(employeeIds, yearMonths).stream()
//				.map(c -> c.getAttendanceTime().orElse(null)).filter(c -> c != null).collect(Collectors.toList());
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
        return null;
	}
		
	/** 検索　（基準日） */
	@Override
	public List<AttendanceTimeOfMonthly> findByDate(String employeeId, GeneralDate criteriaDate) {
//		return mergeRepo.findByDate(employeeId, criteriaDate).stream()
//				.map(c -> c.getAttendanceTime().orElse(null)).filter(c -> c != null).collect(Collectors.toList());
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
        return null;
	}
	
	/** 検索　（終了日を含む期間） */
	@Override
	public List<AttendanceTimeOfMonthly> findByPeriodIntoEndYmd(String employeeId, DatePeriod period) {
		
//		return mergeRepo.findByPeriodIntoEndYmd(employeeId, period).stream()
//				.map(c -> c.getAttendanceTime().orElse(null)).filter(c -> c != null).collect(Collectors.toList());
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
        return null;
	}
			
	/** 登録および更新 */
	@Override
	public void persistAndUpdate(AttendanceTimeOfMonthly domain, Optional<AffiliationInfoOfMonthly> affiliation){

//		mergeRepo.persistAndUpdate(new TimeOfMonthly(Optional.of(domain), affiliation));
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
	}
	
	/** 削除 */
	@Override
	public void remove(String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate) {
		
//		mergeRepo.removeAttendanceTime(employeeId, yearMonth, closureId, closureDate);
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
	}
		
	/** 削除　（年月） */
	@Override
	public void removeByYearMonth(String employeeId, YearMonth yearMonth) {
		
//		mergeRepo.removeAttendanceTime(employeeId, yearMonth);
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
	}
}
