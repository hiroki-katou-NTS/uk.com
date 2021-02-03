package nts.uk.screen.at.app.command.kmk.kmk004.k;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.FlexAggregateMethod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.emp.EmpFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.shr.com.context.AppContexts;

@Data
@NoArgsConstructor
public class EmpFlexMonthActCalSetCommand extends FlexMonthWorkTimeAggrSetCommand {
	/** 雇用コード */
	private String employmentCode;

	public EmpFlexMonthActCalSet toDomain() {

		return EmpFlexMonthActCalSet.of(
				AppContexts.user().companyId(),
				EnumAdaptor.valueOf(this.getAggrMethod(), FlexAggregateMethod.class), 
				this.getInsufficSet().toDomain(),
				this.getLegalAggrSet().toDomain(), 
				this.getFlexTimeHandle().toDomain(), 
				new EmploymentCode(this.employmentCode));
	}

	public EmpFlexMonthActCalSetCommand(String employmentCode, int aggrMethod, ShortageFlexSettingCommand insufficSet,
			AggregateTimeSettingCommand legalAggrSet, FlexTimeHandleCommand flexTimeHandle) {
		super(aggrMethod, insufficSet, legalAggrSet, flexTimeHandle);
		this.employmentCode = employmentCode;
	}
	
}
