package nts.uk.ctx.at.function.app.command.alarm.checkcondition;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.FixedExtraMonFunImport;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.monthly.dtoevent.ExtraResultMonthlyDomainEventDto;

@Getter
@Setter
@NoArgsConstructor
public class MonAlarmCheckConCommand {
	/**固定抽出条件*/
	private List<FixedExtraMonFunImport> listFixExtraMon = new ArrayList<>();
	/**任意抽出条件*/
	List<ExtraResultMonthlyDomainEventDto> arbExtraCon = new ArrayList<>();
	public MonAlarmCheckConCommand(List<FixedExtraMonFunImport> listFixExtraMon, List<ExtraResultMonthlyDomainEventDto> arbExtraCon) {
		super();
		this.listFixExtraMon = listFixExtraMon;
		this.arbExtraCon = arbExtraCon;
	}

	
	
}
