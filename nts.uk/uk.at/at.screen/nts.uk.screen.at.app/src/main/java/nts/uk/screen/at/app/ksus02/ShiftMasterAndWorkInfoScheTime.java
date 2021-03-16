package nts.uk.screen.at.app.ksus02;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.dto.ShiftMasterDto;
import nts.uk.screen.at.app.kdl045.query.WorkAvailabilityOfOneDayDto;
/**
 * //List<シフトマスタ, 勤務情報と補正済み所定時間帯>
 * @author tutk
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class ShiftMasterAndWorkInfoScheTime {

	private ShiftMasterDto shiftMaster;
	
	private WorkInfoAndScheTime workInfoAndScheTime;

	public ShiftMasterAndWorkInfoScheTime(ShiftMasterDto shiftMaster, WorkInfoAndScheTime workInfoAndScheTime) {
		super();
		this.shiftMaster = shiftMaster;
		this.workInfoAndScheTime = workInfoAndScheTime;
	}
	
}
