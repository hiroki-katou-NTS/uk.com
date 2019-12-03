package nts.uk.ctx.at.record.dom.reservation.bentomenu;

import lombok.Getter;

/**
 * 弁当メニュー枠番
 * @author Doan Duy Hung
 *
 */
public class BentoMenuFrameNumber {
	
	@Getter
	private Integer number;
	
	public BentoMenuFrameNumber(Integer number) {
		this.number = number;
	}
}
