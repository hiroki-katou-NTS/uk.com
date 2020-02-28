package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class EmpAddChangeInfoExport {
    private String scd;
    private String empId;
    private String pId;
    private String companyId;
    private Integer familyId;

    /** 個人番号 - b - A1_1*/
    private String basicPenNumber;

    /** 氏名フリガナ*/
    private String nameKanaPs;

    /** 氏名 */
    private String fullNamePs;

    /** 生年月日*/
    private GeneralDate birthDatePs;

    /** 変更後郵便番号*/
    private String postCodePs;

    /** 変更後住所カナ*/
    private String add1KanaPs;

    /** 変更後住所カナ*/
    private String add2KanaPs;

    /** 変更後住所*/
    private String add1Ps;

    /** 変更後住所*/
    private String add2Ps;

    /**変更前住所*/
    private String add1BeforeChangePs;

    /**変更前住所*/
    private String add2BeforeChangePs;

    /** 住所変更年月日*/
    private GeneralDate startDatePs;

    /** 個人番号 - b - A2_2*/
    private String fmBsPenNum;

    /**短期在留 - b - A1_11*/
    private Integer shortResidentAtr;

    /**海外居住 - b - A1_13*/
    private Integer livingAbroadAtr;

    /**住民票住所以外居所 - b - A1_12*/
    private Integer residenceOtherResidentAtr;

    /**その他 - b - A1_14*/
    private Integer otherAtr;

    /**その他理由 - b - A1_15*/
    private String otherReason;

    /**短期在留 - b - A2_15*/
    private Integer spouseShortResidentAtr;

    /**海外居住者 - b - A2_17*/
    private Integer spouseLivingAbroadAtr;

    /**住民票住所以外居所 - b - A2_16*/
    private Integer spouseResidenceOtherResidentAtr;

    /**その他 - b - A2_18*/
    private Integer spouseOtherAtr;

    /**その他理由 - b - A2_19*/
    private String spouseOtherReason;

    /** 生年月日*/
    private GeneralDate birthDateF;

    /** 氏名フリガナ*/
    private String nameKanaF;

    /** 氏名 */
    private String fullNameF;

    /** 変更後郵便番号 */
    private String postalCodeF;

    /**変更後住所カナ*/
    private String add1KanaF;

    /**変更後住所カナ*/
    private String add2KanaF;

    /**変更後住所*/
    private String add1F;

    /**変更後住所*/
    private String add2F;

    /**住所変更年月日*/
    private GeneralDate startDateF;

    /**変更前住所*/
    private String add1BeforeChangeF;

    /**変更前住所*/
    private String add2BeforeChangeF;

    /**事業所住所 */
    private String address1;

    /**事業所住所 */
    private String address2;

    /**事業所名称*/
    private String bussinessName;

    /**事業主氏名*/
    private String referenceName;

    /**電話番号*/
    private String phoneNumber;

    /**健康保険加入*/
    private boolean healthInsurance;

    /**厚生年金保険加入*/
    private boolean empPenInsurance;

    /**本人住所変更日*/
    private GeneralDate personAddChangeDate;

    /**被扶養配偶者住所変更日*/
    private GeneralDate spouseAddChangeDate;

    /**被保険者整理番号 - a - A2_3*/
    private String healInsurNumber;

    /**被保険者同居区分*/
    private boolean insuredLivingTogether;

    /**事業所整理記号1*/
    private String businessEstCode1;

    /**事業所整理記号2*/
    private String businessEstCode2;

    public EmpAddChangeInfoExport() {
        this.healthInsurance = false;
        this.empPenInsurance = false;
        this.personAddChangeDate = null;
        this.spouseAddChangeDate = null;
    }
}
