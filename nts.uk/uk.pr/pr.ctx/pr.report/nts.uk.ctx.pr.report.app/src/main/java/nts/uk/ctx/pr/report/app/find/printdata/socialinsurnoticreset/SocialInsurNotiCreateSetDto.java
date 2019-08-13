package nts.uk.ctx.pr.report.app.find.printdata.socialinsurnoticreset;

import lombok.*;
import nts.uk.ctx.pr.report.dom.printdata.socinsurnoticreset.*;

import java.util.Optional;

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
    private BusinessDivision officeInformation;

    /**
     * 事業所整理記号
     */
    private BussEsimateClass businessArrSymbol;

    /**
     * 出力順
     */
    private SocialInsurOutOrder outputOrder;
    /**
     * 印刷個人番号
     */
    private PersonalNumClass printPersonNumber;
    /**
     * 提出氏名区分
     */
    private SubNameClass submittedName;

    /**
     * 被保険者整理番号
     */
    private InsurPersonNumDivision insuredNumber;

    /**
     * FD番号
     */
    private Optional<FdNumber> fdNumber;

    /**
     * テキスト個人番号
     */
    private Optional<TextPerNumberClass> textPersonNumber;

    /**
     * 出力形式
     */
    private Optional<OutputFormatClass> outputFormat;

    /**
     * 改行コード
     */
    private Optional<LineFeedCode> lineFeedCode;

    public static SocialInsurNotiCreateSetDto fromDomain(SocialInsurNotiCreateSet domain){
        return SocialInsurNotiCreateSetDto.builder()
                        .officeInformation(domain.getOfficeInformation())
                        .businessArrSymbol(domain.getBusinessArrSymbol())
                        .outputOrder(domain.getOutputOrder())
                        .printPersonNumber(domain.getPrintPersonNumber())
                        .submittedName(domain.getSubmittedName())
                        .insuredNumber(domain.getInsuredNumber())
                        .fdNumber(domain.getFdNumber())
                        .textPersonNumber(domain.getTextPersonNumber())
                        .outputFormat(domain.getOutputFormat())
                        .lineFeedCode(domain.getLineFeedCode())
                .build();

    }
}
