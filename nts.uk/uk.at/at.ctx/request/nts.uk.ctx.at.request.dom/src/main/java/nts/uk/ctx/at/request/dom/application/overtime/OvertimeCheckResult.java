package nts.uk.ctx.at.request.dom.application.overtime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OvertimeCheckResult {
	private int ErrorCode;
	private int FrameNo;
	private boolean isConfirm = false;
}
