package nts.uk.screen.at.app.query.kmk004.b;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author chungnt
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WorkTimeComDto {
	// 年月
	private int yearMonth;

	// 労働時間
	private int laborTime;
}
