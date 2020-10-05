package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other;

import java.io.Serializable;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
/** 通常勤務の法定内集計設定 */ 
public abstract class RegularWorkTimeAggrSet extends AggregateRoot implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	protected String comId;
	
	/** 集計時間設定 */
	protected ExcessOutsideTimeSetReg aggregateTimeSet;

	/** 時間外超過設定 */
	protected ExcessOutsideTimeSetReg excessOutsideTimeSet;
	
	protected RegularWorkTimeAggrSet () {}
	
	protected void set(String comId, 
			ExcessOutsideTimeSetReg aggregateTimeSet,
			ExcessOutsideTimeSetReg excessOutsideTimeSet) {

		this.comId = comId;
		this.aggregateTimeSet = aggregateTimeSet;
		this.excessOutsideTimeSet = excessOutsideTimeSet;
	}
}
