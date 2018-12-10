package nts.uk.ctx.at.record.dom.workinformation;

import java.util.Optional;

import lombok.AllArgsConstructor;
//import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.event.DomainEvent;
import nts.arc.time.GeneralDate;

/**
 * 実績の就業時間帯が変更された
 * @author nampt
 *
 */
@Setter
@Getter
@AllArgsConstructor
public class WorkInfoOfDaiPerEvent extends DomainEvent{

	private String employeeID;
	
	private GeneralDate processingDate;
	
	private Optional<String> workTimeCode;
	
}
