package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 消滅情報WORK
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnnualLeaveLapsedWork {

	/** 期間の開始日に消滅するかどうか */
	private boolean lapsedAtr;
	
}
