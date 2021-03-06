package nts.uk.file.at.app.export.monthlyschedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.service.AttendanceItemConvertFactory;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.affiliation.AffiliationInfoOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.affiliation.AffiliationInfoOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.anyitem.AnyItemOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.anyitem.AnyItemOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnLeaRemNumEachMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnLeaRemNumEachMonthRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave.RsvLeaRemNumEachMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave.RsvLeaRemNumEachMonthRepository;

@Stateless
public class MonthlyAttendanceResultImportAdapter {
	/** 月別実績の勤怠時間 */
	@Inject
	private AttendanceTimeOfMonthlyRepository attendanceTimeRepo;
	/** 月別実績の所属情報 */
	@Inject
	private AffiliationInfoOfMonthlyRepository affiliationInfoRepo;
	/** 月別実績の任意項目 */
	@Inject
	private AnyItemOfMonthlyRepository anyItemRepo;
	/** 年休月別残数データ */
	@Inject
	private AnnLeaRemNumEachMonthRepository annualLeaveRepo;
	/** 積立年休月別残数データ */
	@Inject
	private RsvLeaRemNumEachMonthRepository reserveLeaveRepo;
	/** 勤怠項目変換 */
	@Inject
	private AttendanceItemConvertFactory attendanceItemConverterFact;
	
	/** 月別実績データを取得する */
	public List<MonthlyRecordValuesExport> algorithm(String employeeId, YearMonthPeriod period, List<Integer> itemIds) {
		
		// 検索キーを準備する
		List<String> employeeIds = new ArrayList<>();
		employeeIds.add(employeeId);
		
		// 月別実績データを取得する
		val results = this.algorithm(employeeIds, period, itemIds);
		if (results.containsKey(employeeId)) return results.get(employeeId);
		
		return new ArrayList<>();
	}
	
	/** 月別実績データを取得する */
	public Map<String, List<MonthlyRecordValuesExport>> algorithm(List<String> employeeIds, YearMonthPeriod period,
			List<Integer> itemIds) {
		
		Map<String, List<MonthlyRecordValuesExport>> results = new HashMap<>();

		// 検索キーを準備する
		val yearMonths = ConvertHelper.yearMonthsBetween(period);
		
		// 月別実績の所属情報を取得する
		val affiliationInfos = this.affiliationInfoRepo.findBySidsAndYearMonths(employeeIds, yearMonths);
		Map<AttendanceTimeOfMonthlyKey, AffiliationInfoOfMonthly> affiliationInfoMap = new HashMap<>();
		for (val affiliationInfo : affiliationInfos){
			AttendanceTimeOfMonthlyKey key = new AttendanceTimeOfMonthlyKey(
					affiliationInfo.getEmployeeId(),
					affiliationInfo.getYearMonth(),
					affiliationInfo.getClosureId(),
					affiliationInfo.getClosureDate());
			affiliationInfoMap.putIfAbsent(key, affiliationInfo);
		}
		
		// 月別実績の任意項目を取得する
		val anyItems = this.anyItemRepo.findBySidsAndMonths(employeeIds, yearMonths);
		Map<AttendanceTimeOfMonthlyKey, List<AnyItemOfMonthly>> anyItemMap = new HashMap<>();
		for (val anyItem : anyItems){
			AttendanceTimeOfMonthlyKey key = new AttendanceTimeOfMonthlyKey(
					anyItem.getEmployeeId(),
					anyItem.getYearMonth(),
					anyItem.getClosureId(),
					anyItem.getClosureDate());
			anyItemMap.putIfAbsent(key, new ArrayList<>());
			anyItemMap.get(key).add(anyItem);
		}
		
		// 年休月別残数データを取得する
		val annualLeaves = this.annualLeaveRepo.findBySidsAndYearMonths(employeeIds, yearMonths);
		Map<AttendanceTimeOfMonthlyKey, AnnLeaRemNumEachMonth> annualLeaveMap = new HashMap<>();
		for (val annualLeave : annualLeaves){
			AttendanceTimeOfMonthlyKey key = new AttendanceTimeOfMonthlyKey(
					annualLeave.getEmployeeId(),
					annualLeave.getYearMonth(),
					annualLeave.getClosureId(),
					annualLeave.getClosureDate());
			annualLeaveMap.putIfAbsent(key, annualLeave);
		}
		
		// 積立年休月別残数データを取得する
		val reserveLeaves = this.reserveLeaveRepo.findBySidsAndYearMonths(employeeIds, yearMonths);
		Map<AttendanceTimeOfMonthlyKey, RsvLeaRemNumEachMonth> reserveLeaveMap = new HashMap<>();
		for (val reserveLeave : reserveLeaves){
			AttendanceTimeOfMonthlyKey key = new AttendanceTimeOfMonthlyKey(
					reserveLeave.getEmployeeId(),
					reserveLeave.getYearMonth(),
					reserveLeave.getClosureId(),
					reserveLeave.getClosureDate());
			reserveLeaveMap.putIfAbsent(key, reserveLeave);
		}
		
		// 月別実績の勤怠時間を取得する
		val attendanceTimes = this.attendanceTimeRepo.findBySidsAndYearMonths(employeeIds, yearMonths);
		for (val attendanceTime : attendanceTimes){
			val employeeId = attendanceTime.getEmployeeId();
			AttendanceTimeOfMonthlyKey key = new AttendanceTimeOfMonthlyKey(
					employeeId,
					attendanceTime.getYearMonth(),
					attendanceTime.getClosureId(),
					attendanceTime.getClosureDate());
			
			// 勤怠項目値リストに変換する準備をする
			val monthlyConverter = this.attendanceItemConverterFact.createMonthlyConverter();
			monthlyConverter.withAttendanceTime(attendanceTime);
			if (affiliationInfoMap.containsKey(key)){
				monthlyConverter.withAffiliation(affiliationInfoMap.get(key));
			}
			if (anyItemMap.containsKey(key)){
				monthlyConverter.withAnyItem(anyItemMap.get(key));
			}
			if (annualLeaveMap.containsKey(key)){
				monthlyConverter.withAnnLeave(annualLeaveMap.get(key));
			}
			if (reserveLeaveMap.containsKey(key)){
				monthlyConverter.withRsvLeave(reserveLeaveMap.get(key));
			}
			
			// 月別実績データ値リストに追加する
			results.putIfAbsent(employeeId, new ArrayList<>());
			results.get(employeeId).add(MonthlyRecordValuesExport.of(
					attendanceTime.getYearMonth(),
					attendanceTime.getClosureId(),
					attendanceTime.getClosureDate(),
					monthlyConverter.convert(itemIds)));
		}
		
		return results;
	}
	
