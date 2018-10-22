package nts.uk.ctx.core.dom.printdata;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 法定調書用会社
*/
@Getter
public class CompanyStatutoryWrite extends AggregateRoot {
    
    /**
    * 会社ID
    */
    private String cID;
    
    /**
    * コード
    */
    private Code code;
    
    /**
    * 名称
    */
    private Name name;
    
    /**
    * 基本情報
    */
    private BasicInformation basicInformation;
    
    /**
    * 総括表情報
    */
    private SummaryTableInformation summaryTableInformation;
    
    public CompanyStatutoryWrite(String cid, String code, String name, String kanaName, String address1, String address2, String addressKana1, String addressKana2, String phoneNumber, String postalCode, String note, String representativePosition, String representativeName, String linkedDepartment, Integer corporateNumber, String acountingOffTeleNumber, String accountingOffName, String salaPayMethodDueDate1, String salaPayMethodDueDate2, String salaPayMethodDueDate3, String accountManaName, String businessLine1, String businessLine2, String businessLine3, String taxOffice, String vibLocaFinIunstitution, String nameBankTranInstitustion, String contactName, String contactClass, String contactPhoneNumber) {
        this.cID = cid;
        this.code = new Code(code);
        this.name = new Name(name);
        this.basicInformation = new BasicInformation(kanaName, note, representativePosition, representativeName, linkedDepartment, corporateNumber,
                new AddressInformation(address1,address2,addressKana1,addressKana2,phoneNumber,postalCode));
        this.summaryTableInformation = new SummaryTableInformation(acountingOffTeleNumber, accountingOffName, salaPayMethodDueDate1, salaPayMethodDueDate2, salaPayMethodDueDate3, accountManaName, businessLine1, businessLine2, businessLine3, taxOffice, vibLocaFinIunstitution, nameBankTranInstitustion, contactName, contactClass, contactPhoneNumber);
    }
    
}
