package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOffice;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.MultiEmpWorkInfo;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.SocialInsurAcquisiInfor;
import nts.uk.shr.com.company.CompanyInfor;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/**
 * 取得届情報
 */

public class GuaByTheInsurExportDto {

    /**
     * 70歳以上被用者区分
     */
    boolean moreThan70Percent;

    /**
     * 社員ID
     */
    String employeeId;

    /**
     * 事業所住所1
     */
    Optional<CompanyInfor> officeAndressOne;

    /**
     * 事業所住所2
     */
    Optional<CompanyInfor> officeAndressTwo;

    /**
     * 事業所名称
     */
    Optional<CompanyInfor> bussinessName;

    /**
     * 事業所整理記号1
     */
    Optional<SocialInsuranceOffice> busEstablishmentCodeOne;

    /**
     * 事業所整理記号2
     */
    Optional<SocialInsuranceOffice> busEstablishmentCodeTwo;

    /**
     * 事業所氏名
     */
    Optional<CompanyInfor> bussinessName2;

    /**
     * 事業所番号
     */
    Optional<SocialInsuranceOffice> officeNumber;

    /**
     * 事業所郵便番号
     */
    Optional<CompanyInfor> officePostalCode;

    /**
     * 住所カタカナ
     */
    Optional<Person> addressKatakana;

    /**
     * 個人番号
     */
    Optional<Integer> personalNumber;

    /**
     * 健康保険取得日
     */
    Optional<GeneralDate> healInsurAcquiDate;

    /**
     * 健康保険取得日時点所属社保事業所
     */
    Optional<SocialInsuranceOffice> theDateOfHealInsurAcquisition;

    /**
     * 備考（その他）
     */
    Optional<SocialInsurAcquisiInfor> remarksOther;

    /**
     * 備考（その他内容）
     */
    Optional<SocialInsurAcquisiInfor> remarksOtherContents;

    /**
     * 備考（二以上事業所勤務者）
     */
    Optional<MultiEmpWorkInfo> remarksTwoOrMoreOffice;

    /**
     * 備考（短時間労働者）
     */
    Optional<MultiEmpWorkInfo> remarksShortTimeWorker;


    /**
     * 備考（退職後の継続再雇用者）
     */
    Optional<SocialInsurAcquisiInfor> remarksContinuReemAter;

    /**
     * 厚生年金保険取得日
     */
    Optional<GeneralDate> empPensionInsurAcquiDate;

    /**
     * 厚生年金保険取得日時点所属社保事業所
     */
    Optional<SocialInsuranceOffice> theDateOfEmpPensionInsurAcquiDate;

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
     * 報酬月額（合計）
     */
    Optional<SocialInsurAcquisiInfor> compMonthlyAmountTotal;

    /**
     * 報酬月額（現物によるものの額）
     */
    Optional<SocialInsurAcquisiInfor> monthRemunAmountOfActualItem;

    /**
     * 報酬月額（通貨によるものの額）
     */
    Optional<SocialInsurAcquisiInfor> monthRemunAmountInCurrency;

    /**
     * 提出日和暦
     */
    Optional<GeneralDate> submissionDate;

    /**
     * 生年月日（令和）
     */
    Optional<GeneralDate> dateOfBirthRyowa;

    /**
     * 生年月日（平成）
     */
    Optional<GeneralDate> dateOfBirthHeisei;

    /**
     * 生年月日（昭和）
     */
    Optional<GeneralDate> dateOfBirthShowa;

    /**
     * 種別（坑内員（基金））
     */
    Optional<Person> typeMineWorkerFund;

    /**
     * 種別（坑内員）
     */
    Optional<Person> typeMine;

    /**
     * 種別（女（基金））
     */
    Optional<Person> typeFemaleFund;

    /**
     * 種別（女）
     */
    Optional<Person> typeFemale;

    /**
     * 種別（男（基金））
     */
    Optional<Person> typeMaleFund;

    /**
     * 種別（男）
     */
    Optional<Person> typeMale;

    /**
     * 被保険者の氏名（名）
     */
    Optional<Person> nameOfInsuredPerson;

    /**
     * 被保険者の氏名（名ｶﾅ）
     */
    Optional<Person> fullNameOfTheInsurPerson;

    /**
     * 被保険者の氏名（氏）
     */
    Optional<Person> firstNameOfInsuredPerson2;

    /**
     * 被保険者整理番号
     */
    Optional<String> insurPersonRefNumber;

    /**
     *  被扶養者（有）
     */
    Optional<SocialInsurAcquisiInfor> dependentYes;

    /**
     *  被扶養者（無）
     */
    Optional<SocialInsurAcquisiInfor> dependentNo;

    /**
     *  資格取得年月日
     */
    Optional<GeneralDate> qualifiDate;


    /**
     * 資格取得年月日（令和）
     */
    Optional<GeneralDate> qualifiDateRyowa;

    /**
     * 郵便番号
     */
    Optional<SocialInsurAcquisiInfor> postalCode;

    /**
     * 電話番号
     */
    Optional<CompanyInfor> phoneNumber;
}
