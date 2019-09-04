package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.*;
import nts.uk.ctx.pr.report.dom.printdata.comlegalrecord.Name;
import nts.uk.ctx.pr.report.dom.printdata.comlegalrecord.RepresentativeName;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.FdNumber;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.EmployWelPenInsurAche;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.PitInsiderDivision;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empfunmeminfor.FundMembershipNumber;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor.HealthCarePortInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.*;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.inforunionfundnoti.emphealinsurassinfor.HealInsurItems;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.inforunionfundnoti.emppensionfundinfor.AdditiAppCategory;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.inforunionfundnoti.emppensionfundinfor.FundSpecificItems;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.inforunionfundnoti.emppensionfundinfor.MonthlyFeeForFund;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.inforunionfundnoti.emppensionfundinfor.SubscriptionType;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

/**
 * 取得届情報
 */

public class  AcquiNotifiInformation {

    /**
     * 70歳以上被用者区分
     */
    boolean moreThan70Percent;

    /**
     * 社員ID
     */
    String employeeId;

    /**
     * FD番号
     */
    Optional<FdNumber> fdNumber;

    /**
     * 事業所住所1
     */
    Optional<Address1> officeAndressOne;

    /**
     * 事業所住所2
     */
    Optional<Address2> officeAndressTwo;

    /**
     * 事業所名称
     */
    Optional<Name> bussinessName;

    /**
     * 事業所整理記号1
     */
    Optional<HealthInsuranceOfficeNumber1> healthInsuranceOfficeNumber1;

    /**
     * 事業所整理記号2
     */
    Optional<HealthInsuranceOfficeNumber2> healthInsuranceOfficeNumber2;

    /**
     * 事業所氏名
     */
    Optional<RepresentativeName> bussinessName2;

    /**
     * 事業所番号
     */
    Optional<HealthInsuranceOfficeNumber> officeNumber;

    /**
     * 事業所郵便番号
     */
    Optional<PostalCode> officePostalCode;

    /**
     * 住所: String(160)
     */
    Optional<String> streetAdress;
    /**
     * 住所カタカナ: String(160)
     */
    Optional<String> streetAdressKana;

    /**
     * 個人番号 : String(12)
     */
    Optional<String> personalNumber;
    /**
     * 健保固有項目
     */
    Optional<HealInsurItems> healInsurItems;

    /**
     * 健保組合事業所番号
     */
    Optional<HealthInsuranceUnionOfficeNumber> HealInsurAssOffNumber;

    /**
     * 健保組合番号
     */
    Optional<HealthCarePortInfor> HealInsurAssNumber;
    /**
     * 備考（その他）
     */
    Optional<Boolean> remarksOther;

    /**
     * 備考（その他内容）
     */
    Optional<QualificationAcquiNoti> remarksOtherContents;

    /**
     * 備考（二以上事業所勤務者）
     */
    Optional<Boolean> remarksTwoOrMoreOffice;

    /**
     * 備考（短時間労働者）
     */
    Optional<Boolean> remarksShortTimeWorker;

    /**
     * 備考（退職後の継続再雇用者）
     */
    Optional<Boolean> remarksContinuReemAter;
    /**
     * 加入形態区分
     */
    Optional<SubscriptionType> subscriptionType;
    /**
     * 加算給与月額
     */
    Optional<MonthlyFeeForFund> addMonthlySalary;
    /**
     * 加算適用区分(給与)
     */
    Optional<AdditiAppCategory> addAppCtgSalary;
    /**
     * 厚生年金事業所番号
     */
    Optional<WelfarePensionOfficeNumber> EmplPensionOfficeNumber;
    /**
     * 厚生年金基金番号
     */
    Optional<WelfarePensionFundNumber> EmpPensionFundNumber;
    /**
     * 取得区分（健保・厚年）
     */
    Optional<SocialInsurAcquisiInfor> acquiCtgHealInsurOrWelfare;
    /**
     * 取得区分（共済出向）
     */
    Optional<SocialInsurAcquisiInfor> acquiCtgMutualAidSeconded;
    /**
     * 取得区分（船保任継）
     */
    Optional<SocialInsurAcquisiInfor> acquiCtgShipTranfer;
    /**
     * 基礎年金番号
     */
    Optional<BasicPenNumber> basicPensionNumber;
    /**
     * 基金加入員番号
     */
    Optional<FundMembershipNumber> fundMembershipNumber;
    /**
     * 基金固有項目１
     */
    Optional<FundSpecificItems> fundSpecificItems1;
    /**
     * 基金固有項目2
     */
    Optional<FundSpecificItems> fundSpecificItems2;
    /**
     * 基金固有項目3
     */
    Optional<FundSpecificItems> fundSpecificItems3;
    /**
     * 基金固有項目4
     */
    Optional<FundSpecificItems> fundSpecificItems4;
    /**
     * 基金固有項目5
     */
    Optional<FundSpecificItems> fundSpecificItems5;
    /**
     * 基金固有項目6
     */
    Optional<FundSpecificItems> fundSpecificItems6;
    /**
     * 基金固有項目7
     */
    Optional<FundSpecificItems> fundSpecificItems7;
    /**
     * 基金固有項目8
     */
    Optional<FundSpecificItems> fundSpecificItems8;
    /**
     * 基金固有項目9
     */
    Optional<FundSpecificItems> fundSpecificItems9;
    /**
     * 基金固有項目１０
     */
    Optional<FundSpecificItems> fundSpecificItems10;

