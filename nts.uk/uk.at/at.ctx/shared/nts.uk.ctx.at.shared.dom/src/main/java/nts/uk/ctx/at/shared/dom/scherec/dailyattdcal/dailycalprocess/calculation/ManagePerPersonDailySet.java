package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation;

import java.util.Optional;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.BonusPaySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.employeeunitpricehistory.EmployeeUnitPriceHistoryItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyprocess.calc.FactoryManagePerPersonDailySet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.DailyUnit;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;

/**
 * 毎日変更の可能性のあるマスタ管理クラス
 * （社員設定管理）
 * 
 * @author keisuke_hoshina
 *
 */
@Getter
public class ManagePerPersonDailySet {
	
	/** 処理日 */
	private GeneralDate ymd;
	
	/** 労働条件 */
	private WorkingConditionItem personInfo;
	
	/** 法定労働 */
	private DailyUnit dailyUnit;
	
	/** 加算設定 */
	private AddSetting addSetting;
	
	/** 加給設定 */
	private Optional<BonusPaySetting> bonusPaySetting;

	/** 社員単価履歴 */
	private Optional<EmployeeUnitPriceHistoryItem> unitPrice;
	
	/** 平日時の所定時間設定
	 *年休、欠勤の場合に実績に就業時間帯が埋まっていない時に使用する。
	 * 例1： 欠勤の場合は、フレックスを-8：00のような計算をする。　平日時の所定時間を使って計算する。
	 * 例2： 年休の場合は、就業時間に加算する場合は平日時の所定時間分を就業時間に加算する（時給者の場合は就業時間に加算するケースが多い）
	 */
	private PredetermineTimeSetForCalc predetermineTimeSetByPersonWeekDay;

	/** フレックス勤務基本設定 */
	private Optional<FlexMonthWorkTimeAggrSet> flexBasicSet;
	
	/** 日別計算用require */
	private FactoryManagePerPersonDailySet.Require require;
	
	/**
	 * Constructor
	 * @param ymd 処理日
	 * @param personInfo 労働条件項目
	 * @param dailyUnit 法定労働時間
	 * @param addSetting 加算設定
	 * @param bonusPaySetting 加給設定
	 * @param predetermineTimeSetByPersonWeekDay 平日時の計算用就業時間帯
	 * @param flexBasicSet フレックス勤務基本設定
	 * @param require 日別計算用Require
	 */
	public ManagePerPersonDailySet(
			GeneralDate ymd,
			WorkingConditionItem personInfo,
			DailyUnit dailyUnit,
			AddSetting addSetting,
			Optional<BonusPaySetting> bonusPaySetting,
			PredetermineTimeSetForCalc predetermineTimeSetByPersonWeekDay,
			Optional<FlexMonthWorkTimeAggrSet> flexBasicSet,
			FactoryManagePerPersonDailySet.Require require,
			Optional<EmployeeUnitPriceHistoryItem> unitPrice) {
		super();
		this.ymd = ymd;
		this.personInfo = personInfo;
		this.dailyUnit = dailyUnit;
		this.addSetting = addSetting;
		this.bonusPaySetting = bonusPaySetting;
		this.predetermineTimeSetByPersonWeekDay = predetermineTimeSetByPersonWeekDay;
		this.flexBasicSet = flexBasicSet;
		this.require = require;
		this.unitPrice = unitPrice;
	}
}
