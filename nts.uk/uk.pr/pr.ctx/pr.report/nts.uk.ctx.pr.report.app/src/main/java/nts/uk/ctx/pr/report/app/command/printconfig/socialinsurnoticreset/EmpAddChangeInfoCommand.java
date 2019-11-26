package nts.uk.ctx.pr.report.app.command.printconfig.socialinsurnoticreset;


import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class EmpAddChangeInfoCommand {
    /**
     * SID
     */
    private String sid;

    /**
     * 短期在留者
     */
    private int shortResidentAtr;

    /**
     * 海外居住者
     */
    private int livingAbroadAtr;

    /**
     * 住民票住所以外居所
     */
    private int residenceOtherResidentAtr;

    /**
     * その他
     */
    private int otherAtr;

    /**
     * その他理由
     */
    private String otherReason;

    /**
     * 短期在留者
     */
    private int spouseShortResidentAtr;

    /**
     * 海外居住者
     */
    private int spouseLivingAbroadAtr;

    /**
     * 住民票住所以外居所
     */
    private int spouseResidenceOtherResidentAtr;

    /**
     * その他
     */
    private int spouseOtherAtr;

    /**
     * その他理由
     */
    private String spouseOtherReason;

    private String  basicPenNumber;
}
