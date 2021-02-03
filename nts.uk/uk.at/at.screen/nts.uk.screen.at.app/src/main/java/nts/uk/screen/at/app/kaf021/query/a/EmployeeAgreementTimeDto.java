package nts.uk.screen.at.app.kaf021.query.a;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeAgreementTimeDto {
    /**
     * 社員ID
     */
    private String employeeId;
    /**
     * 申請状況
     */
    private Integer status;
    /**
     * 社員コード
     */
    private String employeeCode;
    /**
     * 社員名
     */
    private String employeeName;
    /**
     * 所属CD
     */
    private String affiliationCode;
    /**
     * 所属ID
     */
    private String affiliationId;
    /**
     * 所属名称
     */
    private String affiliationName;
    /**
     * 1月度
     */
    private AgreementTimeMonthDto month1;
    /**
     * 2月度
     */
    private AgreementTimeMonthDto month2;
    /**
     * 3月度
     */
    private AgreementTimeMonthDto month3;
    /**
     * 4月度
     */
    private AgreementTimeMonthDto month4;
    /**
     * 5月度
     */
    private AgreementTimeMonthDto month5;
    /**
     * 6月度
     */
    private AgreementTimeMonthDto month6;
    /**
     * 7月度
     */
    private AgreementTimeMonthDto month7;
    /**
     * 8月度
     */
    private AgreementTimeMonthDto month8;
    /**
     * 9月度
     */
    private AgreementTimeMonthDto month9;
    /**
     * 10月度
     */
    private AgreementTimeMonthDto month10;
    /**
     * 11月度
     */
    private AgreementTimeMonthDto month11;
    /**
     * 12月度
     */
    private AgreementTimeMonthDto month12;
    /**
     * 年間
     */
    private AgreementTimeYearDto year;
    /**
     * 直近2ヵ月平均
     */
    private AgreementMaxAverageTimeDto monthAverage2;
    /**
     * 直近3ヵ月平均
     */
    private AgreementMaxAverageTimeDto monthAverage3;
    /**
     * 直近4ヵ月平均
     */
    private AgreementMaxAverageTimeDto monthAverage4;
    /**
     * 直近5ヵ月平均
     */
    private AgreementMaxAverageTimeDto monthAverage5;
    /**
     * 直近6ヵ月平均
     */
    private AgreementMaxAverageTimeDto monthAverage6;
    /**
     * 36協定超過情報.超過回数
     */
    private Integer exceededNumber;

}