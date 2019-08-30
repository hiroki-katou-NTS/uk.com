package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.Data;

@Data
public class SocialInsurNotiCreateSetQuery {
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
    //payroll value
    /**
     * 対象区分
     */
    private int targetAtr;
}
