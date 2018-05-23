package nts.uk.ctx.at.function.app.find.alarm.checkcondition;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MonAlarmCheckConDto {
	/**固定抽出条件*/
	private List<FixedExtraMonFunDto> listFixExtraMon = new ArrayList<>();
	/**任意抽出条件*/
	//List<ExtraResultMonthly> arbExtraCon = new ArrayList<>();

	public MonAlarmCheckConDto(List<FixedExtraMonFunDto> listFixExtraMon) {
		super();
		this.listFixExtraMon = listFixExtraMon;
	}

	
}
