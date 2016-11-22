package nts.uk.ctx.pr.screen.app.query.qpp005.result;

import lombok.Getter;
import lombok.Value;

@Value
public class DetailItemPositionDto {

	/**
	 * 行
	 */
	private int linePosition;

	/**
	 * 列
	 */
	@Getter
	private int columnPosition;

	public static DetailItemPositionDto fromDomain(int linePosition, int columnPosition) {
		return new DetailItemPositionDto(linePosition, columnPosition);
	}
}