    /**
     * 報酬月額（合計）
     */
    Optional<RemuneraMonthly> compMonthlyAmountTotal;
    /**
     * 報酬月額（現物によるものの額）
     */
    Optional<RemuneraMonthly> monthRemunAmountOfActualItem;

    /**
     * 報酬月額（通貨によるものの額）
     */
    Optional<RemuneraMonthly> monthRemunAmountInCurrency;
    /**
     * 得喪期間
     */
    Optional<EmployWelPenInsurAche> mourningPeriod;
    /**
     * 提出日
     */
    Optional<GeneralDate> filingDate;
    /**
     * 標準給与月額
     */
    Optional<MonthlyFeeForFund> standardMonthlySalary;
    /**
     * 理由（その他）
     */
    Optional<Boolean> reasonOther;
    /**
     * 理由（その他内容）
     */

    Optional<ReaForNotiOfQuatification> reasonOtherContents;
    /**
     * 理由（海外在住）
     */
    Optional<Boolean> reasonResidenAbroad;
    /**
     * 理由（短期在留）
     */
    Optional<Boolean> reasonShortTermResidence;
    /**
     * 生年月日
     */
    Optional<GeneralDate> birthday;
    /**
     * 種別（坑内員（基金））
     */
    Optional<PitInsiderDivision> typeMineWorkerFund;
    /**
     * 種別（坑内員）
     */
    Optional<PitInsiderDivision> typeMiner;
    /**
     * 種別（女（基金））
     */
    Optional<PitInsiderDivision> typeFemaleFund;
    /**
     * 種別（女）
     */
    /** The gender.
     * Male(1), Female(2)
     * */
    // 性別
    Optional<Integer> typeFemale;
    /**
     * 種別（男（基金））
     */
    Optional<PitInsiderDivision> typeMaleFund;
    /**
     * 種別（女）
     */
    /** The gender.
     * Male(1), Female(2)
     * */
    // 性別
    Optional<Integer> typeMale;
    /**
     * 第２加算給与月額
     */
    Optional<MonthlyFeeForFund> secondAddMonthlySalary;
    /**
     * 第２標準給与月額
     */

    Optional<MonthlyFeeForFund> secondStadardMonthlySalary;
    /**
     * 被保険者の氏名（名） : String 40
     */
    Optional<String> nameOfInsuredPerson;
    /**
     * 被保険者の氏名（名ｶﾅ） : String 40
     */
    Optional<String> fullNameOfTheInsurPerson;
    /**
     * 被保険者の氏名（氏） : String 40
     */
    Optional<String> firstNameOfInsuredPerson2;
    /**
     * 被保険者の氏名（氏ｶﾅ）: String 40
     */
    Optional<String> insuredPersonName;
    /**
     * 被扶養者（有）
     */
    Optional<DepenNotiAttachCtg> dependentYes;
    /**
     * 被扶養者（無）
     */
    Optional<DepenNotiAttachCtg> dependentNo;
    /**
     * 資格取得年月日
     */
    Optional<GeneralDate> qualifiDate;
    /**
     * 適用形態区分
     */
    Optional<SubscriptionType> applFormClass;
    /**
     * 郵便番号
     */
    Optional<PostalCode> postalCode;
    /**
     * 都道府県コード
     */
    Optional<String> prefectureCode;
    /**
     * 電話番号
     */
    Optional<PhoneNumber> phoneNumber;


}
