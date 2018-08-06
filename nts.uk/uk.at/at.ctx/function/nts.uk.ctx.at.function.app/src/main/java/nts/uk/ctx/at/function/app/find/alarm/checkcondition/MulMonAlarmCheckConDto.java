package nts.uk.ctx.at.function.app.find.alarm.checkcondition;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.multimonth.doevent.MulMonCheckCondDomainEventDto;

@Getter
@Setter
@NoArgsConstructor
public class MulMonAlarmCheckConDto {
	
	/**任意抽出条件*/
	List<MulMonCheckCondDomainEventDto> arbExtraCon = new ArrayList<>();
	private List<String> errorAlarmCheckIDOlds = new ArrayList<>();

	public MulMonAlarmCheckConDto(List<MulMonCheckCondDomainEventDto> arbExtraCon, List<String> errorAlarmCheckIDOlds) {
		super();
		this.arbExtraCon = arbExtraCon;
		this.errorAlarmCheckIDOlds = errorAlarmCheckIDOlds;
	}
	

	

	
}
