package nts.uk.ctx.at.shared.app.find.workrule.shiftmaster;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class TimeZonesDto {
	private int start;

	/** The end. */
	// 終了
	private int end;
}
