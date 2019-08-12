package nts.uk.ctx.pr.report.dom.printdata.socinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

/**
 * 社会保険届作成設定
 */
@AllArgsConstructor
@Getter
@Setter
public class SocialInsurNotiCreateSet {
    /**
     * 会社ID
     */
    private String cid;
    /**
     * ユーザID
     */
    private String userId;

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
    private nts.uk.ctx.pr.report.dom.printdata.socinsurnoticreset.SubNameClass submittedName;

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





}
