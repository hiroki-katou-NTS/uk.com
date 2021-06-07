package nts.uk.file.at.app.export.worktime;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.shared.app.find.worktime.dto.WorkTimeSettingInfoDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkTimeReportDatasource {
	private String programName;
	private String companyName;
	private GeneralDateTime exportTime;
	private List<WorkTimeSettingInfoDto> workTimeNormal;
	private List<WorkTimeSettingInfoDto> workTimeFlow;
	private List<WorkTimeSettingInfoDto> workTimeFlex;
}
