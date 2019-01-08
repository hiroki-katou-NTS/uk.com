package nts.uk.ctx.at.shared.app.find.specialholiday;

import java.util.Collections;
import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.specialholiday.TargetItem;

@Value
public class TargetItemDto {
	/** 対象の欠勤枠 */
	private List<Integer> absenceFrameNo;
	
	/** 対象の特別休暇枠 */
	private List<Integer> frameNo;
	
	public static TargetItemDto fromDomain(TargetItem targetItem) {
		if(targetItem == null) {
			return null;
		}
		
		return new TargetItemDto(
				targetItem.getAbsenceFrameNo(),
				targetItem.getFrameNo()
		);
	}
	
	public List<Integer> getFrameNo() {
		return frameNo != null ? frameNo : Collections.emptyList();
	}
	
	public List<Integer> getAbsenceFrameNo() {
		return absenceFrameNo != null ? absenceFrameNo : Collections.emptyList();
	}
}
