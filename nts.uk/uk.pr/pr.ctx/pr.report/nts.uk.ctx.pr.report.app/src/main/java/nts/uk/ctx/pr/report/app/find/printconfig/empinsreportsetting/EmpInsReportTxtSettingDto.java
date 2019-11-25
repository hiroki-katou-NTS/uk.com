package nts.uk.ctx.pr.report.app.find.printconfig.empinsreportsetting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportTxtSetting;

@Value
@Builder
@AllArgsConstructor
public class EmpInsReportTxtSettingDto {

    /**
     * 会社ID
     */
    private String cid;

    /**
     * ユーザID
     */
    private String userId;

    /**
     * 事業所区分
     */
    private int officeAtr;

    /**
     * FD番号
     */
    private String fdNumber;

    /**
     * 改行コード
     */
    private int lineFeedCode;

    public static EmpInsReportTxtSettingDto fromDomain(EmpInsReportTxtSetting domain) {
        return new EmpInsReportTxtSettingDto(
                domain.getCid(),
                domain.getUserId(),
                domain.getOfficeAtr().value,
                domain.getFdNumber().v(),
                domain.getLineFeedCode().value
        );
    }
}
