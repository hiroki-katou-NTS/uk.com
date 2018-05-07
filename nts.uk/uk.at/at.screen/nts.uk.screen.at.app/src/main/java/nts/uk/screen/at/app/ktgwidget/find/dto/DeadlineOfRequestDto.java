package nts.uk.screen.at.app.ktgwidget.find.dto;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class DeadlineOfRequestDto {

	private int use;
	
	private GeneralDate deadLine;
}
