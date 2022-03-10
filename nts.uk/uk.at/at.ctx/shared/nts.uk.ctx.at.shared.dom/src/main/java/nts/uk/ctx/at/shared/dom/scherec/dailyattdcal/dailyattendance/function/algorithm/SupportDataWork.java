package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

@AllArgsConstructor
@Data
public class SupportDataWork {
	private Optional<IntegrationOfDaily> integrationOfDaily;
	private Optional<String> errorMessageId;
}
