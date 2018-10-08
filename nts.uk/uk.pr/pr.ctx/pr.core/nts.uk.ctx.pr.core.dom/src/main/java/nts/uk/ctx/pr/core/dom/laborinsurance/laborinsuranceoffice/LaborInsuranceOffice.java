package nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice;

import java.util.Optional;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.enums.EnumAdaptor;

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
    private LaborInsuranceOfficeCode officeCode;
    
    /**
    * 名称
    */
    private LaborInsuranceOfficeName officeName;
    
    /**
    * 基本情報
    */
    private BasicInfomation basicInfomation;
    
    /**
    * 雇用保険情報
    */
    private EmploymentInsuranceInfomation employmentInsuranceInfomation;
    
    public LaborInsuranceOffice(String cid, String officeCode, String officeName, String notes, String representativePosition, String representativeName, String address1, String address2, String addressKana1, String addressKana2, String phoneNumber, String postalCode, String employmentOffficeCode, String employmentOfficeNumber1, String employmentOfficeNumber2, String employmentOfficeNumber3, String cityCode) {
        this.companyId = cid;
        this.officeCode = new LaborInsuranceOfficeCode(officeCode);
        this.officeName = new LaborInsuranceOfficeName(officeName);
        this.basicInfomation = new BasicInfomation(notes, representativePosition, representativeName, phoneNumber, postalCode, address1, addressKana1, address2, addressKana2);
        this.employmentInsuranceInfomation = new EmploymentInsuranceInfomation(employmentOffficeCode, employmentOfficeNumber1, employmentOfficeNumber2, employmentOfficeNumber3, cityCode);
    }
    
}
