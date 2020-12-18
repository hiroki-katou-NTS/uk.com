package nts.uk.ctx.at.function.app.export.alarmworkplace.alarmlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.alarmworkplace.export.AlarmListExtractResultWorkplaceData;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlarmWorkPlaceExportData {

	private String processId;

	private String alarmPatternCode;

	private String alarmPatternName;
}
