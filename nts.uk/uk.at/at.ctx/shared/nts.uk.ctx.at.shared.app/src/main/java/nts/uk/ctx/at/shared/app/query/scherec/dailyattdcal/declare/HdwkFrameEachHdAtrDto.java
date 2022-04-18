package nts.uk.ctx.at.shared.app.query.scherec.dailyattdcal.declare;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HdwkFrameEachHdAtrDto {
	/** 法定内 */
	private Integer statutory;
	/** 法定外 */
	private Integer notStatutory;
	/** 法定外祝日 */
	private Integer notStatHoliday;
}
