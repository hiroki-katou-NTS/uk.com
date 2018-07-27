package nts.uk.ctx.at.function.app.find.alarm.checkcondition;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.monthly.dtoevent.ExtraResultMonthlyDomainEventDto;

@Getter
@Setter
@NoArgsConstructor
public class MonAlarmCheckConDto {
	/**固定抽出条件*/
	private List<FixedExtraMonFunDto> listFixExtraMon = new ArrayList<>();
	/**任意抽出条件*/
	List<ExtraResultMonthlyDomainEventDto> arbExtraCon = new ArrayList<>();
	
	private List<String> listEralCheckIDOld = new ArrayList<>();

	public MonAlarmCheckConDto(List<FixedExtraMonFunDto> listFixExtraMon, List<ExtraResultMonthlyDomainEventDto> arbExtraCon, List<String> listEralCheckIDOld) {
		super();
		this.listFixExtraMon = listFixExtraMon;
		this.arbExtraCon = arbExtraCon;
		this.listEralCheckIDOld = listEralCheckIDOld;
	}
	

	

	
}
