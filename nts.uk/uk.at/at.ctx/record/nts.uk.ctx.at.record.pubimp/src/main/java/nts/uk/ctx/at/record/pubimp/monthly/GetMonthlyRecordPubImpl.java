package nts.uk.ctx.at.record.pubimp.monthly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.attendanceitem.util.AttendanceItemConvertFactory;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.affiliation.AffiliationInfoOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.affiliation.AffiliationInfoOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AnyItemOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AnyItemOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonthRepository;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.RsvLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.RsvLeaRemNumEachMonthRepository;
import nts.uk.ctx.at.record.pub.monthly.GetMonthlyRecordPub;
import nts.uk.ctx.at.record.pub.monthly.MonthlyRecordValuesExport;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

/**
 * 実装：月別実績データを取得する
 * @author shuichu_ishida
 */
@Stateless
public class GetMonthlyRecordPubImpl implements GetMonthlyRecordPub {

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
	@Override
	public List<MonthlyRecordValuesExport> algorithm(String employeeId, YearMonthPeriod period, List<Integer> itemIds) {
		
		List<MonthlyRecordValuesExport> results = new ArrayList<>();

		// 検索キーを準備する
		val yearMonths = ConvertHelper.yearMonthsBetween(period);
		List<String> employeeIds = new ArrayList<>();
		employeeIds.add(employeeId);
		
		// 月別実績の勤怠時間を取得する
		val attendanceTimes = this.attendanceTimeRepo.findBySidsAndYearMonths(employeeIds, yearMonths);
		Map<YearMonth, AttendanceTimeOfMonthly> attendanceTimeMap = new HashMap<>();
		for (val attendanceTime : attendanceTimes){
			attendanceTimeMap.putIfAbsent(attendanceTime.getYearMonth(), attendanceTime);
		}
		
		// 月別実績の所属情報を取得する
		val affiliationInfos = this.affiliationInfoRepo.findBySidsAndYearMonths(employeeIds, yearMonths);
		Map<YearMonth, AffiliationInfoOfMonthly> affiliationInfoMap = new HashMap<>();
		for (val affiliationInfo : affiliationInfos){
			affiliationInfoMap.putIfAbsent(affiliationInfo.getYearMonth(), affiliationInfo);
		}
		
		// 月別実績の任意項目を取得する
		val anyItems = this.anyItemRepo.findBySidsAndMonths(employeeIds, yearMonths);
		Map<YearMonth, List<AnyItemOfMonthly>> anyItemMap = new HashMap<>();
		for (val anyItem : anyItems){
			val dataMonth = anyItem.getYearMonth();
			if (!anyItemMap.containsKey(dataMonth)) anyItemMap.put(dataMonth, new ArrayList<>());
			anyItemMap.get(dataMonth).add(anyItem);
		}
		
		// 年休月別残数データを取得する
		val annualLeaves = this.annualLeaveRepo.findBySidsAndYearMonths(employeeIds, yearMonths);
		Map<YearMonth, AnnLeaRemNumEachMonth> annualLeaveMap = new HashMap<>();
		for (val annualLeave : annualLeaves){
			annualLeaveMap.putIfAbsent(annualLeave.getYearMonth(), annualLeave);
		}
		
		// 積立年休月別残数データを取得する
		val reserveLeaves = this.reserveLeaveRepo.findBySidsAndYearMonths(employeeIds, yearMonths);
		Map<YearMonth, RsvLeaRemNumEachMonth> reserveLeaveMap = new HashMap<>();
		for (val reserveLeave : reserveLeaves){
			reserveLeaveMap.putIfAbsent(reserveLeave.getYearMonth(), reserveLeave);
		}
		
		for (val yearMonth : yearMonths){

			// 勤怠項目値リストに変換する準備をする
			val monthlyConverter = this.attendanceItemConverterFact.createMonthlyConverter();
			if (!attendanceTimeMap.containsKey(yearMonth)) continue;
			monthlyConverter.withAttendanceTime(attendanceTimeMap.get(yearMonth));
			if (affiliationInfoMap.containsKey(yearMonth)){
				monthlyConverter.withAffiliation(affiliationInfoMap.get(yearMonth));
			}
			if (anyItemMap.containsKey(yearMonth)){
				monthlyConverter.withAnyItem(anyItemMap.get(yearMonth));
			}
			if (annualLeaveMap.containsKey(yearMonth)){
				monthlyConverter.withAnnLeave(annualLeaveMap.get(yearMonth));
			}
			if (reserveLeaveMap.containsKey(yearMonth)){
				monthlyConverter.withRsvLeave(reserveLeaveMap.get(yearMonth));
			}
			
			// 月別実績データ値リストに追加する
			results.add(MonthlyRecordValuesExport.of(
					yearMonth,
					monthlyConverter.convert(itemIds)));
		}
		
		return results;
	}
}
