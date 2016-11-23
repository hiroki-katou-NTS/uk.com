package nts.uk.ctx.pr.screen.app.query.qpp005.result;

import lombok.Value;

@Value
public class DetailItemPositionDto {

	/**
	 * 行
	 */
	int linePosition;

	/**
	 * 列
	 */
	int columnPosition;

	public static DetailItemPositionDto fromDomain(int linePosition, int columnPosition) {
		return new DetailItemPositionDto(linePosition, columnPosition);
	}
}
