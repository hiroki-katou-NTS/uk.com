package nts.uk.screen.at.app.mobi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.screen.at.app.ktgwidget.find.dto.OvertimeHoursDto;
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ToppageOvertimeHoursDto {
	private OvertimeHoursDto data;
    private boolean visible;
}
