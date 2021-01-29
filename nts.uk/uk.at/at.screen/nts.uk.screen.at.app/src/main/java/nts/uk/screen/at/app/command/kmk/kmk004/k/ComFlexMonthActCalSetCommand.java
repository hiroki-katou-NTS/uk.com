package nts.uk.screen.at.app.command.kmk.kmk004.k;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.FlexAggregateMethod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.com.ComFlexMonthActCalSet;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb
 *
 */
@Data
@NoArgsConstructor
public class ComFlexMonthActCalSetCommand extends FlexMonthWorkTimeAggrSetCommand {

	/** 所定労動時間使用区分 */
	private boolean withinTimeUsageAttr;

	public ComFlexMonthActCalSet toDomain() {
		return ComFlexMonthActCalSet.of(
				AppContexts.user().companyId(),
				EnumAdaptor.valueOf(this.getAggrMethod(), FlexAggregateMethod.class), 
				this.getInsufficSet().toDomain(),
				this.getLegalAggrSet().toDomain(), 
				this.getFlexTimeHandle().toDomain(), 
				this.withinTimeUsageAttr);
	}

	public ComFlexMonthActCalSetCommand(boolean withinTimeUsageAttr, int aggrMethod,
			ShortageFlexSettingCommand insufficSet, AggregateTimeSettingCommand legalAggrSet,
			FlexTimeHandleCommand flexTimeHandle) {
		super(aggrMethod, insufficSet, legalAggrSet, flexTimeHandle);
		this.withinTimeUsageAttr = withinTimeUsageAttr;
	}
	
	

}
