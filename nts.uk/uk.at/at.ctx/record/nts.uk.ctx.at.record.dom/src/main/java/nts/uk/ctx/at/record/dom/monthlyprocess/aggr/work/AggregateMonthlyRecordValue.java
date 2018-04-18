package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.affiliation.AffiliationInfoOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.MonthlyAggregationErrorInfo;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;

/*
 * 戻り値：ドメインサービス：月別実績を集計する．集計処理
 */
@Getter
public class AggregateMonthlyRecordValue {

	/** 月別実績の勤怠時間 */
	private List<AttendanceTimeOfMonthly> attendanceTimeList;

	/** 月別実績の所属情報 */
	private List<AffiliationInfoOfMonthly> affiliationInfoList;
	
	/** 管理時間の36協定時間 */
	private List<AgreementTimeOfManagePeriod> agreementTimeList;
	
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
		this.affiliationInfoList = new ArrayList<>();
		this.agreementTimeList = new ArrayList<>();
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
}
