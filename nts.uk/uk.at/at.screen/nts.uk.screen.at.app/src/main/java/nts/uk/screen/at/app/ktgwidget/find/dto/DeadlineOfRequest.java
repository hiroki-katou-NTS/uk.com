package nts.uk.screen.at.app.ktgwidget.find.dto;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class DeadlineOfRequest {

	private boolean use;
	
	private GeneralDate deadLine;

	public DeadlineOfRequest(boolean use, GeneralDate deadLine) {
		super();
		this.use = use;
		if(deadLine == null) {
			this.deadLine = GeneralDate.today();
		}else {
			this.deadLine = deadLine;
		}
	}
	
	
}
