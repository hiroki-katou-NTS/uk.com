package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.BasicSetting;

import java.math.BigDecimal;
/**
 * 基本的な設定
 */
@AllArgsConstructor
@Value
public class BasicSettingDto {
    /**
     * 毎月の支払日
     */
    private AccountingClosureDateDto accountingClosureDate;
    /**
     * 社員抽出基準日
     */
    private EmployeeExtractionReferenceDateDto employeeExtractionReferenceDate;

    /**
     * 経理締め日
     */
    private MonthlyPaymentDateDto monthlyPaymentDate;
    /**
     * 要勤務日数
     */
    private BigDecimal workDay;

    public static BasicSettingDto fromDomain(BasicSetting domain){
        return new BasicSettingDto(
                AccountingClosureDateDto.fromDomain(domain.getAccountingClosureDate()),
                EmployeeExtractionReferenceDateDto.fromDomain(domain.getEmployeeExtractionReferenceDate()),
                MonthlyPaymentDateDto.fromDomain(domain.getMonthlyPaymentDate()),
                domain.getWorkDay().v()
        );
    }

}
