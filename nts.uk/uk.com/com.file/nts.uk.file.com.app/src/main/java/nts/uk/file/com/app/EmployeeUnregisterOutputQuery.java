package nts.uk.file.com.app;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class EmployeeUnregisterOutputQuery {
	private GeneralDate date = GeneralDate.today();
}
