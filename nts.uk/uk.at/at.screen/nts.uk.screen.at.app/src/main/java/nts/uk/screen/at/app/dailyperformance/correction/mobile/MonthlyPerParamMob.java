package nts.uk.screen.at.app.dailyperformance.correction.mobile;

import java.util.Collection;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class MonthlyPerParamMob {
	/**社員ID*/
	private String employeeId;
	/**フォーマットコード*/
	private Collection<String> formatCode;
	/**年月*/
	private int yearMonth;
	/**締めID*/
	private Integer closureId;
	/**締め日*/
	private GeneralDate closureDate;
}
