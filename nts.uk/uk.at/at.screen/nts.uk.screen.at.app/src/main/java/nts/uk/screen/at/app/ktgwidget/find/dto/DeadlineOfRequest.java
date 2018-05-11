package nts.uk.screen.at.app.ktgwidget.find.dto;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class DeadlineOfRequest {

	private boolean use;
	
	private GeneralDate deadLine;
}
