package nts.uk.ctx.at.request.app.command.application.overtime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.overtime.ExcessState;
import nts.uk.ctx.at.request.dom.application.overtime.OverStateOutput;

@AllArgsConstructor
@NoArgsConstructor
public class OverStateOutputCommand {
	// 事前申請なし
	public Boolean isExistApp;
	// 事前超過
	public OutDateApplicationCommand advanceExcess;
	// 実績状態
	public Integer achivementStatus;
	// 実績超過
	public OutDateApplicationCommand achivementExcess;
	
	public OverStateOutput toDomain() {
		return new OverStateOutput(
				isExistApp,
				advanceExcess.toDomain(),
				EnumAdaptor.valueOf(achivementStatus, ExcessState.class),
				achivementExcess.toDomain());
	}
	
}
