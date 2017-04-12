package nts.uk.ctx.basic.app.find.company;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Value
public class TestDto {
	String code;
	String name;
	GeneralDate date;
	GeneralDateTime dateTime;
}
