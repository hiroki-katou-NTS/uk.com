package nts.uk.screen.at.app.command.kmk.kmk004.k;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.FlexAggregateMethod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.sha.ShaFlexMonthActCalSet;
import nts.uk.shr.com.context.AppContexts;

@Data
@NoArgsConstructor
public class ShaFlexMonthActCalSetCommand extends FlexMonthWorkTimeAggrSetCommand {
	/** 社員ID */
	private String empId;
	
	public ShaFlexMonthActCalSet toDomain() {
		 return ShaFlexMonthActCalSet.of(
				AppContexts.user().companyId(),
				EnumAdaptor.valueOf(this.getAggrMethod(), FlexAggregateMethod.class), 
				this.getInsufficSet().toDomain(),
				this.getLegalAggrSet().toDomain(), 
				this.getFlexTimeHandle().toDomain(), 
				this.empId);
	}

	public ShaFlexMonthActCalSetCommand(String empId,int aggrMethod, ShortageFlexSettingCommand insufficSet,
			AggregateTimeSettingCommand legalAggrSet, FlexTimeHandleCommand flexTimeHandle) {
		super(aggrMethod, insufficSet, legalAggrSet, flexTimeHandle);
		this.empId = empId;
	}
	
}
