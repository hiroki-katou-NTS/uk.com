package nts.uk.ctx.pr.report.app.command.printconfig.socialinsurnoticreset;

import lombok.Value;
@Value
public class SocialInsurNotiCreateSetCommand {

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
     * 被保険者整理番号
     */
    private int insuredNumber;

    private int submittedName;

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

}
