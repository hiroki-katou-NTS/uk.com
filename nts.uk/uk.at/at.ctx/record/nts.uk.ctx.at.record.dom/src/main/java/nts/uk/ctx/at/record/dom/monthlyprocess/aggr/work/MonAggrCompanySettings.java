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
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComDeforLaborMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComFlexMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComRegulaMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.outsideot.OutsideOTSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.UsageUnitSetting;
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
@Getter
public class MonAggrCompanySettings {

	/** 会社ID */
	private String companyId;
	/** 勤務種類 */
	private Map<String, WorkType> workTypeMap;
	/** 就業時間帯：共通設定 */
	private Map<String, WorkTimezoneCommonSet> workTimeCommonSetMap;
	/** 所定時間設定 */
	private Map<String, PredetemineTimeSetting> predetermineTimeSetMap;
	/** 締め */
	private Map<Integer, Closure> closureMap;
	/** 法定内振替順設定 */
	private LegalTransferOrderSetOfAggrMonthly legalTransferOrderSet;
	/** 残業枠の役割 */
	private List<RoleOvertimeWork> roleOverTimeFrameList;
	/** 休出枠の役割 */
	private List<RoleOfOpenPeriod> roleHolidayWorkFrameList;
	/** 休暇時間加算設定 */
	private Map<String, AggregateRoot> holidayAdditionMap;
	/** 労働時間と日数の設定の利用単位の設定 */
	private UsageUnitSetting usageUnitSet;
	/** 通常勤務会社別月別実績集計設定 */
	private Optional<ComRegulaMonthActCalSet> comRegSetOpt;
	/** 変形労働会社別月別実績集計設定 */
	private Optional<ComDeforLaborMonthActCalSet> comIrgSetOpt;
	/** フレックス会社別月別実績集計設定 */
	private Optional<ComFlexMonthActCalSet> comFlexSetOpt;
	/** フレックス勤務の月別集計設定 */
	private MonthlyAggrSetOfFlex aggrSetOfFlex;
	/** フレックス勤務所定労働時間 */
	private GetFlexPredWorkTime flexPredWorkTime;
	/** 休暇加算設定 */
	private VacationAddSet vacationAddSet;
	/** 時間外超過設定 */
	private OutsideOTSetting outsideOverTimeSet;
	/** 丸め設定 */
	private RoundingSetOfMonthly roundingSet;
	/** 月別実績の給与項目カウント */
	private PayItemCountOfMonthly payItemCount;
	/** 月別実績の縦計方法 */
	private VerticalTotalMethodOfMonthly verticalTotalMethod;
	/** 任意項目 */
	private Map<Integer, OptionalItem> optionalItemMap;
	/** 適用する雇用条件 */
	private Map<Integer, EmpCondition> empConditionMap;
	/** 計算式 */
	private List<Formula> formulaList;
	
	/** エラー情報 */
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

		// 勤務種類
		val workTypes = repositories.getWorkType().findByCompanyId(companyId);
		for (val workType : workTypes){
			val workTypeCode = workType.getWorkTypeCode().v();
			domain.workTypeMap.putIfAbsent(workTypeCode, workType);
		}
		
		// 就業時間帯：共通設定
		domain.workTimeCommonSetMap = repositories.getCommonSet().getAll(companyId);
		
		// 所定時間設定
		val predetermineTimeSets = repositories.getPredetermineTimeSet().findByCompanyID(companyId);
		for (val predetermineTimeSet : predetermineTimeSets){
			val workTimeCode = predetermineTimeSet.getWorkTimeCode().v();
			domain.predetermineTimeSetMap.putIfAbsent(workTimeCode, predetermineTimeSet);
		}
		
		// 「締め」　取得
		val closures = repositories.getClosure().findAllUse(companyId);
		for (val closure : closures){
			val closureId = closure.getClosureId().value;
			domain.closureMap.putIfAbsent(closureId, closure);
		}
		
		// 法定内振替順設定
		val legalTransferOrderSetOpt = repositories.getLegalTransferOrderSetOfAggrMonthly().find(companyId);
		if (!legalTransferOrderSetOpt.isPresent()){
			domain.errorInfos.put(resourceId, new ErrMessageContent(TextResource.localize("Msg_1232")));
			return domain;
		}
		domain.legalTransferOrderSet = legalTransferOrderSetOpt.get();

		// 残業枠の役割
		domain.roleOverTimeFrameList = repositories.getRoleOverTimeFrame().findByCID(companyId);

		// 休出枠の役割
		domain.roleHolidayWorkFrameList = repositories.getRoleHolidayWorkFrame().findByCID(companyId);
		
		// 休暇加算時間設定
		domain.holidayAdditionMap = repositories.getHolidayAddition().findByCompanyId(companyId);
		
		// 労働時間と日数の設定の利用単位の設定
		domain.usageUnitSet = new UsageUnitSetting(new CompanyId(companyId), false, false, false);
		val usagaUnitSetOpt = repositories.getUsageUnitSetRepo().findByCompany(companyId);
		if (usagaUnitSetOpt.isPresent()) domain.usageUnitSet = usagaUnitSetOpt.get();
		
		// 通常勤務会社別月別実績集計設定
		domain.comRegSetOpt = repositories.getComRegSetRepo().find(companyId);
		
		// 変形労働会社別月別実績集計設定
		domain.comIrgSetOpt = repositories.getComIrgSetRepo().find(companyId);
		
		// フレックス会社別月別実績集計設定
		domain.comFlexSetOpt = repositories.getComFlexSetRepo().find(companyId);

		// フレックス勤務の月別集計設定
		val aggrSetOfFlexOpt = repositories.getMonthlyAggrSetOfFlex().find(companyId);
		if (!aggrSetOfFlexOpt.isPresent()){
			domain.errorInfos.put(resourceId, new ErrMessageContent(TextResource.localize("Msg_1238")));
			return domain;
		}
		domain.aggrSetOfFlex = aggrSetOfFlexOpt.get();

		// フレックス勤務所定労働時間
		val flexPredWorkTimeOpt = repositories.getFlexPredWorktime().find(companyId);
		if (!flexPredWorkTimeOpt.isPresent()){
			domain.errorInfos.put(resourceId, new ErrMessageContent(TextResource.localize("Msg_1243")));
			return domain;
		}
		domain.flexPredWorkTime = flexPredWorkTimeOpt.get();
		
		// 休暇加算設定
		domain.vacationAddSet = repositories.getVacationAddSet().get(companyId);
		
		// 時間外超過設定
		val outsideOTSetOpt = repositories.getOutsideOTSet().findById(companyId);
		if (!outsideOTSetOpt.isPresent()){
			domain.errorInfos.put(resourceId, new ErrMessageContent(TextResource.localize("Msg_1236")));
			return domain;
		}
		domain.outsideOverTimeSet = outsideOTSetOpt.get();
		
		// 丸め設定
		domain.roundingSet = new RoundingSetOfMonthly(companyId);
		val roundingSetOpt = repositories.getRoundingSetOfMonthly().find(companyId);
		if (roundingSetOpt.isPresent()) domain.roundingSet = roundingSetOpt.get();
		
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
		
		return domain;
	}
}
