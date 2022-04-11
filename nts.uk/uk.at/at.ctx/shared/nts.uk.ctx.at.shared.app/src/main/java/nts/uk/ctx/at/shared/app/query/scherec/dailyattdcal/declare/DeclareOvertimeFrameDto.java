package nts.uk.ctx.at.shared.app.query.scherec.dailyattdcal.declare;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeclareOvertimeFrameDto {
	/** 早出残業 */
	private Integer earlyOvertime;
	/** 早出残業深夜 */
	private Integer earlyOvertimeMn;
	/** 普通残業 */
	private Integer overtime;
	/** 普通残業深夜 */
	private Integer overtimeMn;
}
