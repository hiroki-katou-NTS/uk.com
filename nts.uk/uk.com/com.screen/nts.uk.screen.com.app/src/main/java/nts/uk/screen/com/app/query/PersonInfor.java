package nts.uk.screen.com.app.query;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonInfor {
    //個人ID
    private String personId;

    //社員ID
    private String employeeCode;
    //社員ID
    private String employeeId;

    //ビジネスネーム
    private String employeeName;

}
