package nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other;

//import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

//import org.omg.CORBA.TCKind;

import lombok.Getter;
//import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.temporarytime.TemporaryFrameTimeOfDaily;

/** 日別実績の臨時時間 */
@Getter
public class TemporaryTimeOfDaily {

	/** 臨時時間: 日別実績の臨時枠時間*/
	private List<TemporaryFrameTimeOfDaily> temporaryTime;

	public TemporaryTimeOfDaily(List<TemporaryFrameTimeOfDaily> temporaryTime) {
		super();
		this.temporaryTime = temporaryTime;
	}

	public TemporaryTimeOfDaily() {
		super();
	}
	
	/**
	 * 臨時時間枠の枠時間の合計を算出する
	 * @return　total frame time
	 */
	public int totalTemporaryFrameTime() {
		//return new AttendanceTime(this.temporaryTime.stream().map(tc -> tc.getTemporaryTime().v()).collect(Collectors.summingInt(tc -> tc)));
		return this.temporaryTime.stream().map(tc -> tc.getTemporaryTime().v()).collect(Collectors.summingInt(tc -> tc));
	}
	
}
