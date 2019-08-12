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

public class GuaByTheInsurExportData {

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
    Optional<String> addressKatakana;

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
     * 取得区分（健保・厚年）
     */

}