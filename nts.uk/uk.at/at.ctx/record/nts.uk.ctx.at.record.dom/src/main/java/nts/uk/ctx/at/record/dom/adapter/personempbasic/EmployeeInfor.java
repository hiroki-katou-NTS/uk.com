package nts.uk.ctx.at.record.dom.adapter.personempbasic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeInfor {
    //個人ID
    private String personId;

    //社員ID
    private String employeeId;

    //ビジネスネーム
    private String businessName;


    //社員コード
    private String employeeCode;

}
