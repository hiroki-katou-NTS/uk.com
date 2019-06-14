package nts.uk.ctx.pr.core.infra.entity.laborinsurance.laborinsuranceoffice;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.LaborInsuranceOffice;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 労働保険事業所
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_LABOR_INSU_OFFICE")
public class QpbmtLaborInsuOffice extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtLaborInsuOfficePk laborInsuOfficePk;

    /**
     * 名称
     */
    @Basic(optional = false)
    @Column(name = "LABOR_OFFICE_NAME")
    public String laborOfficeName;

    /**
     * メモ
     */
    @Basic(optional = true)
    @Column(name = "NOTES")
    public String notes;

    /**
     * 代表者職位
     */
    @Basic(optional = false)
    @Column(name = "REPRESENTATIVE_POSITION")
    public String representativePosition;

    /**
     * 労働保険事業所代表者名
     */
    @Basic(optional = false)
    @Column(name = "REPRESENTATIVE_NAME")
    public String representativeName;

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
     * 事業所記号
     */
    @Basic(optional = true)
    @Column(name = "EMPLOYMENT_OFFFICE_CODE")
    public String employmentOffficeCode;

    /**
     * 事業所番号1
     */
    @Basic(optional = true)
    @Column(name = "EMPLOYMENT_OFFICE_NUMBER1")
    public String employmentOfficeNumber1;

    /**
     * 事業所番号2
     */
    @Basic(optional = true)
    @Column(name = "EMPLOYMENT_OFFICE_NUMBER2")
    public String employmentOfficeNumber2;

    /**
     * 事業所番号3
     */
    @Basic(optional = true)
    @Column(name = "EMPLOYMENT_OFFICE_NUMBER3")
    public String employmentOfficeNumber3;

    /**
     * 都市区符号
     */
    @Basic(optional = true)
    @Column(name = "CITY_CODE")
    public String cityCode;

    @Override
    protected Object getKey() {
        return laborInsuOfficePk;
    }

    public LaborInsuranceOffice toDomain() {
        return new LaborInsuranceOffice(this.laborInsuOfficePk.cid, this.laborInsuOfficePk.laborOfficeCode, this.laborOfficeName, this.notes, this.representativePosition, this.representativeName, this.address1, this.address2, this.addressKana1, this.addressKana2, this.phoneNumber, this.postalCode, this.employmentOffficeCode, this.employmentOfficeNumber1, this.employmentOfficeNumber2, this.employmentOfficeNumber3, this.cityCode);
    }

    public static QpbmtLaborInsuOffice toEntity(LaborInsuranceOffice domain) {
        return new QpbmtLaborInsuOffice(new QpbmtLaborInsuOfficePk(domain.getCompanyId(), domain.getLaborOfficeCode().v()), domain.getLaborOfficeName().v(), domain.getBasicInformation().getNotes().map(i -> i.v()).orElse(null), domain.getBasicInformation().getRepresentativePosition().v(), domain.getBasicInformation().getRepresentativeName().map(i -> i.v()).orElse(null), domain.getBasicInformation().getStreetAddress().getAddress1().map(i -> i.v()).orElse(null), domain.getBasicInformation().getStreetAddress().getAddress2().map(i -> i.v()).orElse(null), domain.getBasicInformation().getStreetAddress().getAddressKana1().map(i -> i.v()).orElse(null), domain.getBasicInformation().getStreetAddress().getAddressKana2().map(i -> i.v()).orElse(null), domain.getBasicInformation().getStreetAddress().getPhoneNumber().map(i -> i.v()).orElse(null), domain.getBasicInformation().getStreetAddress().getPostalCode().map(i -> i.v()).orElse(null), domain.getEmploymentInsuranceInfomation().getOfficeCode().map(i -> i.v()).orElse(null), domain.getEmploymentInsuranceInfomation().getOfficeNumber1().map(i -> i.v()).orElse(null), domain.getEmploymentInsuranceInfomation().getOfficeNumber2().map(i -> i.v()).orElse(null), domain.getEmploymentInsuranceInfomation().getOfficeNumber3().map(i -> i.v()).orElse(null), domain.getEmploymentInsuranceInfomation().getCityCode().map(i -> i.v()).orElse(null));
    }

}
