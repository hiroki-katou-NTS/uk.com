package nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.InsurPersonNumDivision;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.SocialInsurOutOrder;

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
    private nts.uk.ctx.pr.report.dom.printdata.socinsurnoticreset.PersonalNumClass printPersonNumber;
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
    private Optional<nts.uk.ctx.pr.report.dom.printdata.socinsurnoticreset.FdNumber> fdNumber;

    /**
     * テキスト個人番号
     */
    private Optional<nts.uk.ctx.pr.report.dom.printdata.socinsurnoticreset.TextPerNumberClass> textPersonNumber;

    /**
     * 出力形式
     */
    private Optional<nts.uk.ctx.pr.report.dom.printdata.socinsurnoticreset.OutputFormatClass> outputFormat;

    /**
     * 改行コード
     */
    private Optional<nts.uk.ctx.pr.report.dom.printdata.socinsurnoticreset.LineFeedCode> lineFeedCode;





}
