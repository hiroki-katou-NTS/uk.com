package nts.uk.ctx.pr.screen.app.query.paymentdata.result;

import lombok.Getter;

public class ItemPositionDto {
	
	/**
	 * 行
	 */
	private int linePosition;

	/**
	 * 列
	 */
	@Getter
	private int columnPosition;
}
