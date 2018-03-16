package nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.common;

import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.FlexAggregateMethod;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.ShortageFlexSetting;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.AggrSettingMonthlyOfFlxNewGetMemento;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.AggregateTimeSettingNew;
import nts.uk.ctx.at.shared.dom.workingcondition.NotUseAtr;

public class AggrSettingMonthlyOfFlxNewDto implements AggrSettingMonthlyOfFlxNewGetMemento{
	
	private Integer flexAggregateMethod;
	
	private Integer getIncludeOT;
	
	private ShortageFlexSetting shortageFlexSetting;
	
	
	
	@Override
	public FlexAggregateMethod getFlexAggregateMethod() {
		return this.flexAggregateMethod;
	}

	@Override
	public NotUseAtr getIncludeOverTime() {
		return this.getIncludeOT;
	}

	@Override
	public ShortageFlexSetting getShortageFlexSetting() {
		return this.shortageFlexSetting;
	}

	@Override
	public AggregateTimeSettingNew getLegalAggrSetOfFlx() {
		return null;
	}

}
