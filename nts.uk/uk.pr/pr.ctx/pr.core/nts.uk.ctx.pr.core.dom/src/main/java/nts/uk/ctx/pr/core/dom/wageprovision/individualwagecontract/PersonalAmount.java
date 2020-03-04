package nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 個人別金額
 */
@Data
@AllArgsConstructor
public class PersonalAmount {

    private String empId;

    //履歴ID
    private String historyId;

    //社員コード
    private String employeeCode;

    //ビジネスネーム
    private String businessName;

    //期間
    private Integer startYearMonth;

    //期間
    private Integer endYearMonth;

    //金額
    private Long amount;
}
