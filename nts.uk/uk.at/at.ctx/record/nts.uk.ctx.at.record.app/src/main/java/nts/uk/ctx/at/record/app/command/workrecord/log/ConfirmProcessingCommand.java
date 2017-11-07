package nts.uk.ctx.at.record.app.command.workrecord.log;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLog;
import nts.uk.ctx.at.record.dom.workrecord.log.TargetPerson;

@Data
public class ConfirmProcessingCommand {
	
	private String taskId; 
	
	private List<TargetPerson> listTarget; 
	
	private List<EmpCalAndSumExeLog> listEmpCalAndSumExeLog;

}
