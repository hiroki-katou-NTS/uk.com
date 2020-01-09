package nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.com.time.japanese.JapaneseDate;

/**
 * 雇用保険被保険者資格取得届
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmpInsGetQualifReport {

    /**
     * 1週間の所定労働時間
     */
    private Integer scheduleWorkingTimePerWeek;

    /**
     * 事業所名
     */
    private String officeName;

    /**
     * 事業所番号1
     */
    private String officeNumber1;

    /**
     * 事業所番号2
     */
    private String officeNumber2;

    /**
     * 事業所番号3
     */
    private String officeNumber3;

    /**
     * 個人番号
     */
    private Integer personalNumber;

    /**
     * 元号（生年月日）
     */
    private String eraDateOfBirth;

    /**
     * 取得区分
     */
    private Integer acquisitionAtr;

    /**
     * 変更後氏名
     */
    private String nameAfterChange;

    /**
     * 変更後氏名フリガナ
     */
    private String fullNameAfterChange;

    /**
     * 契約更新条項の有無
     */
    private Integer contractRenewalProvision;

    /**
     * 契約期間の定め
     */
    private Integer setContractPeriod;

    /**
     * 契約開始年月日（和暦）
     */
    private String contractStartDateJp;

    /**
     * 契約終了年月日（和暦）
     */
    private String contractEndDateJp;

    /**
     * 就職経路
     */
    private Integer jobPath;

    /**
     * 性別
     */
    private Integer gender;

    /**
     * 生年月日（和暦）
     */
    private String dateOfBirthJp;

    /**
     * 社員ID
     */
    private String sid;

    /**
     * 職種
     */
    private String occupation;

    /**
     * 被保険者となったことの原因
     */
    private Integer causeOfInsured;

    /**
     * 被保険者氏名
     */
    private String insuredName;

    /**
     * 被保険者氏名フリガナ
     */
    private String insuredFullName;

    /**
     * 被保険者番号
     */
    private String insuredNumber;

    /**
     * 賃金（支払の態様）
     */
    private Integer wagePaymentMode;

    /**
     * 賃金（賃金月額:単位千円）
     */
    private Integer paymentWage;

    /**
     * 資格取得年月日（和暦）
     */
    private String qualificationDateJp;

    /**
     * 雇用形態
     */
    private Integer employmentStatus;

    private String insuredRomanName;

    private String insuredRomanName2;

    private String nationalityRegion;

    private String residenceStatus;

    private String stayPeriod;

    private Integer unqualifiedActivityPermission;

    private Integer contractWorkAtr;

    private String officePostalCode;

    private String officeLocation_1;

    private String officeLocation_2;

    private String businessOwnerName;

    private String officePhoneNumber;

    private JapaneseDate submissionDateJp;

    private String scd;

    private String personalNameKana;

}
