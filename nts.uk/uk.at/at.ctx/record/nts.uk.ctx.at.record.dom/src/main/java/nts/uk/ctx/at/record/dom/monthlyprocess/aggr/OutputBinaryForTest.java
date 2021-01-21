package nts.uk.ctx.at.record.dom.monthlyprocess.aggr;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.serialize.binary.ObjectBinaryFile;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.affiliationinformation.repository.AffiliationInforOfDailyPerforRepository;
import nts.uk.ctx.at.record.dom.affiliationinformation.repository.WorkTypeOfDailyPerforRepository;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.repo.PCLogOnInfoOfDailyRepo;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDailyRepo;
import nts.uk.ctx.at.record.dom.raisesalarytime.repo.SpecificDateAttrOfDailyPerforRepo;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.record.dom.worktime.repository.TemporaryTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.com.ComFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.com.ComDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.com.ComRegulaMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.anyitem.AnyItemOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTSettingRepository;

/**
 * テスト用バイナリデータ出力
 * @author shuichi_ishida
 */
@Stateless
public class OutputBinaryForTest implements OutputBinaryForTestInterface {

	// 会社単位設定
	
	/** 休日加算設定 */
	@Inject
	private HolidayAddtionRepository holidayAddition;
	/** 通常勤務会社別月別実績集計設定 */
	@Inject
	private ComRegulaMonthActCalSetRepo comRegSetRepo;
	/** 変形労働会社別月別実績集計設定 */
	@Inject
	private ComDeforLaborMonthActCalSetRepo comIrgSetRepo;
	/** フレックス会社別月別実績集計設定 */
	@Inject
	private ComFlexMonthActCalSetRepo comFlexSetRepo;
	/** 時間外超過設定の取得 */
	@Inject
	private OutsideOTSettingRepository outsideOTSetRepo;
	
	// 日別実績
	
	/** 日別実績の勤務種別の取得 */
	@Inject
	private WorkTypeOfDailyPerforRepository workTypeOfDaily;
	/** 日別実績の勤怠時間の取得 */
	@Inject
	private AttendanceTimeRepository attendanceTimeOfDaily;
	/** 日別実績の勤務情報の取得 */
	@Inject
	private WorkInformationRepository workInformationOfDaily;
	/** 日別実績の所属情報の取得 */
	@Inject
	private AffiliationInforOfDailyPerforRepository affiliationInfoOfDaily;
	/** 日別実績の出退勤の取得 */
	@Inject
	private TimeLeavingOfDailyPerformanceRepository timeLeavingOfDaily;
	/** 日別実績の臨時出退勤の取得 */
	@Inject
	private TemporaryTimeOfDailyPerformanceRepository temporaryTimeOfDaily;
	/** 日別実績の特定日区分の取得 */
	@Inject
	private SpecificDateAttrOfDailyPerforRepo specificDateAttrOfDaily;
	/** 日別実績のPCログオン情報 */
	@Inject
	private PCLogOnInfoOfDailyRepo PCLogonInfoOfDaily;
	/** 社員の日別実績エラー一覧 */
	@Inject
	private EmployeeDailyPerErrorRepository employeeDailyError;
	/** 日別実績の任意項目の取得 */
	@Inject
	private AnyItemValueOfDailyRepo anyItemValueOfDaily;
	
	// 月別実績

	/** 月別実績の勤怠時間 */
	@Inject
	private AttendanceTimeOfMonthlyRepository attendanceTimeOfMonthly;
	/** 月別実績の任意項目 */
	@Inject
	private AnyItemOfMonthlyRepository anyItemOfMonthly;
	
	@Override
	public void init1(String cid) {
		Map<String, Object> toBinaryMap = this.getByCid(cid);
		val file = Paths.get("c:\\MonBinWithCid.csv");
		ObjectBinaryFile.write(toBinaryMap, file);
	}
	
	@Override
	public void init2(String empId, DatePeriod period) {
		Map<String, Object> toBinaryMap = this.getByEmpIdPeriod(empId, period);
		val file = Paths.get("c:\\MonBinWithEmpIdPeriod.csv");
		ObjectBinaryFile.write(toBinaryMap, file);
	}
	
