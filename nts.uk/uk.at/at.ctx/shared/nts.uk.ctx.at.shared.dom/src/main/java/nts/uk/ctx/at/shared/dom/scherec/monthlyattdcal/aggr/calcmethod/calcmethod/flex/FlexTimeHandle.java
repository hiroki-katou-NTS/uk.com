package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex;

import java.io.Serializable;

import lombok.Getter;
import lombok.val;

@Getter
/** フレックス時間の扱い */
public class FlexTimeHandle implements Serializable {

	/** Serializable */
	private static final long serialVersionUID = 1L;
	
	/** 残業時間をフレックス時間に含める */
	private boolean includeOverTime;
	
	/** 法定外休出時間をフレックス時間に含める */
	private boolean includeIllegalHdwk;
	
	private FlexTimeHandle () {}
	
	/**
	 * @param includeOverTime 残業時間をフレックス時間に含める
	 * @param includeIllegalHdwk 法定外休出時間をフレックス時間に含める
	 * @return フレックス時間の扱い
	 */
	public static FlexTimeHandle of(boolean includeOverTime, boolean includeIllegalHdwk){
		
		val domain = new FlexTimeHandle();
		domain.includeOverTime = includeOverTime;
		domain.includeIllegalHdwk = includeIllegalHdwk;
		return domain;
	}
}
