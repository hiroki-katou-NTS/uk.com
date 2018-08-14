package nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.log;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicScheCorrectCommand {
	
	private List<BasicSchedule> domainOld;
	
	private List<BasicSchedule> domainNew;
	
	private boolean isInsertMode;

}
