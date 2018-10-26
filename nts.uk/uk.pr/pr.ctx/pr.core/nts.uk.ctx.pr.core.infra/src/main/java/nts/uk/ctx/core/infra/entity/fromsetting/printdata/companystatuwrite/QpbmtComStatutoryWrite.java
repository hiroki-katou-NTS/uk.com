package nts.uk.ctx.core.infra.entity.fromsetting.printdata.companystatuwrite;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.core.dom.printdata.CompanyStatutoryWrite;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 法定調書用会社
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_COMPANY_STATU_WRITE")
public class QpbmtComStatutoryWrite extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtComStatutoryWritePk comStatutoryWritePk;
    
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
    @Basic(optional = true)
    @Column(name = "ADDRESS1")
    public String address1;
    
    /**
    * 住所２
    */
    @Basic(optional = true)
    @Column(name = "ADDRESS2")
    public String address2;
    
    /**
    * 住所カナ１
    */
    @Basic(optional = true)
    @Column(name = "ADDRESS_KANA1")
    public String addressKana1;
    
    /**
    * 住所カナ２
    */
    @Basic(optional = true)
    @Column(name = "ADDRESS_KANA2")
    public String addressKana2;
    
    /**
    * 電話番号
    */
    @Basic(optional = true)
    @Column(name = "PHONE_NUMBER")
    public String phoneNumber;
    
    /**
    * 郵便番号
    */
    @Basic(optional = true)
    @Column(name = "POSTAL_CODE")
    public String postalCode;
    
    /**
    * メモ
    */
    @Basic(optional = true)
    @Column(name = "NOTE")
    public String note;
    
    /**
    * 会社代表者職位
    */
    @Basic(optional = true)
    @Column(name = "REPRESENTATIVE_POSITION")
    public String representativePosition;
    
    /**
    * 会社代表者名
    */
    @Basic(optional = true)
    @Column(name = "REPRESENTATIVE_NAME")
    public String representativeName;
    
    /**
    * 紐付け部門
    */
    @Basic(optional = true)
    @Column(name = "LINKED_DEPARTMENT")
    public String linkedDepartment;
    
    /**
    * 法人番号
    */
    @Basic(optional = true)
    @Column(name = "CORPORATE_NUMBER")
    public BigDecimal corporateNumber;
    
    /**
    * 会計事務所電話番号
    */
    @Basic(optional = true)
    @Column(name = "ACOUNTING_OFF_TELE_NUMBER")
    public String acountingOffTeleNumber;
    
    /**
    * 会計事務所名称
    */
    @Basic(optional = true)
    @Column(name = "ACCOUNTING_OFF_NAME")
    public String accountingOffName;
    
    /**
    * 給与支払方法と期日1
    */
    @Basic(optional = true)
    @Column(name = "SALA_PAY_METHOD_DUE_DATE1")
    public String salaPayMethodDueDate1;
    
    /**
    * 給与支払方法と期日2
    */
    @Basic(optional = true)
    @Column(name = "SALA_PAY_METHOD_DUE_DATE2")
    public String salaPayMethodDueDate2;
    
    /**
    * 給与支払方法と期日3
    */
    @Basic(optional = true)
    @Column(name = "SALA_PAY_METHOD_DUE_DATE3")
    public String salaPayMethodDueDate3;
    
    /**
    * 経理責任者氏名
    */
    @Basic(optional = true)
    @Column(name = "ACCOUNT_MANA_NAME")
    public String accountManaName;
    
    /**
    * 事業種目1
    */
    @Basic(optional = true)
    @Column(name = "BUSINESS_LINE1")
    public String businessLine1;
    
    /**
    * 事業種目2
    */
    @Basic(optional = true)
    @Column(name = "BUSINESS_LINE2")
    public String businessLine2;
    
    /**
    * 事業種目3
    */
    @Basic(optional = true)
    @Column(name = "BUSINESS_LINE3")
    public String businessLine3;
    
    /**
    * 所轄税務署
    */
    @Basic(optional = true)
    @Column(name = "TAX_OFFICE")
    public String taxOffice;
    
    /**
    * 振込希望金融機関所在地
    */
    @Basic(optional = true)
    @Column(name = "VIB_LOCA_FIN_IUNSTITUTION")
    public String vibLocaFinIunstitution;
    
    /**
    * 振込希望金融機関名称
    */
    @Basic(optional = true)
    @Column(name = "NAME_BANK_TRAN_INSTITUSTION")
    public String nameBankTranInstitustion;
    
    /**
    * 連絡者氏名
    */
    @Basic(optional = true)
    @Column(name = "CONTACT_NAME")
    public String contactName;
    
    /**
    * 連絡者所属課係
    */
    @Basic(optional = true)
    @Column(name = "CONTACT_CLASS")
    public String contactClass;
    
    /**
    * 連絡者電話番号
    */
    @Basic(optional = true)
    @Column(name = "CONTACT_PHONE_NUMBER")
    public String contactPhoneNumber;
    
    @Override
    protected Object getKey()
    {
        return comStatutoryWritePk;
    }

    public CompanyStatutoryWrite toDomain() {
        return new CompanyStatutoryWrite(this.comStatutoryWritePk.cid, this.comStatutoryWritePk.code, this.name, this.kanaName, this.address1, this.address2, this.addressKana1, this.addressKana2, this.phoneNumber, this.postalCode, this.note, this.representativePosition, this.representativeName, this.linkedDepartment, this.corporateNumber, this.acountingOffTeleNumber, this.accountingOffName, this.salaPayMethodDueDate1, this.salaPayMethodDueDate2, this.salaPayMethodDueDate3, this.accountManaName, this.businessLine1, this.businessLine2, this.businessLine3, this.taxOffice, this.vibLocaFinIunstitution, this.nameBankTranInstitustion, this.contactName, this.contactClass, this.contactPhoneNumber);
    }
    public static QpbmtComStatutoryWrite toEntity(CompanyStatutoryWrite domain) {
        return new QpbmtComStatutoryWrite(new QpbmtComStatutoryWritePk(domain.getCID(), domain.getCode().v()),domain.getName().v(),
                domain.getBasicInformation().getKanaName().v(),
                domain.getBasicInformation().getAddressInformation().getAddress1() == null ? null : domain.getBasicInformation().getAddressInformation().getAddress1().get().v(),
                domain.getBasicInformation().getAddressInformation().getAddress2() == null ? null : domain.getBasicInformation().getAddressInformation().getAddress2().get().v(),
                domain.getBasicInformation().getAddressInformation().getAddressKana1() == null ? null :  domain.getBasicInformation().getAddressInformation().getAddressKana1().get().v(),
                domain.getBasicInformation().getAddressInformation().getAddressKana2() == null ? null : domain.getBasicInformation().getAddressInformation().getAddressKana2().get().v(),
                domain.getBasicInformation().getAddressInformation().getPhoneNumber() == null ? null : domain.getBasicInformation().getAddressInformation().getPhoneNumber().get().v(),
                domain.getBasicInformation().getAddressInformation().getPostalCode() == null ? null : domain.getBasicInformation().getAddressInformation().getPostalCode().get().v(),
                domain.getBasicInformation().getNotes().isPresent() ? domain.getBasicInformation().getNotes().get().v() : null,
                domain.getBasicInformation().getClubRepresentativePosition().map(i->i.v()).orElse(null),
                domain.getBasicInformation().getClubRepresentativeName().map(i->i.v()).orElse(null),
                domain.getBasicInformation().getLinkingDepartment().isPresent() ? domain.getBasicInformation().getLinkingDepartment().get() : null,
                domain.getBasicInformation().getCorporateNumber().isPresent() ? new BigDecimal(domain.getBasicInformation().getCorporateNumber().get().v()) : null,
                domain.getSummaryTableInformation().getAccountingOfficeTelephoneNumber().map(i->i.v()).orElse(null),
                domain.getSummaryTableInformation().getAccountingOfficeName().map(i->i.v()).orElse(null),
                domain.getSummaryTableInformation().getSalaryPaymentMethodAndDueDate1().map(i->i.v()).orElse(null),
                domain.getSummaryTableInformation().getSalaryPaymentMethodAndDueDate2().map(i->i.v()).orElse(null),
                domain.getSummaryTableInformation().getSalaryPaymentMethodAndDueDate3().map(i->i.v()).orElse(null),
                domain.getSummaryTableInformation().getAccountManagerName().map(i->i.v()).orElse(null),
                domain.getSummaryTableInformation().getBusinessLine1().map(i->i.v()).orElse(null),
                domain.getSummaryTableInformation().getBusinessLine2().map(i->i.v()).orElse(null),
                domain.getSummaryTableInformation().getBusinessLine3().map(i->i.v()).orElse(null),
                domain.getSummaryTableInformation().getTaxOffice().map(i->i.v()).orElse(null),
                domain.getSummaryTableInformation().getVibrantLocationFinancialInstitutions().map(i->i.v()).orElse(null),
                domain.getSummaryTableInformation().getNameBankTransferInstitution().map(i->i.v()).orElse(null),
                domain.getSummaryTableInformation().getContactName().map(i->i.v()).orElse(null),
                domain.getSummaryTableInformation().getContactClass().map(i->i.v()).orElse(null),
                domain.getSummaryTableInformation().getContactPhoneNumber().map(i->i.v()).orElse(null));
    }

}
