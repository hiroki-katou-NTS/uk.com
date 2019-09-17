package nts.uk.screen.at.app.dailyperformance.correction.dto.mobile;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ErrorParam {
	
	private GeneralDate startDate;
	
	private GeneralDate endDate;
	
	private List<String> employeeIDLst;
	
	private Integer attendanceItemID;
}
