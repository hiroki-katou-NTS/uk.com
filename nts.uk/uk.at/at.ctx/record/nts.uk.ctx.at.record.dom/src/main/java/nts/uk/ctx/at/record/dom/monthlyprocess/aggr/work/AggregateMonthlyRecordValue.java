package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.affiliation.AffiliationInfoOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AnyItemOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata.AbsenceLeaveRemainData;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata.MonthlyDayoffRemainData;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.RsvLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.MonthlyAggregationErrorInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnAndRsvLeave;
import nts.uk.ctx.at.record.dom.weekly.AttendanceTimeOfWeekly;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/*
 * 戻り値：ドメインサービス：月別実績を集計する．集計処理
 */
@Getter
public class AggregateMonthlyRecordValue {

	/** 月別実績の勤怠時間 */
	private List<AttendanceTimeOfMonthly> attendanceTimeList;
	/** 週別実績の勤怠時間 */
	private List<AttendanceTimeOfWeekly> attendanceTimeWeeks;
	/** 月別実績の所属情報 */
	private List<AffiliationInfoOfMonthly> affiliationInfoList;
	/** 月別実績の任意項目 */
	private List<AnyItemOfMonthly> anyItemList;
	/** 管理時間の36協定時間 */
	private List<AgreementTimeOfManagePeriod> agreementTimeList;
	/** 年休月別残数データ */
	private List<AnnLeaRemNumEachMonth> annLeaRemNumEachMonthList;
	/** 積立年休月別残数データ */
	private List<RsvLeaRemNumEachMonth> rsvLeaRemNumEachMonthList;
	/** 振休月別残数データ */
	private List<AbsenceLeaveRemainData> absenceLeaveRemainList;
	/** 代休月別残数データ */
	private List<MonthlyDayoffRemainData> monthlyDayoffRemainList;
	
	/** 年休積立年休の集計結果 */
	@Setter
	private AggrResultOfAnnAndRsvLeave aggrResultOfAnnAndRsvLeave;
	/** エラー情報 */
	private Map<String, MonthlyAggregationErrorInfo> errorInfos;
	/** 中断フラグ */
	@Setter
	private boolean interruption;
	
	/*
	 * コンストラクタ
	 */
	public AggregateMonthlyRecordValue(){
		
		this.attendanceTimeList = new ArrayList<>();
		this.attendanceTimeWeeks = new ArrayList<>();
		this.affiliationInfoList = new ArrayList<>();
		this.anyItemList = new ArrayList<>();
		this.agreementTimeList = new ArrayList<>();
		this.annLeaRemNumEachMonthList = new ArrayList<>();
		this.rsvLeaRemNumEachMonthList = new ArrayList<>();
		this.absenceLeaveRemainList = new ArrayList<>();
		this.monthlyDayoffRemainList = new ArrayList<>();
		
		this.aggrResultOfAnnAndRsvLeave = new AggrResultOfAnnAndRsvLeave();
		this.errorInfos = new HashMap<>();
		this.interruption = false;
	}
	
	/**
	 * エラー情報を追加する
	 * @param resourceId リソースID
	 * @param message エラーメッセージ
	 */
	public void addErrorInfos(String resourceId, ErrMessageContent message){
		this.errorInfos.putIfAbsent(resourceId, new MonthlyAggregationErrorInfo(resourceId, message));
	}
	
	/**
	 * エラー情報に指定のリソースIDがあるかどうか
	 * @param resourceId リソースID
	 * @return true：ある、false：ない
	 */
	public boolean existErrorResource(String resourceId){
		return this.errorInfos.containsKey(resourceId);
	}
	
	/**
	 * 月別実績の所属情報を取得
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日
	 * @return 月別実績の所属情報
	 */
	public Optional<AffiliationInfoOfMonthly> getAffiliationInfo(
			String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate){
		
		for (val affiliationInfo : this.affiliationInfoList){
			if (affiliationInfo.getEmployeeId() == employeeId &&
				affiliationInfo.getYearMonth().equals(yearMonth) &&
				affiliationInfo.getClosureId() == closureId &&
				affiliationInfo.getClosureDate().getClosureDay().equals(closureDate.getClosureDay()) &&
				affiliationInfo.getClosureDate().getLastDayOfMonth() == closureDate.getLastDayOfMonth()){
				
				return Optional.of(affiliationInfo);
			}
		}
		return Optional.empty();
	}
	
	/**
	 * 月別実績の任意項目を取得
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日
	 * @param anyItemId 任意項目ID
	 * @return 月別実績の任意項目
	 */
	public Optional<AnyItemOfMonthly> getAnyItem(String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate, int anyItemId){
		
		for (val anyItem : this.anyItemList){
			if (anyItem.getEmployeeId() == employeeId &&
				anyItem.getYearMonth().equals(yearMonth) &&
				anyItem.getClosureId() == closureId &&
				anyItem.getClosureDate().getClosureDay().equals(closureDate.getClosureDay()) &&
				anyItem.getClosureDate().getLastDayOfMonth() == closureDate.getLastDayOfMonth() &&
				anyItem.getAnyItemId() == anyItemId){
				
				return Optional.of(anyItem);
			}
		}
		return Optional.empty();
	}
	
	/**
	 * 月別実績の任意項目の追加または更新
	 * @param putAnyItem 月別実績の任意項目
	 */
	public void putAnyItemOrUpdate(AnyItemOfMonthly putAnyItem){
		
		val itrAnyItem = this.anyItemList.iterator();
		while (itrAnyItem.hasNext()){
			val anyItem = itrAnyItem.next();
			if (anyItem.getEmployeeId() == putAnyItem.getEmployeeId() &&
				anyItem.getYearMonth().equals(putAnyItem.getYearMonth()) &&
				anyItem.getClosureId() == putAnyItem.getClosureId() &&
				anyItem.getClosureDate().getClosureDay().equals(putAnyItem.getClosureDate().getClosureDay()) &&
				anyItem.getClosureDate().getLastDayOfMonth() == putAnyItem.getClosureDate().getLastDayOfMonth() &&
				anyItem.getAnyItemId() == putAnyItem.getAnyItemId()){

				itrAnyItem.remove();
				break;
			}
		}
		this.anyItemList.add(putAnyItem);
	}
	
	/**
	 * 月別実績の任意項目の合算
	 * @param sumAnyItem 月別実績の任意項目
	 */
	public void sumAnyItem(AnyItemOfMonthly sumAnyItem){
		
		val itrAnyItem = this.anyItemList.iterator();
		while (itrAnyItem.hasNext()){
			val anyItem = itrAnyItem.next();
			if (anyItem.getEmployeeId() == sumAnyItem.getEmployeeId() &&
				anyItem.getYearMonth().equals(sumAnyItem.getYearMonth()) &&
				anyItem.getClosureId() == sumAnyItem.getClosureId() &&
				anyItem.getClosureDate().getClosureDay().equals(sumAnyItem.getClosureDate().getClosureDay()) &&
				anyItem.getClosureDate().getLastDayOfMonth() == sumAnyItem.getClosureDate().getLastDayOfMonth() &&
				anyItem.getAnyItemId() == sumAnyItem.getAnyItemId()){

				anyItem.sum(sumAnyItem);
				return;
			}
		}
		this.anyItemList.add(sumAnyItem);
	}
}
