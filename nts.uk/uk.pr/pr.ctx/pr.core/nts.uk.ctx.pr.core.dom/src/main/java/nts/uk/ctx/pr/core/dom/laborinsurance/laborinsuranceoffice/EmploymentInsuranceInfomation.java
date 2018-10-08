package nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice;

import java.util.Optional;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.enums.EnumAdaptor;

/**
* 雇用保険情報
*/
@AllArgsConstructor
@Getter
public class EmploymentInsuranceInfomation extends DomainObject
{
    
    /**
    * 事業所記号
    */
    private Optional<OfficeCode> officeCode;
    
    /**
    * 事業所番号1
    */
    private Optional<EmploymentInsuranceBusinessOfficeNumber1> officeNumber1;
    
    /**
    * 事業所番号2
    */
    private Optional<EmploymentInsuranceBusinessOfficeNumber2> officeNumber2;
    
    /**
    * 事業所番号3
    */
    private Optional<EmploymentInsuranceBusinessOfficeNumber3> officeNumber3;
    
    /**
    * 都市区符号
    */
    private Optional<CityCode> cityCode;
    
    public EmploymentInsuranceInfomation(String employmentOffficeCode, String employmentOfficeNumber1, String employmentOfficeNumber2, String employmentOfficeNumber3, String cityCode) {
        this.officeNumber1 = employmentOfficeNumber1 == null ? Optional.empty() : Optional.of(new EmploymentInsuranceBusinessOfficeNumber1(employmentOfficeNumber1));
        this.officeNumber2 = employmentOfficeNumber2 == null ? Optional.empty() : Optional.of(new EmploymentInsuranceBusinessOfficeNumber2(employmentOfficeNumber2));
        this.officeNumber3 = employmentOfficeNumber3 == null ? Optional.empty() : Optional.of(new EmploymentInsuranceBusinessOfficeNumber3(employmentOfficeNumber3));
        this.cityCode = cityCode == null ? Optional.empty() : Optional.of(new CityCode(cityCode));
        this.officeCode = officeCode == null ? Optional.empty() : Optional.of(new OfficeCode(employmentOffficeCode));
    }
    
}
