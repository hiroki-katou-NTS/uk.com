package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.monthly.roundingset.RoundingSetOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.VacationAddSet;
import nts.uk.ctx.at.record.dom.monthly.vtotalmethod.PayItemCountOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.vtotalmethod.VerticalTotalMethodOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.workform.flex.MonthlyAggrSetOfFlex;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.legaltransferorder.LegalTransferOrderSetOfAggrMonthly;
import nts.uk.ctx.at.record.dom.optitem.OptionalItem;
import nts.uk.ctx.at.record.dom.optitem.applicable.EmpCondition;
import nts.uk.ctx.at.record.dom.optitem.calculation.Formula;
import nts.uk.ctx.at.record.dom.standardtime.AgreementOperationSetting;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComDeforLaborMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComFlexMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComRegulaMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.outsideot.OutsideOTSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.UsageUnitSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.workrecord.monthlyresults.roleofovertimework.RoleOvertimeWork;
import nts.uk.ctx.at.shared.dom.workrecord.monthlyresults.roleopenperiod.RoleOfOpenPeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.statutoryworktime.flex.GetFlexPredWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.i18n.TextResource;

/**
 * 月別集計で必要な会社別設定
 * @author shuichu_ishida
 */
public class MonAggrCompanySettings {

	/** 会社ID */
	@Getter
	private String companyId;
	/** 勤務種類 */
	private Map<String, WorkType> workTypeMap;
	/** 就業時間帯：共通設定 */
	private Map<String, WorkTimezoneCommonSet> workTimeCommonSetMap;
	/** 所定時間設定 */
	private Map<String, PredetemineTimeSetting> predetermineTimeSetMap;
	/** 締め */
	@Getter
	private Map<Integer, Closure> closureMap;
	/** 法定内振替順設定 */
	@Getter
	private LegalTransferOrderSetOfAggrMonthly legalTransferOrderSet;
	/** 残業枠の役割 */
	@Getter
	private List<RoleOvertimeWork> roleOverTimeFrameList;
	/** 休出枠の役割 */
	@Getter
	private List<RoleOfOpenPeriod> roleHolidayWorkFrameList;
	/** 休暇時間加算設定 */
	@Getter
	private Map<String, AggregateRoot> holidayAdditionMap;
	/** 労働時間と日数の設定の利用単位の設定 */
	@Getter
	private UsageUnitSetting usageUnitSet;
	/** 通常勤務会社別月別実績集計設定 */
	@Getter
	private Optional<ComRegulaMonthActCalSet> comRegSetOpt;
	/** 変形労働会社別月別実績集計設定 */
	@Getter
	private Optional<ComDeforLaborMonthActCalSet> comIrgSetOpt;
	/** フレックス会社別月別実績集計設定 */
	@Getter
	private Optional<ComFlexMonthActCalSet> comFlexSetOpt;
	/** フレックス勤務の月別集計設定 */
	@Getter
	private MonthlyAggrSetOfFlex aggrSetOfFlex;
	/** フレックス勤務所定労働時間 */
	@Getter
	private GetFlexPredWorkTime flexPredWorkTime;
	/** 休暇加算設定 */
	@Getter
	private VacationAddSet vacationAddSet;
	/** 時間外超過設定 */
	@Getter
	private OutsideOTSetting outsideOverTimeSet;
	/** 丸め設定 */
	@Getter
	private RoundingSetOfMonthly roundingSet;
	/** 月別実績の給与項目カウント */
	@Getter
	private PayItemCountOfMonthly payItemCount;
	/** 月別実績の縦計方法 */
	@Getter
	private VerticalTotalMethodOfMonthly verticalTotalMethod;
	/** 36協定運用設定 */
	@Getter
	private Optional<AgreementOperationSetting> agreementOperationSet;
	/** 任意項目 */
	@Getter
	private Map<Integer, OptionalItem> optionalItemMap;
	/** 適用する雇用条件 */
	@Getter
	private Map<Integer, EmpCondition> empConditionMap;
	/** 計算式 */
	@Getter
	private List<Formula> formulaList;
	/** 年休設定 */
	@Getter
	private AnnualPaidLeaveSetting annualLeaveSet;
	
	/** エラー情報 */
	@Getter
	private Map<String, ErrMessageContent> errorInfos;
	
	private MonAggrCompanySettings(String companyId){
		this.companyId = companyId;
		this.workTypeMap = new HashMap<>();
		this.workTimeCommonSetMap = new HashMap<>();
		this.predetermineTimeSetMap = new HashMap<>();
		this.closureMap = new HashMap<>();
		this.roleOverTimeFrameList = new ArrayList<>();
		this.roleHolidayWorkFrameList = new ArrayList<>();
		this.holidayAdditionMap = new HashMap<>();
		this.comRegSetOpt = Optional.empty();
		this.comIrgSetOpt = Optional.empty();
		this.comFlexSetOpt = Optional.empty();
		this.agreementOperationSet = Optional.empty();
		this.optionalItemMap = new HashMap<>();
		this.empConditionMap = new HashMap<>();
		this.formulaList = new ArrayList<>();
		this.errorInfos = new HashMap<>();
	}
	
