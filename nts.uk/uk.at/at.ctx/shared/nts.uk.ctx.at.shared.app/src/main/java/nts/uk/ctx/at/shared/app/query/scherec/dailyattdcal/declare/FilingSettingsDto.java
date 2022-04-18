package nts.uk.ctx.at.shared.app.query.scherec.dailyattdcal.declare;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author NWS
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilingSettingsDto {
	// <optional>残業枠
	private List<OvertimeWorkFrameDto> optionalOvertimeWorkFrameDto;

	// <optional>休出枠
	private List<WorkdayoffFrameDto> optionalWorkdayoffFrameDto;

	// <optional>申告設定
	private DeclareSetDto optionalDeclareSetDto;
}
