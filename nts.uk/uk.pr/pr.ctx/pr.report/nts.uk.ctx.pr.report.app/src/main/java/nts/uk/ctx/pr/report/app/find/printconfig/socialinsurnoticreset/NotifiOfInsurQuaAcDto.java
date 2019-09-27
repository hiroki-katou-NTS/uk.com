package nts.uk.ctx.pr.report.app.find.printconfig.socialinsurnoticreset;

import lombok.*;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.SocialInsurNotiCreateSet;
import nts.uk.ctx.pr.shared.dom.salgenpurposeparam.SalGenParaValue;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Builder
public class NotifiOfInsurQuaAcDto {
    /**
     * 事業所情報
     */
    private int officeInformation;

    /**
     * 事業所整理記号
     */
    private int businessArrSymbol;

    /**
     * 出力順
     */
    private int outputOrder;
    /**
     * 印刷個人番号
     */
    private int printPersonNumber;
    /**
     * 提出氏名区分
     */
    private int submittedName;

    /**
     * 被保険者整理番号
     */
    private int insuredNumber;

    /**
     * FD番号
     */
    private String fdNumber;

    /**
     * テキスト個人番号
     */
    private int textPersonNumber;

    /**
     * 出力形式
     */
    private int outputFormat;

    /**
     * 改行コード
     */
    private int lineFeedCode;

    // param value
    /**
     * 履歴ID
     */
    private String historyId;

    /**
     * 選択肢
     */
    private int selection;

    /**
     * 有効区分
     */
    private int availableAtr;

    /**
     * 値（数値）
     */
    private BigDecimal numValue;

    /**
     * 値（文字）
     */
    private String charValue;

    /**
     * 値（時間）
     */
    private int timeValue;

    /**
     * 対象区分
     */
    private int targetAtr;


    public static NotifiOfInsurQuaAcDto fromDomain(SocialInsurNotiCreateSet domain, SalGenParaValue domainParaValue) {
        NotifiOfInsurQuaAcDto dto = new NotifiOfInsurQuaAcDto();
        if (domain != null) {
            dto.setOfficeInformation(domain.getOfficeInformation().value);
            dto.setBusinessArrSymbol(domain.getBusinessArrSymbol().value);
            dto.setOutputOrder(domain.getOutputOrder().value);
            dto.setPrintPersonNumber(domain.getPrintPersonNumber().value);
            dto.setSubmittedName(domain.getSubmittedName().value);
            dto.setInsuredNumber(domain.getInsuredNumber().value);
            dto.setFdNumber(domain.getFdNumber().isPresent() ? domain.getFdNumber().get().v() : "");
            dto.setTextPersonNumber(domain.getTextPersonNumber().isPresent() ? domain.getTextPersonNumber().get().value : 0);
            dto.setOutputFormat(domain.getOutputFormat().isPresent() ? domain.getOutputFormat().get().value : 0);
            dto.setLineFeedCode(domain.getLineFeedCode().isPresent() ? domain.getLineFeedCode().get().value : 0);

        }
        if (domainParaValue != null) {
            dto.setHistoryId(domainParaValue.getHistoryId());
            dto.setSelection(domainParaValue.getSelection().get());
            dto.setAvailableAtr(domainParaValue.getAvailableAtr().value);
            dto.setNumValue(domainParaValue.getNumValue().isPresent() ? domainParaValue.getNumValue().get().v() : null);
            dto.setCharValue(domainParaValue.getCharValue().isPresent() ? domainParaValue.getCharValue().get().v() : null);
            dto.setTimeValue(domainParaValue.getTimeValue().get().v());
            dto.setTargetAtr(domainParaValue.getTargetAtr().isPresent() ? domainParaValue.getTargetAtr().get().value : null);
        }
        return dto;

    }
}
