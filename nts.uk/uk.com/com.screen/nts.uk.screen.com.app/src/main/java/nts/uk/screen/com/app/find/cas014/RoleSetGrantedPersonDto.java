package nts.uk.screen.com.app.find.cas014;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.calendar.period.DatePeriod;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleSetGrantedPersonDto {
    private String roleSetCd;

    private String companyId;

    private DatePeriod validPeriod;

    private String employeeID;
}
