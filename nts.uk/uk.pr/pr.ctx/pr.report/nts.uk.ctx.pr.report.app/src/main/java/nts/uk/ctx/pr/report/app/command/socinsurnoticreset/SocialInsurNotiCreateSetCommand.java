package nts.uk.ctx.pr.report.app.command.socinsurnoticreset;

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

}
