package nts.uk.ctx.pr.core.app.command.laborinsurance.laborinsuranceoffice;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.LaborInsuranceOffice;

@Value
public class LaborInsuranceOfficeCommand
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
    
    public LaborInsuranceOffice fromCommandToDomain () {
        return new LaborInsuranceOffice(this.companyId, this.laborOfficeCode, this.laborOfficeName, this.notes, this.representativePosition, this.representativeName, this.address1, this.address2, this.addressKana1, this.addressKana2, this.phoneNumber, this.postalCode, this.employmentOfficeCode, this.employmentOfficeNumber1, this.employmentOfficeNumber2, this.employmentOfficeNumber3, this.cityCode);
    }

}
