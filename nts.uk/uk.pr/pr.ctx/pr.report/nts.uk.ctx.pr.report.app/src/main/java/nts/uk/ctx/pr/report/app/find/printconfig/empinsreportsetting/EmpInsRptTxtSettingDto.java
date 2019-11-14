package nts.uk.ctx.pr.report.app.find.printconfig.empinsreportsetting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportTxtSetting;

@Value
@AllArgsConstructor
@Builder
public class EmpInsRptTxtSettingDto {

    /**
     * 会社ID
     */
    public String cid;

    /**
     * ユーザID
     */
    public String userId;

    /**
     * 事業所区分
     */
    private int officeAtr;

    /**
     * FD番号
     */
    private int fdNumber;

    /**
     * 改行コード
     */
    private int lineFeedCode;

    public static EmpInsRptTxtSettingDto fromDomainToDto(EmpInsReportTxtSetting domain) {
        return new EmpInsRptTxtSettingDto(domain.getCid(), domain.getUserId(), domain.getOfficeAtr().value, domain.getFdNumber().v(), domain.getLineFeedCode().value);
    }
}
