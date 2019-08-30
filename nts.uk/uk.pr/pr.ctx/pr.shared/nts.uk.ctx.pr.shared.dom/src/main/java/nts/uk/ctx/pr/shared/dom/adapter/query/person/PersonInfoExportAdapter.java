package nts.uk.ctx.pr.shared.dom.adapter.query.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonInfoExportAdapter {

    private String pid;

    private String businessName;

    private GeneralDate entryDate;

    private int gender;

    private GeneralDate birthDay;

    /** The employee id. */
    private String employeeId;

    /** The employee code. */
    private String employeeCode;

    private GeneralDate retiredDate;
}
