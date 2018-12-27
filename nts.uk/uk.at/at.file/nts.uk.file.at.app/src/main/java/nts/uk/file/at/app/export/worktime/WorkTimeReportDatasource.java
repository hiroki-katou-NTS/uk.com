package nts.uk.file.at.app.export.worktime;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkTimeReportDatasource {
	private String companyName;
	private String exportTime;
	private List<Object[]> workTimeNormal;
	private List<Object[]> workTimeFlow;
	private List<Object[]> workTimeFlex;
}
