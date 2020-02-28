package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@NoArgsConstructor

public class GuaByTheInsurExportDto {
    /**
     * 提出日
     */
    private GeneralDate filingDate;
    /**
     * 事業所番号
     */
    private String officeNumber;

    /**
     * 事業所整理記号1
     */
    private String businessstablishmentbCode1;
    /**
     * 事業所整理記号2
     */
    private String businessstablishmentbCode2;
    /**
     * 事業所郵便番号
     */
    private String officePostalCode;
    /**
     * 事業所住所１
     */
    private String officeAddress1;
    /**
     * 事業所住所２
     */
    private String officeAddress2;
    /**
     * 事業所名称
     */
    private String businessName;
    /**
     * 事業所氏名
     */
    private String businessName1;
    /**
     * 電話番号
     */
    private String phoneNumber;
    /**
     * 被保険者の氏名（氏）
     */
    private String nameOfInsuredPersonMr;
    /**
     * 被保険者の氏名（名）
     */
    private String nameOfInsuredPerson;
    /**
     * 被保険者の氏名（氏ｶﾅ）
     */
    private String nameOfInsuredPersonMrK;
    /**
     * 被保険者の氏名（名ｶﾅ）
     */
    private String nameOfInsuredPerson1;

    /**
     * 生年月日(昭和)
     */
    private String brithDayShowa;

    /**
     * 生年月日(平成)
     */
    private String brithDayHeisei;
    /**
     * 生年月日(令和)
     */
    private String brithDayRyowa;
    /**
     * 生年月日
     */
    private String brithDay;
    /**
     *
     */
    private String hisId;
    /**
     *坑内員区分
     */
    private int undergoundDivision;
    //C2_10
    /**
     * 種別（男）
     */
    private int typeMale;
    /**
     *  種別（女）
     */
    private int typeFeMale;
    /**
     *  種別（坑内員）
     */
    private int typeMiner;
    /**
     * 種別（男(基金)）
     */
    private int typeMaleFund;
    /**
     *  種別（女(基金)）
     */
    private int typeFeMaleFund;
    /**
     * 種別（坑内員(基金)）
     */
    private int typeMineWorkerFund;

    /**
     * 取得区分（健保・厚年）
     */
    private int acquiCtgHealthInsurWelfare;
    /**
     * 取得区分（共済出向）
     */
    private int acquiCtgMutualAidSeconded;
    /**
     * 取得区分（船保任継）
     *
     */
    private int acquiCtgShipTransfer;
    /**
     * 取得区分（船保任継）
     */
    private String personalNumber;
    /**
     * 資格取得年月日(令和)
     */
    private String dateOfQualifiRyowa;
    /**
     *  資格取得年月日
     */
    private String  qualificationDate;
    /**
     * 被扶養者（無）
     */
    private int dependentNo;
    /**
     * 被扶養者（有）
     */
    private int dependentYes;
    /**
     * 報酬月額（通貨によるものの額）
     */
    private int monRemunerationAmountInCurrency;
    /**
     *  報酬月額（現物によるものの額）
     */
    private int monRemunerationAmountOfActualItem;
    /**
     *  報酬月額（合計）
     */
    private String compenMonthlyAamountTotal;
    /**
     * 郵便番号
     */
    private String postalCode;
    /**
     *  住所
     */
    private String streetAddress;
    /**
     * 住所カタカナ
     */
    private String addressKana;
    /**
     * 備考（70歳以上被用者）
     */
    private int remarks70OldAndOverEmployees;
    /**
     *  備考（二以上事業所勤務者）
     */
    private int remarksTwoOrMoreOfficeWorkers;
    /**
     * 備考（短時間労働者）
     */
    private int remarksShortTimeWorkers;
    /**
     * 備考（退職後の継続再雇用）
     */
    private int remarksContReemAfterRetirement;
    /**
     * 備考（その他）
     */
    private int remarksOther;
    /**
     *  備考（その他内容）
     */
    private String remarksOtherContent;
    /**
     * 理由（海外在住）
     */
    private int reasonResidentAbroad;
    /**
     *  理由（短期在留）
     */
    private int reasonShortTermResidence;
    /**
     * 理由（その他）
     */
    private int reasonOther;

    private int shortStay;
    /**
     * 理由（その他内容）
     */
    private String reasonOtherContent;

    private String officeCd;

    private String employeeCd;

    private int Gender;
}
