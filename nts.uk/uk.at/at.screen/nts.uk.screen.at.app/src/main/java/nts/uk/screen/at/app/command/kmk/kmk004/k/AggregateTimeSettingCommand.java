package nts.uk.screen.at.app.command.kmk.kmk004.k;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.AggregateSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.AggregateTimeSetting;

/**
 * 
 * @author sonnlb
 *
 */
public class AggregateTimeSettingCommand {
	/** 集計設定 */
	private int aggregateSet;

	public AggregateTimeSetting toDomain() {
		return AggregateTimeSetting.of(EnumAdaptor.valueOf(aggregateSet, AggregateSetting.class));
	}

}
