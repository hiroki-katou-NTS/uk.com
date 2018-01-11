package nts.uk.ctx.at.record.app.command.workrecord.erroralarm.condition;

import lombok.Value;

@Value
public class DeleteWorkRecordExtraConCommand {
	private String errorAlarmCheckID;
	
	private int checkItem;
}
