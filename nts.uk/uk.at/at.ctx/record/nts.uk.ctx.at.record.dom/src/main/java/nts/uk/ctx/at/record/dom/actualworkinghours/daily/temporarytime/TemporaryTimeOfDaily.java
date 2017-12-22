package nts.uk.ctx.at.record.dom.actualworkinghours.daily.temporarytime;

import java.util.List;

import lombok.Getter;

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
}