	/**
	 * 設定読み込み
	 * @param companyId 会社ID
	 * @param repositories 月別集計が必要とするリポジトリ
	 * @return 月別集計で必要な会社別設定
	 */
	public static MonAggrCompanySettings loadSettings(
			String companyId,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		final String resourceId = "001";
		
		MonAggrCompanySettings domain = new MonAggrCompanySettings(companyId);
		
		// 月別実績の給与項目カウント　取得
		domain.payItemCount = new PayItemCountOfMonthly(companyId);
		val payItemCountOpt = repositories.getPayItemCountOfMonthly().find(companyId);
		if (payItemCountOpt.isPresent()) domain.payItemCount = payItemCountOpt.get();
		
		// 月別実績の縦計方法　取得
		//*****（未）　設計待ち。特定日設定部分が保留中。仮に空条件で。
		domain.verticalTotalMethod = new VerticalTotalMethodOfMonthly(companyId);
		
		// 任意項目
		val optionalItems = repositories.getOptionalItem().findAll(companyId);
		List<Integer> optionalItemNoList = new ArrayList<>();
		for (val optionalItem : optionalItems){
			domain.optionalItemMap.put(optionalItem.getOptionalItemNo().v(), optionalItem);
			optionalItemNoList.add(optionalItem.getOptionalItemNo().v());
		}
		
		// 適用する雇用条件
		val empConditions = repositories.getEmpCondition().findAll(companyId, optionalItemNoList);
		for (val empCondifion : empConditions){
			domain.empConditionMap.put(empCondifion.getOptItemNo().v(), empCondifion);
		}
		
		// 計算式
		domain.formulaList = repositories.getFormula().find(companyId);
		
		// 年休設定
		domain.annualLeaveSet = repositories.getAnnualPaidLeaveSet().findByCompanyId(companyId);

		// 設定読み込み処理　（36協定時間用）
		domain.loadSettingsForAgreementProc(companyId, resourceId, repositories);
		
		return domain;
	}
	
	/**
	 * 設定読み込み　（36協定時間用）
	 * @param companyId 会社ID
	 * @param repositories 月別集計が必要とするリポジトリ
	 * @return 月別集計で必要な会社別設定
	 */
	public static MonAggrCompanySettings loadSettingsForAgreement(
			String companyId,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		final String resourceId = "001";
		
		MonAggrCompanySettings domain = new MonAggrCompanySettings(companyId);

		// 設定読み込み処理　（36協定時間用）
		domain.loadSettingsForAgreementProc(companyId, resourceId, repositories);
		
		return domain;
	}
	
	/**
	 * 設定読み込み処理　（36協定時間用）
	 * @param companyId 会社ID
	 * @param resourceId リソースID
	 * @param repositories 月別集計が必要とするリポジトリ
	 * @return 月別集計で必要な会社別設定
	 */
	private void loadSettingsForAgreementProc(
			String companyId,
			String resourceId,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		// 締め
		val closures = repositories.getClosure().findAllUse(companyId);
		for (val closure : closures){
			val closureId = closure.getClosureId().value;
			this.closureMap.putIfAbsent(closureId, closure);
		}
		
		// 法定内振替順設定
		val legalTransferOrderSetOpt = repositories.getLegalTransferOrderSetOfAggrMonthly().find(companyId);
		if (!legalTransferOrderSetOpt.isPresent()){
			this.errorInfos.put(resourceId, new ErrMessageContent(TextResource.localize("Msg_1232")));
			return;
		}
		this.legalTransferOrderSet = legalTransferOrderSetOpt.get();

		// 残業枠の役割
		this.roleOverTimeFrameList = repositories.getRoleOverTimeFrame().findByCID(companyId);

		// 休出枠の役割
		this.roleHolidayWorkFrameList = repositories.getRoleHolidayWorkFrame().findByCID(companyId);
		
		// 休暇時間加算設定
		this.holidayAdditionMap = repositories.getHolidayAddition().findByCompanyId(companyId);
		
		// 労働時間と日数の設定の利用単位の設定
		this.usageUnitSet = new UsageUnitSetting(new CompanyId(companyId), false, false, false);
		val usagaUnitSetOpt = repositories.getUsageUnitSetRepo().findByCompany(companyId);
		if (usagaUnitSetOpt.isPresent()) this.usageUnitSet = usagaUnitSetOpt.get();
		
		// 通常勤務会社別月別実績集計設定
		this.comRegSetOpt = repositories.getComRegSetRepo().find(companyId);
		
		// 変形労働会社別月別実績集計設定
		this.comIrgSetOpt = repositories.getComIrgSetRepo().find(companyId);
		
		// フレックス会社別月別実績集計設定
		this.comFlexSetOpt = repositories.getComFlexSetRepo().find(companyId);

		// フレックス勤務の月別集計設定
		val aggrSetOfFlexOpt = repositories.getMonthlyAggrSetOfFlex().find(companyId);
		if (!aggrSetOfFlexOpt.isPresent()){
			this.errorInfos.put(resourceId, new ErrMessageContent(TextResource.localize("Msg_1238")));
			return;
		}
		this.aggrSetOfFlex = aggrSetOfFlexOpt.get();

		// フレックス勤務所定労働時間
		val flexPredWorkTimeOpt = repositories.getFlexPredWorktime().find(companyId);
		if (!flexPredWorkTimeOpt.isPresent()){
			this.errorInfos.put(resourceId, new ErrMessageContent(TextResource.localize("Msg_1243")));
			return;
		}
		this.flexPredWorkTime = flexPredWorkTimeOpt.get();
		
		// 休暇加算設定
		this.vacationAddSet = repositories.getVacationAddSet().get(companyId);
		
		// 時間外超過設定
		val outsideOTSetOpt = repositories.getOutsideOTSet().findById(companyId);
		if (!outsideOTSetOpt.isPresent()){
			this.errorInfos.put(resourceId, new ErrMessageContent(TextResource.localize("Msg_1236")));
			return;
		}
		this.outsideOverTimeSet = outsideOTSetOpt.get();
		
		// 丸め設定
		this.roundingSet = new RoundingSetOfMonthly(companyId);
		val roundingSetOpt = repositories.getRoundingSetOfMonthly().find(companyId);
		if (roundingSetOpt.isPresent()) this.roundingSet = roundingSetOpt.get();
		
		// 36協定運用設定を取得
		this.agreementOperationSet = repositories.getAgreementOperationSet().find(companyId);
	}
	
