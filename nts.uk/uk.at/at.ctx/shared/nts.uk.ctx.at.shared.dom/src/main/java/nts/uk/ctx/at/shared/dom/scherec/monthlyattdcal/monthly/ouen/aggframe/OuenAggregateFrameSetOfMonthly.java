package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.ouen.aggframe;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

@Getter
/** 月別実績の応援集計枠設定 */
public class OuenAggregateFrameSetOfMonthly extends AggregateRoot {

	/** 会社ID: 会社ID */
	private String companyId;
	
	/** 集計枠単位: 集計枠単位 */
	private AggregateFrameUnit unit;
	
	/** 集計枠: 月別実績の応援集計枠 */
	private List<OuenAggregateFrameOfMonthly> frames; 

	private OuenAggregateFrameSetOfMonthly(String companyId, AggregateFrameUnit unit) {
		this.companyId = companyId;
		this.unit = unit;
		this.frames = new ArrayList<>();
	}
	
	private OuenAggregateFrameSetOfMonthly(String companyId, AggregateFrameUnit unit, List<OuenAggregateFrameOfMonthly> frames) {
		this.companyId = companyId;
		this.unit = unit;
		this.frames = frames;
	}
	
	public static OuenAggregateFrameSetOfMonthly create(String companyId, AggregateFrameUnit unit) {
		
		return new OuenAggregateFrameSetOfMonthly(companyId, unit);
	}
	
	public static OuenAggregateFrameSetOfMonthly create(String companyId, int unit,
			List<OuenAggregateFrameOfMonthly> frames) {
		
		return new OuenAggregateFrameSetOfMonthly(companyId, 
				EnumAdaptor.valueOf(unit, AggregateFrameUnit.class), frames);
	}
	
	public void add(OuenAggregateFrameOfMonthly frame) {
		this.frames.add(frame);
	}
	
	@AllArgsConstructor
	public static enum AggregateFrameUnit {

		/** 職場 */
		WORKPLACE(1),
		/** 場所 */
		WORKLOCATION(2),
		/** 作業 */
		WORK(3);
		
		public final int value;
	}
}
