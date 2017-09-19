package nts.uk.ctx.bs.employee.dom.position.jobtitlemaster;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class JobStudies {

	private String historyId;

	GeneralDate startDate;

	GeneralDate endDate;

	public static JobStudies creatFromJavaType(String historyId, String startDate, String endDate) {
		return new JobStudies(
				historyId, 
				GeneralDate.legacyDate(new Date(startDate)),
				GeneralDate.legacyDate(new Date(endDate)));

	}

}
