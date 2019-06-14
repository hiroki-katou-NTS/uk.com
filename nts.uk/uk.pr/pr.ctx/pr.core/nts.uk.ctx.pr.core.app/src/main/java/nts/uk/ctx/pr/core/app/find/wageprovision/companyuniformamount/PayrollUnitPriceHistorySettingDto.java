package nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;

@AllArgsConstructor
@Value
public class PayrollUnitPriceHistorySettingDto {

    /**
     * コード
     */
    private String code;
    /**
     * 履歴ID
     */
    private String historyId;

    /**
     * 開始年月
     */
    private int startYearMonth;

    /**
     * 終了年月
     */
    private int endYearMonth;



    /**
     * 金額
     */
    private BigDecimal amountOfMoney;

    /**
     * 対象区分
     */
    private Integer targetClass;

    /**
     * 日給月給者
     */
    private Integer monthSalaryPerDay;

    /**
     * 日給者
     */
    private Integer monthlySalary;

    /**
     * 時給者
     */
    private Integer hourlyPay;

    /**
     * 月給者
     */
    private Integer aDayPayee;

    /**
     * 設定区分
     */
    private int setClassification;

    /**
     * メモ
     */
    private String notes;
}
