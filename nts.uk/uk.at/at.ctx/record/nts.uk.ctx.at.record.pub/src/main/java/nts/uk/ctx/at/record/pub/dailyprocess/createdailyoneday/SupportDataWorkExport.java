package nts.uk.ctx.at.record.pub.dailyprocess.createdailyoneday;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupportDataWorkExport {
	private IntegrationOfDaily integrationOfDaily;
	private String errorMessageId;
}
