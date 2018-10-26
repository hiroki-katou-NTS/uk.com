package nts.uk.ctx.core.app.find.printdata.companystatuwrite;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.core.dom.printdata.CompanyStatutoryWrite;

import java.math.BigDecimal;

/**
* 法定調書用会社: DTO
*/
@AllArgsConstructor
@Value
public class CompanyStatutoryWriteDto
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
    private BigDecimal corporateNumber;
    
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
    
    
    public static CompanyStatutoryWriteDto fromDomain(CompanyStatutoryWrite domain)
    {
        return new CompanyStatutoryWriteDto(domain.getCID(), domain.getCode().v(), domain.getName().v(),
                domain.getBasicInformation().getKanaName().v(),
                domain.getBasicInformation().getAddressInformation().getAddress1() == null ? null : domain.getBasicInformation().getAddressInformation().getAddress1().get().v(),
                domain.getBasicInformation().getAddressInformation().getAddress2() == null ? null : domain.getBasicInformation().getAddressInformation().getAddress2().get().v() ,
                domain.getBasicInformation().getAddressInformation().getAddressKana1() == null ? null : domain.getBasicInformation().getAddressInformation().getAddressKana1().get().v(),
                domain.getBasicInformation().getAddressInformation().getAddressKana2() == null ? null : domain.getBasicInformation().getAddressInformation().getAddressKana2().get().v(),
                domain.getBasicInformation().getAddressInformation().getPhoneNumber() == null ? null : domain.getBasicInformation().getAddressInformation().getPhoneNumber().get().v(),
                domain.getBasicInformation().getAddressInformation().getPostalCode() == null ? null : domain.getBasicInformation().getAddressInformation().getPostalCode().get().v(),
                domain.getBasicInformation().getNotes().isPresent() ? domain.getBasicInformation().getNotes().get().v() :null,
                domain.getBasicInformation().getClubRepresentativePosition().isPresent()? domain.getBasicInformation().getClubRepresentativePosition().get().v() : null,
                domain.getBasicInformation().getClubRepresentativeName().isPresent() ? domain.getBasicInformation().getClubRepresentativeName().get().v() : null,
                domain.getBasicInformation().getLinkingDepartment().isPresent() ? domain.getBasicInformation().getLinkingDepartment().get() : null,
                domain.getBasicInformation().getCorporateNumber().isPresent() ? new BigDecimal(domain.getBasicInformation().getCorporateNumber().get().v()) : null,
                domain.getSummaryTableInformation().getAccountingOfficeTelephoneNumber().isPresent() ? domain.getSummaryTableInformation().getAccountingOfficeTelephoneNumber().get().v() : null,
                domain.getSummaryTableInformation().getAccountingOfficeName().isPresent() ? domain.getSummaryTableInformation().getAccountingOfficeName().get().v() : null,
                domain.getSummaryTableInformation().getSalaryPaymentMethodAndDueDate1().isPresent() ? domain.getSummaryTableInformation().getSalaryPaymentMethodAndDueDate1().get().v() : null,
                domain.getSummaryTableInformation().getSalaryPaymentMethodAndDueDate2().isPresent() ? domain.getSummaryTableInformation().getSalaryPaymentMethodAndDueDate2().get().v() : null,
                domain.getSummaryTableInformation().getSalaryPaymentMethodAndDueDate3().isPresent() ? domain.getSummaryTableInformation().getSalaryPaymentMethodAndDueDate3().get().v() : null,
                domain.getSummaryTableInformation().getAccountManagerName().isPresent() ? domain.getSummaryTableInformation().getAccountManagerName().get().v() : null,
                domain.getSummaryTableInformation().getBusinessLine1().isPresent() ? domain.getSummaryTableInformation().getBusinessLine1().get().v() : null,
                domain.getSummaryTableInformation().getBusinessLine2().isPresent() ? domain.getSummaryTableInformation().getBusinessLine2().get().v() : null,
                domain.getSummaryTableInformation().getBusinessLine3().isPresent() ? domain.getSummaryTableInformation().getBusinessLine3().get().v() : null,
                domain.getSummaryTableInformation().getTaxOffice().isPresent() ? domain.getSummaryTableInformation().getTaxOffice().get().v() : null,
                domain.getSummaryTableInformation().getVibrantLocationFinancialInstitutions().isPresent() ? domain.getSummaryTableInformation().getVibrantLocationFinancialInstitutions().get().v() : null,
                domain.getSummaryTableInformation().getNameBankTransferInstitution().isPresent() ? domain.getSummaryTableInformation().getNameBankTransferInstitution().get().v() : null,
                domain.getSummaryTableInformation().getContactName().isPresent() ? domain.getSummaryTableInformation().getContactName().get().v() : null,
                domain.getSummaryTableInformation().getContactClass().isPresent() ? domain.getSummaryTableInformation().getContactClass().get().v() : null,
                domain.getSummaryTableInformation().getContactPhoneNumber().isPresent() ? domain.getSummaryTableInformation().getContactPhoneNumber().get().v() : null);
    }
    
}