	/**
	 * 勤務種類の取得
	 * @param workTypeCode 勤務種類コード
	 * @param repositories 月別集計が必要とするリポジトリ
	 * @return 勤務種類　（なければ、null）
	 */
	public WorkType getWorkTypeMap(
			String workTypeCode,
			RepositoriesRequiredByMonthlyAggr repositories){
	
		if (this.workTypeMap.containsKey(workTypeCode)) return this.workTypeMap.get(workTypeCode);
		
		val workTypeOpt = repositories.getWorkType().findByPK(this.companyId, workTypeCode);
		if (workTypeOpt.isPresent()){
			this.workTypeMap.put(workTypeCode, workTypeOpt.get());
		}
		else {
			this.workTypeMap.put(workTypeCode, null);
		}
		return this.workTypeMap.get(workTypeCode);
	}

	/**
	 * 現在の全ての勤務種類の取得
	 * @return 勤務種類マップ
	 */
	public Map<String, WorkType> getAllWorkTypeMap(){
		return this.workTypeMap;
	}
	
	/**
	 * 就業時間帯：共通設定の取得
	 * @param workTimeCode 就業時間帯コード
	 * @param repositories 月別集計が必要とするリポジトリ
	 * @return 就業時間帯：共通設定　（なければ、null）
	 */
	public WorkTimezoneCommonSet getWorkTimeCommonSetMap(
			String workTimeCode,
			RepositoriesRequiredByMonthlyAggr repositories){
	
		if (this.workTimeCommonSetMap.containsKey(workTimeCode)) return this.workTimeCommonSetMap.get(workTimeCode);
		
		val workTimeCommonSetOpt = repositories.getCommonSet().get(this.companyId, workTimeCode);
		if (workTimeCommonSetOpt.isPresent()){
			this.workTimeCommonSetMap.put(workTimeCode, workTimeCommonSetOpt.get());
		}
		else {
			this.workTimeCommonSetMap.put(workTimeCode, null);
		}
		return this.workTimeCommonSetMap.get(workTimeCode);
	}

	/**
	 * 所定時間設定の取得
	 * @param workTimeCode 就業時間帯コード
	 * @param repositories 月別集計が必要とするリポジトリ
	 * @return 所定時間設定　（なければ、null）
	 */
	public PredetemineTimeSetting getPredetemineTimeSetMap(
			String workTimeCode,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		if (this.predetermineTimeSetMap.containsKey(workTimeCode)) return this.predetermineTimeSetMap.get(workTimeCode);
		
		val predetermineTimeSetOpt = repositories.getPredetermineTimeSet().findByWorkTimeCode(this.companyId, workTimeCode);
		if (predetermineTimeSetOpt.isPresent()){
			this.predetermineTimeSetMap.put(workTimeCode, predetermineTimeSetOpt.get());
		}
		else {
			this.predetermineTimeSetMap.put(workTimeCode, null);
		}
		return this.predetermineTimeSetMap.get(workTimeCode);
	}
}
