package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.ouen.aggframe;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainObject;

@Getter
/** 月別実績の応援集計枠 */
public class OuenAggregateFrameOfMonthly implements DomainObject {
	
	/** 集計枠: int */
	private int frameNo;

	/** 集計対象: 集計枠に紐づく対象 */
	private TargetLinkAggregateFrame target;
	
	/** 集計枠名: 集計枠名 */
	private AggregateFrameName name;

	private OuenAggregateFrameOfMonthly(int frameNo, TargetLinkAggregateFrame target, AggregateFrameName name) {
		super();
		this.frameNo = frameNo;
		this.target = target;
		this.name = name;
	}
	
	public static OuenAggregateFrameOfMonthly create(int frameNo,
			TargetLinkAggregateFrame target, AggregateFrameName name) {
	
		return new OuenAggregateFrameOfMonthly(frameNo, target, name);
	}
}
