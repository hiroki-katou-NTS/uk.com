package nts.uk.screen.at.app.command.kmk.kmk004.k;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.FlexAggregateMethod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.wkp.WkpFlexMonthActCalSet;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb
 *
 */
@Data
@NoArgsConstructor
public class WkpFlexMonthActCalSetCommand extends FlexMonthWorkTimeAggrSetCommand {

	/** 職場ID */
	private String workplaceId;

	public WkpFlexMonthActCalSet toDomain() {
		return WkpFlexMonthActCalSet.of(AppContexts.user().companyId(),
				EnumAdaptor.valueOf(this.getAggrMethod(), FlexAggregateMethod.class), this.getInsufficSet().toDomain(),
				this.getLegalAggrSet().toDomain(), this.getFlexTimeHandle().toDomain(), this.workplaceId);
	}

	public WkpFlexMonthActCalSetCommand(String workplaceId, int aggrMethod, ShortageFlexSettingCommand insufficSet,
			AggregateTimeSettingCommand legalAggrSet, FlexTimeHandleCommand flexTimeHandle) {
		super(aggrMethod, insufficSet, legalAggrSet, flexTimeHandle);
		this.workplaceId = workplaceId;
	}

}
