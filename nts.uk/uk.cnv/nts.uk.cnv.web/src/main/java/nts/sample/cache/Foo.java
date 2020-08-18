package nts.sample.cache;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class Foo {

	private String employeeId;
	private GeneralDate date;
}
