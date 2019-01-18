package nts.uk.file.at.app.export.worktime;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkTimeReportDatasource {
	private String programName;
	private String companyName;
	private GeneralDateTime exportTime;
	private List<Object[]> workTimeNormal;
	private List<Object[]> workTimeFlow;
	private List<Object[]> workTimeFlex;
}
