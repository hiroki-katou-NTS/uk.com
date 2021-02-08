package nts.uk.ctx.at.request.app.command.application.overtime;

import nts.uk.ctx.at.request.dom.application.overtime.service.DetailOutput;

public class DetailOutputCommand {
	// 残業申請の表示情報
	public DisplayInfoOverTimeCommand displayInfoOverTime;
	// 残業申請
	public AppOverTimeCommand appOverTime;
	
	
	public DetailOutput toDomain() {
		return new DetailOutput(
				displayInfoOverTime.toDomain(),
				appOverTime.toDomain());
	}
}
