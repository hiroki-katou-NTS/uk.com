package nts.uk.ctx.at.record.dom.divergence.time.setting;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DivergenceInputRequired {
	
	Require(0),
	
	Not_Require(1);
	
	public final int value;
}
