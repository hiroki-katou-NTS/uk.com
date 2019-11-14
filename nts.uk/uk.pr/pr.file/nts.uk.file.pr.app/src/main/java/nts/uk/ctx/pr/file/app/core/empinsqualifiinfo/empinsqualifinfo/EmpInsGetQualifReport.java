package nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
     * 事業所番号
     */
    private Integer officeNumber;

    /**
     * 個人番号
     */
    private Integer personalNumber;

    /**
     * 元号（生年月日）
     */
    private String originalName;

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
    private String estContractPeriod;

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
    private Integer occupation;

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
    private String nameOfInsuredPeople;

    /**
     * 被保険者番号
     */
    private Integer insuredNumber;

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

}
