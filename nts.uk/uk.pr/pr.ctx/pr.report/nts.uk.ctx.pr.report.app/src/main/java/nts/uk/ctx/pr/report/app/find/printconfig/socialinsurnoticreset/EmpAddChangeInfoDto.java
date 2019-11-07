package nts.uk.ctx.pr.report.app.find.printconfig.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
@AllArgsConstructor
@Value
@Builder
public class EmpAddChangeInfoDto {
    /**
     * SID
     */
    private String sid;

    /**
     * 短期在留者
     */
    public int shortResidentAtr;

    /**
     * 海外居住者
     */
    public int livingAbroadAtr;

    /**
     * 住民票住所以外居所
     */
    public int residenceOtherResidentAtr;

    /**
     * その他
     */
    public int otherAtr;

    /**
     * その他理由
     */
    public String otherReason;

    /**
     * 短期在留者
     */
    public int spouseShortResidentAtr;

    /**
     * 海外居住者
     */
    public int spouseLivingAbroadAtr;

    /**
     * 住民票住所以外居所
     */
    public int spouseResidenceOtherResidentAtr;

    /**
     * その他
     */
    public int spouseOtherAtr;

    /**
     * その他理由
     */
    public String spouseOtherReason;

    public String  basicPenNumber;

}
