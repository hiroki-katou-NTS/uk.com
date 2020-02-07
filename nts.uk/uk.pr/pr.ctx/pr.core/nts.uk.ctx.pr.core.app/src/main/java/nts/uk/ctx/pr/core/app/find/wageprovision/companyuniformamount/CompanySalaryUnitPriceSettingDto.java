package nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount.PayrollUnitPriceSetting;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CompanySalaryUnitPriceSettingDto {
    /**
     * 履歴ID
     */
    public String historyId;

    /**
     * 金額
     */
    public BigDecimal amountOfMoney;

    /**
     * 対象区分
     */
    public Integer targetClass;

    /**
     * 日給月給者
     */
    public Integer monthSalaryPerDay;

    /**
     * 日給者
     */
    public Integer monthlySalary;

    /**
     * 時給者
     */
    public Integer hourlyPay;

    /**
     * 月給者
     */
    public Integer aDayPayee;

    /**
     * 設定区分
     */
    public int setClassification;

    /**
     * メモ
     */
    public String notes;

    public static CompanySalaryUnitPriceSettingDto fromDomainToDto(PayrollUnitPriceSetting domain) {
        CompanySalaryUnitPriceSettingDto dto = new CompanySalaryUnitPriceSettingDto();
        dto.historyId = domain.getHistoryId();
        dto.notes = domain.getNotes().map(PrimitiveValueBase::v).orElse(null);
        dto.amountOfMoney = domain.getAmountOfMoney().v();
        dto.setClassification = domain.getFixedWage().getSetClassification().value;
        domain.getFixedWage().getFlatAllEmployees().ifPresent(item-> {
            dto.targetClass = item.getTargetClass().value;
        });
        domain.getFixedWage().getPerSalaryConType().ifPresent(item-> {
            dto.monthlySalary = item.getMonthlySalary().value;
            dto.monthSalaryPerDay = item.getMonthSalaryPerDay().value;
            dto.aDayPayee = item.getMonthlySalary().value;
            dto.hourlyPay = item.getHourlyPay().value;
        });
        return dto;
    }
}
