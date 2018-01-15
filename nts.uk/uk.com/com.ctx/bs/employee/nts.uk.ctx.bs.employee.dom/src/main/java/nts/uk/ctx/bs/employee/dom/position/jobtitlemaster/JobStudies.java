package nts.uk.ctx.bs.employee.dom.position.jobtitlemaster;

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

	public static JobStudies creatFromJavaType(String historyId, GeneralDate startDate, GeneralDate endDate) {
		return new JobStudies(
				historyId, 
				startDate,
				endDate);

	}

}
