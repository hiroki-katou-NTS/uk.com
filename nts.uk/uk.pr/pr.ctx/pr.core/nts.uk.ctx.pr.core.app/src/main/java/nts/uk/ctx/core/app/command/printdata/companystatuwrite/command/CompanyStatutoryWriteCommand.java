package nts.uk.ctx.core.app.command.printdata.companystatuwrite.command;

import lombok.Value;

@Value
public class CompanyStatutoryWriteCommand
{

    /**
     * 会社ID
     */
    private String cID;

    /**
     * コード
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * カナ名
     */
    private String kanaName;

    /**
     * 住所１
     */
    private String address1;

    /**
     * 住所２
     */
    private String address2;

    /**
     * 住所カナ１
     */
    private String addressKana1;

    /**
     * 住所カナ２
     */
    private String addressKana2;

    /**
     * 電話番号
     */
    private String phoneNumber;

    /**
     * 郵便番号
     */
    private String postalCode;

    /**
     * メモ
     */
    private String notes;

    /**
     * 会社代表者職位
     */
    private String clubRepresentativePosition;

    /**
     * 会社代表者名
     */
    private String clubRepresentativeName;

    /**
     * 紐付け部門
     */
    private String linkingDepartment;

    /**
     * 法人番号
     */
    private Integer corporateNumber;

    /**
     * 会計事務所電話番号
     */
    private String accountingOfficeTelephoneNumber;

    /**
     * 会計事務所名称
     */
    private String accountingOfficeName;

    /**
     * 給与支払方法と期日1
     */
    private String salaryPaymentMethodAndDueDate1;

    /**
     * 給与支払方法と期日2
     */
    private String salaryPaymentMethodAndDueDate2;

    /**
     * 給与支払方法と期日3
     */
    private String salaryPaymentMethodAndDueDate3;

    /**
     * 経理責任者氏名
     */
    private String accountManagerName;

    /**
     * 事業種目1
     */
    private String businessLine1;

    /**
     * 事業種目2
     */
    private String businessLine2;

    /**
     * 事業種目3
     */
    private String businessLine3;

    /**
     * 所轄税務署
     */
    private String taxOffice;

    /**
     * 振込希望金融機関所在地
     */
    private String vibrantLocationFinancialInstitutions;

    /**
     * 振込希望金融機関名称
     */
    private String nameBankTransferInstitution;

    /**
     * 連絡者氏名
     */
    private String contactName;

    /**
     * 連絡者所属課係
     */
    private String contactClass;

    /**
     * 連絡者電話番号
     */
    private String contactPhoneNumber;


}
