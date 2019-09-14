package nts.uk.screen.at.app.dailyperformance.correction.mobile;

import java.util.Collection;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class MonthlyPerParamMob {

	private String employeeId;
	private Collection<String> formatCode;
	private int yearMonth;
	private Integer closureId;
	private GeneralDate closureDate;
}
