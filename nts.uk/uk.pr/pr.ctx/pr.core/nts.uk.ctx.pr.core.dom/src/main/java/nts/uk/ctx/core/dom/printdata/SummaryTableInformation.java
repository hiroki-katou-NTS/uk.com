package nts.uk.ctx.core.dom.printdata;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
* 総括表情報
*/
@AllArgsConstructor
@Getter
public class SummaryTableInformation extends DomainObject
{
    
    /**
    * 会計事務所電話番号
    */
    private Optional<AccountingOffTelePhoneNumber> accountingOfficeTelephoneNumber;
    
    /**
    * 会計事務所名称
    */
    private Optional<AccountingOffName> accountingOfficeName;
    
    /**
    * 給与支払方法と期日1
    */
    private Optional<SalaPayMethodDueDate> salaryPaymentMethodAndDueDate1;
    
    /**
    * 給与支払方法と期日2
    */
    private Optional<SalaPayMethodDueDate> salaryPaymentMethodAndDueDate2;
    
    /**
    * 給与支払方法と期日3
    */
    private Optional<SalaPayMethodDueDate> salaryPaymentMethodAndDueDate3;
    
    /**
    * 経理責任者氏名
    */
    private Optional<AccountManaName> accountManagerName;
    
    /**
    * 事業種目1
    */
    private Optional<BusinessLine> businessLine1;
    
    /**
    * 事業種目2
    */
    private Optional<BusinessLine> businessLine2;
    
    /**
    * 事業種目3
    */
    private Optional<BusinessLine> businessLine3;
    
    /**
    * 所轄税務署
    */
    private Optional<TaxOffice> taxOffice;
    
    /**
    * 振込希望金融機関所在地
    */
    private Optional<VibLocaFinIns> vibrantLocationFinancialInstitutions;
    
    /**
    * 振込希望金融機関名称
    */
    private Optional<NameBankTranfeIns> nameBankTransferInstitution;
    
    /**
    * 連絡者氏名
    */
    private Optional<ContactName> contactName;
    
    /**
    * 連絡者所属課係
    */
    private Optional<ContactClass> contactClass;
    
    /**
    * 連絡者電話番号
    */
    private Optional<ContactPhoneNumber> contactPhoneNumber;
    
    public SummaryTableInformation(String acountingOffTeleNumber, String accountingOffName, String salaPayMethodDueDate1, String salaPayMethodDueDate2, String salaPayMethodDueDate3, String accountManaName, String businessLine1, String businessLine2, String businessLine3, String taxOffice, String vibLocaFinIunstitution, String nameBankTranInstitustion, String contactName, String contactClass, String contactPhoneNumber) {
        this.accountManagerName = accountManaName == null ? Optional.empty() : Optional.of(new AccountManaName(accountManaName));
        this.contactClass = contactClass == null ? Optional.empty() : Optional.of(new ContactClass(contactClass));
        this.contactName = contactName == null ? Optional.empty() : Optional.of(new ContactName(contactName));
        this.contactPhoneNumber = contactPhoneNumber == null ? Optional.empty() : Optional.of(new ContactPhoneNumber(contactPhoneNumber));
        this.accountingOfficeName = accountingOffName == null ? Optional.empty() : Optional.of(new AccountingOffName(accountingOffName));
        this.accountingOfficeTelephoneNumber = acountingOffTeleNumber == null ? Optional.empty() : Optional.of(new AccountingOffTelePhoneNumber(acountingOffTeleNumber));
        this.salaryPaymentMethodAndDueDate1 = salaPayMethodDueDate1 == null ? Optional.empty() : Optional.of(new SalaPayMethodDueDate(salaPayMethodDueDate1));
        this.salaryPaymentMethodAndDueDate2 = salaPayMethodDueDate2 == null ? Optional.empty() : Optional.of(new SalaPayMethodDueDate(salaPayMethodDueDate2));
        this.salaryPaymentMethodAndDueDate3 = salaPayMethodDueDate3 == null ? Optional.empty() : Optional.of(new SalaPayMethodDueDate(salaPayMethodDueDate3));
        this.businessLine1 = businessLine1 == null ? Optional.empty() : Optional.of(new BusinessLine(businessLine1));
        this.businessLine2 = businessLine2 == null ? Optional.empty() : Optional.of(new BusinessLine(businessLine2));
        this.businessLine3 = businessLine3 == null ? Optional.empty() : Optional.of(new BusinessLine(businessLine3));
        this.taxOffice = taxOffice == null ? Optional.empty() : Optional.of(new TaxOffice(taxOffice));
        this.nameBankTransferInstitution = nameBankTranInstitustion == null ? Optional.empty() : Optional.of(new NameBankTranfeIns(nameBankTranInstitustion));
        this.vibrantLocationFinancialInstitutions = vibLocaFinIunstitution == null ? Optional.empty() : Optional.of(new VibLocaFinIns(vibLocaFinIunstitution));
    }
    
}
