package nts.uk.ctx.at.record.dom.adapter.workschedule;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

/**
 * 
 * @author nampt
 * 休憩時間帯
 *
 */
@Getter
@Setter
public class BreakTimeSheetImport extends DomainObject {
	
	//休憩枠NO BreakFrameNo 
	private int breakFrameNo;
	
	//開始 - 勤怠打刻(実打刻付き)
	@Setter
	private int startTime;
	
	//終了 - 勤怠打刻(実打刻付き)
	@Setter
	private int endTime;
	
	/** 休憩時間: 勤怠時間 */
	private int breakTime;

	public BreakTimeSheetImport(int breakFrameNo, int startTime, int endTime, int breakTime) {
		super();
		this.breakFrameNo = breakFrameNo;
		this.startTime = startTime;
		this.endTime = endTime;
		this.breakTime = breakTime;
	}


	
}
