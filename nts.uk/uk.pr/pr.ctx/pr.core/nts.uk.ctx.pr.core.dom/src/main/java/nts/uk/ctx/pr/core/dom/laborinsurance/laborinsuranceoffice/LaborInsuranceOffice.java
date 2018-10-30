package nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.context.AppContexts;

/**
* 労働保険事業所
*/
@Getter
public class LaborInsuranceOffice extends AggregateRoot {
    
    /**
    * 会社ID
    */
    private String companyId;
    
    /**
    * コード
    */
    private LaborInsuranceOfficeCode laborOfficeCode;
    
    /**
    * 名称
    */
    private LaborInsuranceOfficeName laborOfficeName;
    
    /**
    * 基本情報
    */
    private BasicInformation basicInformation;
    
    /**
    * 雇用保険情報
    */
    private EmploymentInsuranceInfomation employmentInsuranceInfomation;
    
    public LaborInsuranceOffice(String cid, String laborOfficeCode, String laborOfficeName, String notes, String representativePosition, String representativeName, String address1, String address2, String addressKana1, String addressKana2, String phoneNumber, String postalCode, String employmentOfficeCode, String employmentOfficeNumber1, String employmentOfficeNumber2, String employmentOfficeNumber3, String cityCode) {
        this.companyId = AppContexts.user().companyId();
        this.laborOfficeCode = new LaborInsuranceOfficeCode(laborOfficeCode);
        this.laborOfficeName = new LaborInsuranceOfficeName(laborOfficeName);
        this.basicInformation = new BasicInformation(notes, representativePosition, representativeName, phoneNumber, postalCode, address1, addressKana1, address2, addressKana2);
        this.employmentInsuranceInfomation = new EmploymentInsuranceInfomation(employmentOfficeCode, employmentOfficeNumber1, employmentOfficeNumber2, employmentOfficeNumber3, cityCode);
    }
    
}