	/** 月別実績の勤怠時間を取得する */
	public Optional<AttendanceTimeOfMonthly> getAttendanceTime(String employeeId, YearMonth yearMonth) {
		
		val attendanceTimes = this.attendanceTimeRepo.findByYearMonthOrderByStartYmd(employeeId, yearMonth);
		AttendanceTimeOfMonthly result = null;
		for (val attendanceTime : attendanceTimes){
			if (result == null){
				result = attendanceTime;
				continue;
			}
			attendanceTime.sum(result);
			result = attendanceTime;
		}
		return Optional.ofNullable(result);
	}
	
	/** 月別実績の値を取得する */
	public Map<String, List<MonthlyRecordValuesExport>> getRecordValues(List<String> employeeIds,
			YearMonthPeriod period, List<Integer> itemIds) {
		
		Map<String, List<MonthlyRecordValuesExport>> results = new HashMap<>();

		// 検索キーを準備する
		val yearMonths = ConvertHelper.yearMonthsBetween(period);
		
		// 月別実績の勤怠時間を取得する
		val attendanceTimes = this.attendanceTimeRepo.findBySidsAndYearMonths(employeeIds, yearMonths);
		Map<String, Map<YearMonth, AttendanceTimeOfMonthly>> attendanceTimeMap = new HashMap<>();
		for (val attendanceTime : attendanceTimes){
			val employeeId = attendanceTime.getEmployeeId();
			val yearMonth = attendanceTime.getYearMonth();
			attendanceTimeMap.putIfAbsent(employeeId, new HashMap<>());
			if (attendanceTimeMap.get(employeeId).containsKey(yearMonth)){
				attendanceTime.sum(attendanceTimeMap.get(employeeId).get(yearMonth));
				attendanceTimeMap.get(employeeId).put(yearMonth, attendanceTime);
				continue;
			}
			attendanceTimeMap.get(employeeId).putIfAbsent(yearMonth, attendanceTime);
		}
		
		// 月別実績の所属情報を取得する
		val affiliationInfos = this.affiliationInfoRepo.findBySidsAndYearMonths(employeeIds, yearMonths);
		Map<String, Map<YearMonth, AffiliationInfoOfMonthly>> affiliationInfoMap = new HashMap<>();
		for (val affiliationInfo : affiliationInfos){
			val employeeId = affiliationInfo.getEmployeeId();
			val yearMonth = affiliationInfo.getYearMonth();
			affiliationInfoMap.putIfAbsent(employeeId, new HashMap<>());
			affiliationInfoMap.get(employeeId).putIfAbsent(yearMonth, affiliationInfo);
		}
		
		// 月別実績の任意項目を取得する
		val anyItems = this.anyItemRepo.findBySidsAndMonths(employeeIds, yearMonths);
		Map<String, Map<YearMonth, List<AnyItemOfMonthly>>> anyItemMap = new HashMap<>();
		for (val anyItem : anyItems){
			val employeeId = anyItem.getEmployeeId();
			val yearMonth = anyItem.getYearMonth();
			anyItemMap.putIfAbsent(employeeId, new HashMap<>());
			anyItemMap.get(employeeId).putIfAbsent(yearMonth, new ArrayList<>());
			ListIterator<AnyItemOfMonthly> itrAnyItem = anyItemMap.get(employeeId).get(yearMonth).listIterator();
			boolean isSum = false;
			while (itrAnyItem.hasNext()){
				val outAnyItem = itrAnyItem.next();
				if (outAnyItem.getAnyItemId() == anyItem.getAnyItemId()){
					outAnyItem.sum(anyItem);
					itrAnyItem.set(outAnyItem);
					isSum = true;
					break;
				}
			}
			if (!isSum) anyItemMap.get(employeeId).get(yearMonth).add(anyItem);
		}
		
		// 年休月別残数データを取得する
		val annualLeaves = this.annualLeaveRepo.findBySidsAndYearMonths(employeeIds, yearMonths);
		Map<String, Map<YearMonth, AnnLeaRemNumEachMonth>> annualLeaveMap = new HashMap<>();
		for (val annualLeave : annualLeaves){
			val employeeId = annualLeave.getEmployeeId();
			val yearMonth = annualLeave.getYearMonth();
			annualLeaveMap.putIfAbsent(employeeId, new HashMap<>());
			annualLeaveMap.get(employeeId).put(yearMonth, annualLeave);
		}
		
		// 積立年休月別残数データを取得する
		val reserveLeaves = this.reserveLeaveRepo.findBySidsAndYearMonths(employeeIds, yearMonths);
		Map<String, Map<YearMonth, RsvLeaRemNumEachMonth>> reserveLeaveMap = new HashMap<>();
		for (val reserveLeave : reserveLeaves){
			val employeeId = reserveLeave.getEmployeeId();
			val yearMonth = reserveLeave.getYearMonth();
			reserveLeaveMap.putIfAbsent(employeeId, new HashMap<>());
			reserveLeaveMap.get(employeeId).put(yearMonth, reserveLeave);
		}
		
		for (val employeeId : employeeIds){
			for (val yearMonth : yearMonths){
				if (!attendanceTimeMap.containsKey(employeeId)) continue;
				if (!attendanceTimeMap.get(employeeId).containsKey(yearMonth)) continue;
				val attendanceTime = attendanceTimeMap.get(employeeId).get(yearMonth);

				// 勤怠項目値リストに変換する準備をする
				val monthlyConverter = this.attendanceItemConverterFact.createMonthlyConverter();
				monthlyConverter.withAttendanceTime(attendanceTime);
				if (affiliationInfoMap.containsKey(employeeId)){
					if (affiliationInfoMap.get(employeeId).containsKey(yearMonth)){
						monthlyConverter.withAffiliation(affiliationInfoMap.get(employeeId).get(yearMonth));
					}
				}
				if (anyItemMap.containsKey(employeeId)){
					if (anyItemMap.get(employeeId).containsKey(yearMonth)){
						monthlyConverter.withAnyItem(anyItemMap.get(employeeId).get(yearMonth));
					}
				}
				if (annualLeaveMap.containsKey(employeeId)){
					if (annualLeaveMap.get(employeeId).containsKey(yearMonth)){
						monthlyConverter.withAnnLeave(annualLeaveMap.get(employeeId).get(yearMonth));
					}
				}
				if (reserveLeaveMap.containsKey(employeeId)){
					if (reserveLeaveMap.get(employeeId).containsKey(yearMonth)){
						monthlyConverter.withRsvLeave(reserveLeaveMap.get(employeeId).get(yearMonth));
					}
				}
				
				// 月別実績データ値リストに追加する
				results.putIfAbsent(employeeId, new ArrayList<>());
				results.get(employeeId).add(MonthlyRecordValuesExport.of(
						yearMonth,
						attendanceTime.getClosureId(),
						attendanceTime.getClosureDate(),
						monthlyConverter.convert(itemIds)));
			}
		}
		
		return results;
	}
}
