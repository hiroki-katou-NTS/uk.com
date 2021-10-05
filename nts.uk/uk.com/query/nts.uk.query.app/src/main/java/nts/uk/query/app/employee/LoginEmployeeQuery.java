package nts.uk.query.app.employee;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Value
public class LoginEmployeeQuery {

    GeneralDateTime baseDate;

    int systemType;
    
    public GeneralDate getDate() {
    	return this.baseDate.toDate();
    }

}
