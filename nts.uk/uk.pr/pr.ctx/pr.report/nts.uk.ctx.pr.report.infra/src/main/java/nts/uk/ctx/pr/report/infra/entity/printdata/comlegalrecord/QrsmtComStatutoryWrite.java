package nts.uk.ctx.pr.report.infra.entity.printdata.comlegalrecord;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.pr.report.dom.printdata.comlegalrecord.CompanyStatutoryWrite;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 法定調書用会社
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QRSMT_COMPANY_STATU_WRITE")
public class QrsmtComStatutoryWrite extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QrsmtComStatutoryWritePk comStatutoryWritePk;

    /**
     * 名称
     */
    @Basic(optional = false)
    @Column(name = "NAME")
    public String name;

    /**
     * カナ名
     */
    @Basic(optional = false)
    @Column(name = "KANA_NAME")
    public String kanaName;

    /**
     * 住所１
     */
    @Basic
    @Column(name = "ADDRESS1")
    public String address1;

    /**
     * 住所２
     */
    @Basic
    @Column(name = "ADDRESS2")
    public String address2;

    /**
     * 住所カナ１
     */
    @Basic
    @Column(name = "ADDRESS_KANA1")
    public String addressKana1;

    /**
     * 住所カナ２
     */
    @Basic
    @Column(name = "ADDRESS_KANA2")
    public String addressKana2;

    /**
     * 電話番号
     */
    @Basic
    @Column(name = "PHONE_NUMBER")
    public String phoneNumber;

    /**
     * 郵便番号
     */
    @Basic
    @Column(name = "POSTAL_CODE")
    public String postalCode;

    /**
     * メモ
     */
    @Basic
    @Column(name = "NOTE")
    public String note;

    /**
     * 会社代表者職位
     */
    @Basic
    @Column(name = "REPRESENTATIVE_POSITION")
    public String representativePosition;

    /**
     * 会社代表者名
     */
    @Basic
    @Column(name = "REPRESENTATIVE_NAME")
    public String representativeName;

    /**
     * 紐付け部門
     */
    @Basic
    @Column(name = "LINKED_DEPARTMENT")
    public String linkedDepartment;

    /**
     * 法人番号
     */
    @Basic
    @Column(name = "CORPORATE_NUMBER")
    public String corporateNumber;

    /**
     * 会計事務所電話番号
     */
    @Basic
    @Column(name = "ACOUNTING_OFF_TELE_NUMBER")
    public String acountingOffTeleNumber;

    /**
     * 会計事務所名称
     */
    @Basic
    @Column(name = "ACCOUNTING_OFF_NAME")
    public String accountingOffName;

    /**
     * 給与支払方法と期日1
     */
    @Basic
    @Column(name = "SALA_PAY_METHOD_DUE_DATE1")
    public String salaPayMethodDueDate1;

    /**
     * 給与支払方法と期日2
     */
    @Basic
    @Column(name = "SALA_PAY_METHOD_DUE_DATE2")
    public String salaPayMethodDueDate2;

    /**
     * 給与支払方法と期日3
     */
    @Basic
    @Column(name = "SALA_PAY_METHOD_DUE_DATE3")
    public String salaPayMethodDueDate3;

    /**
     * 経理責任者氏名
     */
    @Basic
    @Column(name = "ACCOUNT_MANA_NAME")
    public String accountManaName;

    /**
     * 事業種目1
     */
    @Basic
    @Column(name = "BUSINESS_LINE1")
    public String businessLine1;

    /**
     * 事業種目2
     */
    @Basic
    @Column(name = "BUSINESS_LINE2")
    public String businessLine2;

    /**
     * 事業種目3
     */
    @Basic
    @Column(name = "BUSINESS_LINE3")
    public String businessLine3;

    /**
     * 所轄税務署
     */
    @Basic
    @Column(name = "TAX_OFFICE")
    public String taxOffice;

    /**
     * 振込希望金融機関所在地
     */
    @Basic
    @Column(name = "VIB_LOCA_FIN_IUNSTITUTION")
    public String vibLocaFinIunstitution;

    /**
     * 振込希望金融機関名称
     */
    @Basic
    @Column(name = "NAME_BANK_TRAN_INSTITUSTION")
    public String nameBankTranInstitustion;

    /**
     * 連絡者氏名
     */
    @Basic
    @Column(name = "CONTACT_NAME")
    public String contactName;

    /**
     * 連絡者所属課係
     */
    @Basic
    @Column(name = "CONTACT_CLASS")
    public String contactClass;

    /**
     * 連絡者電話番号
     */
    @Basic
    @Column(name = "CONTACT_PHONE_NUMBER")
    public String contactPhoneNumber;

    @Override
    protected Object getKey() {
        return comStatutoryWritePk;
    }

    public CompanyStatutoryWrite toDomain() {
        return new CompanyStatutoryWrite(this.comStatutoryWritePk.cid, this.comStatutoryWritePk.code, this.name, this.kanaName, this.address1, this.address2, this.addressKana1, this.addressKana2, this.phoneNumber, this.postalCode, this.note, this.representativePosition, this.representativeName, this.linkedDepartment, this.corporateNumber, this.acountingOffTeleNumber, this.accountingOffName, this.salaPayMethodDueDate1, this.salaPayMethodDueDate2, this.salaPayMethodDueDate3, this.accountManaName, this.businessLine1, this.businessLine2, this.businessLine3, this.taxOffice, this.vibLocaFinIunstitution, this.nameBankTranInstitustion, this.contactName, this.contactClass, this.contactPhoneNumber);
    }

    public static QrsmtComStatutoryWrite toEntity(CompanyStatutoryWrite domain) {
        return new QrsmtComStatutoryWrite(new QrsmtComStatutoryWritePk(domain.getCID(), domain.getCode().v()), domain.getName().v(),
                domain.getBasicInformation().getKanaName().v(),
                domain.getBasicInformation().getAddressInformation().getAddress1() == null ? null : domain.getBasicInformation().getAddressInformation().getAddress1().get().v(),
                domain.getBasicInformation().getAddressInformation().getAddress2() == null ? null : domain.getBasicInformation().getAddressInformation().getAddress2().get().v(),
                domain.getBasicInformation().getAddressInformation().getAddressKana1() == null ? null : domain.getBasicInformation().getAddressInformation().getAddressKana1().get().v(),
                domain.getBasicInformation().getAddressInformation().getAddressKana2() == null ? null : domain.getBasicInformation().getAddressInformation().getAddressKana2().get().v(),
                domain.getBasicInformation().getAddressInformation().getPhoneNumber() == null ? null : domain.getBasicInformation().getAddressInformation().getPhoneNumber().get().v(),
                domain.getBasicInformation().getAddressInformation().getPostalCode() == null ? null : domain.getBasicInformation().getAddressInformation().getPostalCode().get().v(),
                domain.getBasicInformation().getNotes().isPresent() ? domain.getBasicInformation().getNotes().get().v() : null,
                domain.getBasicInformation().getClubRepresentativePosition().map(PrimitiveValueBase::v).orElse(null),
                domain.getBasicInformation().getClubRepresentativeName().map(PrimitiveValueBase::v).orElse(null),
                domain.getBasicInformation().getLinkingDepartment().isPresent() ? domain.getBasicInformation().getLinkingDepartment().get() : null,
                domain.getBasicInformation().getCorporateNumber().isPresent() ? domain.getBasicInformation().getCorporateNumber().get().v() : null,
                domain.getSummaryTableInformation().getAccountingOfficeTelephoneNumber().map(PrimitiveValueBase::v).orElse(null),
                domain.getSummaryTableInformation().getAccountingOfficeName().map(PrimitiveValueBase::v).orElse(null),
                domain.getSummaryTableInformation().getSalaryPaymentMethodAndDueDate1().map(PrimitiveValueBase::v).orElse(null),
                domain.getSummaryTableInformation().getSalaryPaymentMethodAndDueDate2().map(PrimitiveValueBase::v).orElse(null),
                domain.getSummaryTableInformation().getSalaryPaymentMethodAndDueDate3().map(PrimitiveValueBase::v).orElse(null),
                domain.getSummaryTableInformation().getAccountManagerName().map(PrimitiveValueBase::v).orElse(null),
                domain.getSummaryTableInformation().getBusinessLine1().map(PrimitiveValueBase::v).orElse(null),
                domain.getSummaryTableInformation().getBusinessLine2().map(PrimitiveValueBase::v).orElse(null),
                domain.getSummaryTableInformation().getBusinessLine3().map(PrimitiveValueBase::v).orElse(null),
                domain.getSummaryTableInformation().getTaxOffice().map(PrimitiveValueBase::v).orElse(null),
                domain.getSummaryTableInformation().getVibrantLocationFinancialInstitutions().map(PrimitiveValueBase::v).orElse(null),
                domain.getSummaryTableInformation().getNameBankTransferInstitution().map(PrimitiveValueBase::v).orElse(null),
                domain.getSummaryTableInformation().getContactName().map(PrimitiveValueBase::v).orElse(null),
                domain.getSummaryTableInformation().getContactClass().map(PrimitiveValueBase::v).orElse(null),
                domain.getSummaryTableInformation().getContactPhoneNumber().map(PrimitiveValueBase::v).orElse(null));
    }
}