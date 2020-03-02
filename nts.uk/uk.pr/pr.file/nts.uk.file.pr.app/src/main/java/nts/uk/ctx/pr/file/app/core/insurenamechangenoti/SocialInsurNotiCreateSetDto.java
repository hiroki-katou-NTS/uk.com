package nts.uk.ctx.pr.file.app.core.insurenamechangenoti;

import lombok.*;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.SocialInsurNotiCreateSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Builder
public class SocialInsurNotiCreateSetDto {

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
    private Integer textPersonNumber;

    /**
     * 出力形式
     */
    private Integer outputFormat;

    /**
     * 改行コード
     */
    private Integer lineFeedCode;

    public static SocialInsurNotiCreateSetDto fromDomain(SocialInsurNotiCreateSet domain) {
        return SocialInsurNotiCreateSetDto.builder()
                .officeInformation(domain.getOfficeInformation().value)
                .businessArrSymbol(domain.getBusinessArrSymbol().value)
                .outputOrder(domain.getOutputOrder().value)
                .printPersonNumber(domain.getPrintPersonNumber().value)
                .submittedName(domain.getSubmittedName().value)
                .insuredNumber(domain.getInsuredNumber().value)
                .fdNumber(domain.getFdNumber().isPresent() ? domain.getFdNumber().get().v() : null)
                .textPersonNumber(domain.getTextPersonNumber().isPresent() ? domain.getTextPersonNumber().get().value : null)
                .outputFormat(domain.getOutputFormat().isPresent() ? domain.getOutputFormat().get().value : null)
                .lineFeedCode(domain.getLineFeedCode().isPresent() ? domain.getLineFeedCode().get().value : null)
                .build();
    }


}
