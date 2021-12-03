package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param;

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
public class ReserveLeaveLapsedWork {

	/** 期間の開始日に消滅するかどうか */
	private boolean lapsedAtr;
}

