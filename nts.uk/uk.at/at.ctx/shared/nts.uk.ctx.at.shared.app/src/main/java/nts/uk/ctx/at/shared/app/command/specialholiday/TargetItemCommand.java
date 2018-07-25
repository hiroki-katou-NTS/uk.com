package nts.uk.ctx.at.shared.app.command.specialholiday;

import java.util.List;

import lombok.Value;

@Value
public class TargetItemCommand {
	/** 対象の欠勤枠 */
	private List<Integer> absenceFrameNo;
	
	/** 対象の特別休暇枠 */
	private List<Integer> frameNo;
}
