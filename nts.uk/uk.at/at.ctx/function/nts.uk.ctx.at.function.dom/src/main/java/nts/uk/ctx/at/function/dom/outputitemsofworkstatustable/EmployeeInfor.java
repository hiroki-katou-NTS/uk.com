package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeInfor {
    /** The employee id. */
    String employeeId; // 社員ID

    /** The employee code. */
    String employeeCode; // 社員コード

    /** The business name. */
    String businessName; // ビジネスネーム

    /** The business name Kana. */
    String businessNameKana; // ビジネスネームカナ

    /** The employment cls. */
    Integer employmentCls; // 就業区分

    //個人ID
    String personID;

    //社員名
    String employeeName;

    //誕生日
    GeneralDate birthday;

    //年齢
    int age;

    int gender;
}
