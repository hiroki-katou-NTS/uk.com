package nts.uk.ctx.at.record.app.command.remainingnumber.nursingcareleave.data;

import lombok.Value;

@Value
public class UpdateCareDataCommand {
	
	//社員ID
	private String sId;
	
	//使用日数
	private int userDay;
}