	@Override
	public void init3(String empId, YearMonth yearMonth) {
		Map<String, Object> toBinaryMap = this.getByEmpIdYearMonth(empId, yearMonth);
		val file = Paths.get("c:\\MonBinWithEmpIdYearMonth.csv");
		ObjectBinaryFile.write(toBinaryMap, file);
	}
	
	public Map<String, Object> getByCid(String cid){
		Map<String, Object> map= new HashMap<>();
		
		val holidayAddition = this.holidayAddition.findByCompanyId(cid);
		if (!holidayAddition.isEmpty()) map.put("HolidayAddition", holidayAddition);
		
		val comRegSet = this.comRegSetRepo.find(cid);
		if (comRegSet.isPresent()) map.put("ComRegSet", comRegSet.get());

		val comIrgSet = this.comIrgSetRepo.find(cid);
		if (comIrgSet.isPresent()) map.put("ComIrgSet", comIrgSet.get());
		
		val comFlexSet = this.comFlexSetRepo.find(cid);
		if (comFlexSet.isPresent()) map.put("ComFlexSet", comFlexSet.get());
		
		val outsideOTSet = this.outsideOTSetRepo.findById(cid);
		if (outsideOTSet.isPresent()) map.put("OutsideOTSet", outsideOTSet.get());
		
		return map;
	}
	
	public Map<String, Object> getByEmpIdPeriod(String empId, DatePeriod period){
		Map<String, Object> map= new HashMap<>();
		List<String> empIds = new ArrayList<>();
		empIds.add(empId);
		
		val workTypes = this.workTypeOfDaily.finds(empIds, period);
		if (workTypes.isEmpty()) throw new RuntimeException("勤務種別が取得できません。");
		map.put("WorkTypeOfDay", workTypes);
		
		val affiliations = this.affiliationInfoOfDaily.finds(empIds, period);
		if (affiliations.isEmpty()) throw new RuntimeException("日別実績の所属情報が取得できません。");
		map.put("AffiliationOfDay", affiliations);
		
		val attendanceTimes = this.attendanceTimeOfDaily.findByPeriodOrderByYmd(empId, period);
		if (!attendanceTimes.isEmpty()) map.put("AttendanceTimeOfDay", attendanceTimes);

		val workInfos = this.workInformationOfDaily.findByPeriodOrderByYmd(empId, period);
		if (!workInfos.isEmpty()) map.put("WorkInfoOfDay", workInfos);

		val timeLeavings = this.timeLeavingOfDaily.findbyPeriodOrderByYmd(empId, period);
		if (!timeLeavings.isEmpty()) map.put("TimeLeavingOfDay", timeLeavings);
		
		val temporaryTimes = this.temporaryTimeOfDaily.findbyPeriodOrderByYmd(empId, period);
		if (!temporaryTimes.isEmpty()) map.put("TemporaryTimeOfDay", temporaryTimes);
		
		val specificDateAttrs = this.specificDateAttrOfDaily.findByPeriodOrderByYmd(empId, period);
		if (!specificDateAttrs.isEmpty()) map.put("SpecificDateAttrOfDay", specificDateAttrs);
		
		val pcLogOnInfos = this.PCLogonInfoOfDaily.finds(empIds, period);
		if (!pcLogOnInfos.isEmpty()) map.put("PCLogOnInfoOfDay", pcLogOnInfos);
		
		val perErrors = this.employeeDailyError.findByPeriodOrderByYmd(empId, period);
		if (!perErrors.isEmpty()) map.put("PerErrorOfDay", perErrors);
		
		val anyItems = this.anyItemValueOfDaily.finds(empIds, period);
		if (!anyItems.isEmpty()) map.put("AnyItemOfDay", anyItems);
		
		return map;
	}
	
	public Map<String, Object> getByEmpIdYearMonth(String empId, YearMonth yearMonth){
		Map<String, Object> map= new HashMap<>();
		
		val attendanceTimes = this.attendanceTimeOfMonthly.findByYearMonthOrderByStartYmd(empId, yearMonth);
		if (!attendanceTimes.isEmpty()) map.put("AttendanceTimeOfMon", attendanceTimes);
		
		val anyItems = this.anyItemOfMonthly.findByMonthly(empId, yearMonth);
		if (!anyItems.isEmpty()) map.put("AnyItemOfMon", anyItems);
		
		return map;
	}
}
