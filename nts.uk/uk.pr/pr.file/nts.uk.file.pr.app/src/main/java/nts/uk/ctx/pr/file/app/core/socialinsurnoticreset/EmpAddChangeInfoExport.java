package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpAddChangeInfoExport {
    private String empId;
    private String companyId;
    private String familyId;
    private String personId;

    /** 個人番号 */
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

    /** 住所変更年月日*/
    private GeneralDate startDatePs;

    /** 個人番号*/
    private String fmBsPenNum;

    private Integer shortResidentAtr;
    private Integer livingAbroadAtr;
    private Integer residenceOtherResidentAtr;
    private Integer otherAtr;
    private String otherReason;
    private Integer spouseShortResidentAtr;
    private Integer spouseLivingAbroadAtr;
    private Integer spouseResidenceOtherResidentAtr;
    private Integer spouseOtherAtr;
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
    private String add1BeforeChange;

    /**変更前住所*/
    private String add2BeforeChange;

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

    private boolean empPenInsurance;
    private GeneralDate personAddChangeDate;
    private GeneralDate spouseAddChangeDate;
}
