package nts.uk.ctx.core.app.command.printdata.companystatuwrite.finder;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.core.dom.printdata.BasicInformation;
import nts.uk.ctx.core.dom.printdata.CompanyStatutoryWrite;

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
    
    
    public static CompanyStatutoryWriteDto fromDomain(CompanyStatutoryWrite domain)
    {
        return new CompanyStatutoryWriteDto(domain.getCID(), domain.getCode().v(), domain.getName().v(),
                domain.getBasicInformation().getKanaName().v(),
                domain.getBasicInformation().getAddressInformation().getAddress1().map(i->i.v()).orElse(null),
                domain.getBasicInformation().getAddressInformation().getAddress2().map(i->i.v()).orElse(null),
                domain.getBasicInformation().getAddressInformation().getAddressKana1().map(i->i.v()).orElse(null),
                domain.getBasicInformation().getAddressInformation().getAddressKana2().map(i->i.v()).orElse(null),
                domain.getBasicInformation().getAddressInformation().getPhoneNumber().map(i->i.v()).orElse(null),
                domain.getBasicInformation().getAddressInformation().getPostalCode().map(i->i.v()).orElse(null),
                domain.getBasicInformation().getNotes().map(i->i.v()).orElse(null),
                domain.getBasicInformation().getClubRepresentativePosition().map(i->i.v()).orElse(null),
                domain.getBasicInformation().getClubRepresentativeName().map(i->i.v()).orElse(null),
                domain.getBasicInformation().getLinkingDepartment().isPresent() ? domain.getBasicInformation().getLinkingDepartment().get() : null,
                domain.getBasicInformation().getCorporateNumber().map(i->i.v()).orElse(null),
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
