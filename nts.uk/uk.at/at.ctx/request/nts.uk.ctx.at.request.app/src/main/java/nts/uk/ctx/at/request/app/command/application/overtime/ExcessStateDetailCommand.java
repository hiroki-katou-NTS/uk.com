package nts.uk.ctx.at.request.app.command.application.overtime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType_Update;
import nts.uk.ctx.at.request.dom.application.overtime.ExcessState;
import nts.uk.ctx.at.request.dom.application.overtime.ExcessStateDetail;
import nts.uk.ctx.at.request.dom.application.overtime.FrameNo;

@AllArgsConstructor
@NoArgsConstructor
public class ExcessStateDetailCommand {
	// frameNo
	public Integer frame;
	// type
	public Integer type;
	// 超過状態
	public Integer excessState;
	
	public ExcessStateDetail toDomain() {
		return new ExcessStateDetail(
				new FrameNo(frame),
				EnumAdaptor.valueOf(type, AttendanceType_Update.class),
				EnumAdaptor.valueOf(excessState, ExcessState.class));
	}
}
