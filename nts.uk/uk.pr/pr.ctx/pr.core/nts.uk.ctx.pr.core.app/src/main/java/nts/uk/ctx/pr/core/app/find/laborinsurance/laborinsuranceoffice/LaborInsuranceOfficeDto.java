package nts.uk.ctx.pr.core.app.find.laborinsurance.laborinsuranceoffice;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.LaborInsuranceOffice;

/**
* 労働保険事業所: DTO
*/
@AllArgsConstructor
@Value
public class LaborInsuranceOfficeDto
{
    
    /**
    * 会社ID
    */
    private String companyId;
    
    /**
    * コード
    */
    private String laborOfficeCode;

    /**
     * 名称
     */
    public String laborOfficeName;

    /**
     * メモ
     */
    public String notes;

    /**
     * 代表者職位
     */
    public String representativePosition;

    /**
     * 労働保険事業所代表者名
     */
    public String representativeName;

    /**
     * 住所１
     */
    public String address1;

    /**
     * 住所２
     */
    public String address2;

    /**
     * 住所カナ１
     */
    public String addressKana1;

    /**
     * 住所カナ２
     */
    public String addressKana2;

    /**
     * 電話番号
     */
    public String phoneNumber;

    /**
     * 郵便番号
     */
    public String postalCode;

    /**
     * 事業所記号
     */
    public String employmentOfficeCode;

    /**
     * 事業所番号1
     */
    public String employmentOfficeNumber1;

    /**
     * 事業所番号2
     */
    public String employmentOfficeNumber2;

    /**
     * 事業所番号3
     */
    public String employmentOfficeNumber3;

    /**
     * 都市区符号
     */
    public String cityCode;

    public static LaborInsuranceOfficeDto fromDomainToDto (LaborInsuranceOffice domain) {
        return new LaborInsuranceOfficeDto(domain.getCompanyId(), domain.getLaborOfficeCode().v(), domain.getLaborOfficeName().v(), domain.getBasicInformation().getNotes().map(i -> i.v()).orElse(null), domain.getBasicInformation().getRepresentativePosition().v(), domain.getBasicInformation().getRepresentativeName().map(i -> i.v()).orElse(null), domain.getBasicInformation().getStreetAddress().getAddress1().map(i -> i.v()).orElse(null), domain.getBasicInformation().getStreetAddress().getAddress2().map(i -> i.v()).orElse(null), domain.getBasicInformation().getStreetAddress().getAddressKana1().map(i -> i.v()).orElse(null), domain.getBasicInformation().getStreetAddress().getAddressKana2().map(i -> i.v()).orElse(null), domain.getBasicInformation().getStreetAddress().getPhoneNumber().map(i -> i.v()).orElse(null), domain.getBasicInformation().getStreetAddress().getPostalCode().map(i -> i.v()).orElse(null), domain.getEmploymentInsuranceInfomation().getOfficeCode().map(i -> i.v()).orElse(null), domain.getEmploymentInsuranceInfomation().getOfficeNumber1().map(i -> i.v()).orElse(null), domain.getEmploymentInsuranceInfomation().getOfficeNumber2().map(i -> i.v()).orElse(null), domain.getEmploymentInsuranceInfomation().getOfficeNumber3().map(i -> i.v()).orElse(null), domain.getEmploymentInsuranceInfomation().getCityCode().map(i -> i.v()).orElse(null));
    }
}
