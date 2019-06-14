package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.AdvancedSetting;
/**
 * 高度な設定
 */
@AllArgsConstructor
@Value
public class AdvancedSettingDto {

    /*
     *勤怠締め年月日
     */
    private CloseDateDto closeDate;

    /*
     *所得税基準年月日
     */
    private IncomTaxBaseYearDto incomTaxBaseYear;

    /*
     *明細印字月
     */
    private DetailPrintingMonDto detailPrintingMon;

    /*
     *社会保険基準年月日
     */
    private SociInsuStanDateDto sociInsuStanDate;

    /*
     *給与社会保険徴収月
     */
    private SalaryInsuColMonDto salaryInsuColMon;

    /*
     *雇用保険基準日
     */
    private EmpInsurStanDateDto empInsurStanDate;

    public static AdvancedSettingDto fromDomain(AdvancedSetting domain){
        return new AdvancedSettingDto (
                CloseDateDto.fromDomain(domain.getCloseDate()),
                IncomTaxBaseYearDto.fromDomain(domain.getIncomTaxBaseYear()),
                DetailPrintingMonDto.fromDomain(domain.getItemPrintingMonth()),
                SociInsuStanDateDto.fromDomain(domain.getSociInsuStanDate()),
                SalaryInsuColMonDto.fromDomain(domain.getSocialInsuColleMon()),
                EmpInsurStanDateDto.fromDomain(domain.getEmpInsurStanDate())
        );
    }


}
