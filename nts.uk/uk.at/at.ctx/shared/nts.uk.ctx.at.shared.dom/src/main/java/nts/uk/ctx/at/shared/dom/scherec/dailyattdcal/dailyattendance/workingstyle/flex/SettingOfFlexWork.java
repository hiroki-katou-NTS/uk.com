package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workingstyle.flex;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.flex.FlexSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.AggregateTimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.FlexAggregateMethod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.FlexTimeHandle;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.ShortageFlexSetting;
import nts.uk.shr.com.context.AppContexts;

/**
 * フレックス勤務の設定
 * @author keisuke_hoshina
 */
@Getter
public class SettingOfFlexWork {
	
	/** フレックス勤務の日別計算設定 */
	private FlexSet flexSet;
	/** フレックス勤務基本設定 */
	private FlexMonthWorkTimeAggrSet basicSet;

	private SettingOfFlexWork(){}
	
	/**
	 * コンストラクタ
	 * @param flexSet フレックス勤務の設定
	 * @param basicSet フレックス勤務基本設定
	 */
	public SettingOfFlexWork(FlexSet flexSet, FlexMonthWorkTimeAggrSet basicSet) {
		this.flexSet = flexSet;
		this.basicSet = basicSet;
	}

	public static SettingOfFlexWork defaultValue(){
		SettingOfFlexWork myclass = new SettingOfFlexWork();
		myclass.flexSet = FlexSet.defaultValue();
		myclass.basicSet = myclass.new DefaultFlexBasicSet();
		return myclass;
	}
	
	private class DefaultFlexBasicSet extends FlexMonthWorkTimeAggrSet {
		protected static final long serialVersionUID = 1L;
		
		public DefaultFlexBasicSet() {
			this.comId = AppContexts.user().companyId();
			this.aggrMethod = FlexAggregateMethod.PRINCIPLE;
			this.insufficSet = new ShortageFlexSetting();
			this.legalAggrSet = new AggregateTimeSetting();
			this.flexTimeHandle = FlexTimeHandle.of(false, false);
		}
	}
}
