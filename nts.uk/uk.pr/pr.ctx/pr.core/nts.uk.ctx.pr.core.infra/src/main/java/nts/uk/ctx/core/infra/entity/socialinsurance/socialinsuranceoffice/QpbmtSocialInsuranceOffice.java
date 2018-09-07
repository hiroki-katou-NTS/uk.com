package nts.uk.ctx.core.infra.entity.socialinsurance.socialinsuranceoffice;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceBusinessAddress;
import nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOffice;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 社会保険事業所
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_SOCIAL_INS_OFFICE")
public class QpbmtSocialInsuranceOffice extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtSocialInsuranceOfficePk socialInsuranceOfficePk;

    /**
     * 名称
     */
    @Basic(optional = false)
    @Column(name = "NAME")
    public String name;

    /**
     * 代表者職位
     */
    @Basic(optional = false)
    @Column(name = "REPRESENTATIVE_POSITION")
    public String representativePosition;

    /**
     * メモ
     */
    @Basic(optional = true)
    @Column(name = "MEMO")
    public String memo;

    /**
     * 代表者名
     */
    @Basic(optional = true)
    @Column(name = "REPRESENTATIVE_NAME")
    public String representativeName;

    /**
     * 略名
     */
    @Basic(optional = true)
    @Column(name = "SHORT_NAME")
    public String shortName;

    /**
     * 住所1
     */
    @Basic(optional = true)
    @Column(name = "ADDRESS_1")
    public String address1;

    /**
     * 住所2
     */
    @Basic(optional = true)
    @Column(name = "ADDRESS_2")
    public String address2;

    /**
     * 住所カナ1
     */
    @Basic(optional = true)
    @Column(name = "ADDRESS_KANA_1")
    public String addressKana1;

    /**
     * 住所カナ2
     */
    @Basic(optional = true)
    @Column(name = "ADDRESS_KANA_2")
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
     * 健康保険事業所整理番号1
     */
    @Basic(optional = true)
    @Column(name = "HEALTH_INSURANCE_OFFICE_NUMBER_1")
    public String healthInsuranceOfficeNumber1;

    /**
     * 健康保険事業所整理番号2
     */
    @Basic(optional = true)
    @Column(name = "HEALTH_INSURANCE_OFFICE_NUMBER_2")
    public String healthInsuranceOfficeNumber2;

    /**
     * 厚生年金事業所整理番号1
     */
    @Basic(optional = true)
    @Column(name = "WELFARE_PENSION_OFFICE_NUMBER_1")
    public String welfarePensionOfficeNumber1;

    /**
     * 厚生年金事業所整理番号2
     */
    @Basic(optional = true)
    @Column(name = "WELFARE_PENSION_OFFICE_NUMBER_2")
    public String welfarePensionOfficeNumber2;

    /**
     * 健康保険事業所記号
     */
    @Basic(optional = true)
    @Column(name = "HEALTH_INSURANCE_OFFICE_CODE")
    public String healthInsuranceOfficeCode;

    /**
     * 健康保険都市区符号
     */
    @Basic(optional = true)
    @Column(name = "HEALTH_INSURANCE_CITY_CODE")
    public String healthInsuranceCityCode;

    /**
     * 健康保険都道府県No
     */
    @Basic(optional = true)
    @Column(name = "HEALTH_INSURANCE_PREFECTURE_NO")
    public Integer healthInsurancePrefectureNo;

    /**
     * 厚生年金事業所記号
     */
    @Basic(optional = true)
    @Column(name = "WELFARE_PENSION_OFFICE_CODE")
    public String welfarePensionOfficeCode;

    /**
     * 厚生年金都市区符号
     */
    @Basic(optional = true)
    @Column(name = "WELFARE_PENSION_CITY_CODE")
    public String welfarePensionCityCode;

    /**
     * 厚生年金保険都道府県No
     */
    @Basic(optional = true)
    @Column(name = "WELFARE_PENSION_PREFECTURE_NO")
    public Integer welfarePensionPrefectureNo;

    /**
     * 健康保険事業所番号
     */
    @Basic(optional = true)
    @Column(name = "HEALTH_INSURANCE_OFFICE_NUMBER")
    public Integer healthInsuranceOfficeNumber;

    /**
     * 健保組合事務所番号
     */
    @Basic(optional = true)
    @Column(name = "HEALTH_INSURANCE_UNION_OFFICE_NUMBER")
    public String healthInsuranceUnionOfficeNumber;

    /**
     * 厚生年金基金番号
     */
    @Basic(optional = true)
    @Column(name = "WELFARE_PENSION_FUND_NUMBER")
    public Integer welfarePensionFundNumber;

    /**
     * 厚生年金事業所番号
     */
    @Basic(optional = true)
    @Column(name = "WELFARE_PENSION_OFFICE_NUMBER")
    public String welfarePensionOfficeNumber;

    @Override
    protected Object getKey() {
        return socialInsuranceOfficePk;
    }

    /**
     * Entity to domain
     *
     * @param entity QpbmtSocialInsuranceOffice
     * @return SocialInsuranceOffice
     */
    public SocialInsuranceOffice toDomain(QpbmtSocialInsuranceOffice entity) {
        return new SocialInsuranceOffice(entity.socialInsuranceOfficePk.cid, entity.socialInsuranceOfficePk.code, entity.name,
                entity.shortName, entity.representativeName, entity.representativePosition, entity.postalCode, entity.address1, entity.addressKana1, entity.address2, entity.addressKana2, entity.phoneNumber, entity.memo,
                entity.welfarePensionFundNumber, entity.welfarePensionOfficeNumber, entity.healthInsuranceOfficeNumber, entity.healthInsuranceUnionOfficeNumber,
                entity.healthInsuranceOfficeNumber1, entity.healthInsuranceOfficeNumber2, entity.welfarePensionOfficeNumber1, entity.welfarePensionOfficeNumber2,
                entity.healthInsuranceCityCode, entity.healthInsuranceOfficeCode, entity.welfarePensionCityCode, entity.welfarePensionOfficeCode, entity.healthInsurancePrefectureNo, entity.welfarePensionPrefectureNo);
    }

    /**
     * Domain to Entity
     *
     * @param domain SocialInsuranceOffice
     * @return QpbmtSocialInsuranceOffice
     */
    public static QpbmtSocialInsuranceOffice toEntity(SocialInsuranceOffice domain) {
        return new QpbmtSocialInsuranceOffice(domain);
    }

    private QpbmtSocialInsuranceOffice(SocialInsuranceOffice domain) {
        this.socialInsuranceOfficePk.cid      = domain.getCompanyID();
        this.socialInsuranceOfficePk.code     = domain.getCode().v();
        this.name                             = domain.getName().v();
        this.representativePosition           = domain.getBasicInformation().getRepresentativePosition().v();
        this.memo                             = domain.getBasicInformation().getMemo().map(PrimitiveValueBase::v).orElse(null);
        this.representativeName               = domain.getBasicInformation().getRepresentativeName().map(PrimitiveValueBase::v).orElse(null);
        this.shortName                        = domain.getBasicInformation().getShortName().map(PrimitiveValueBase::v).orElse(null);
        this.postalCode                       = domain.getBasicInformation().getAddress().map(SocialInsuranceBusinessAddress::getPostalCode).map(postalCode -> postalCode.orElse(null)).map(PrimitiveValueBase::v).orElse(null);
        this.address1                         = domain.getBasicInformation().getAddress().map(SocialInsuranceBusinessAddress::getAddress1).map(address1 -> address1.orElse(null)).map(PrimitiveValueBase::v).orElse(null);
        this.address2                         = domain.getBasicInformation().getAddress().map(SocialInsuranceBusinessAddress::getAddress2).map(address2 -> address2.orElse(null)).map(PrimitiveValueBase::v).orElse(null);
        this.addressKana1                     = domain.getBasicInformation().getAddress().map(SocialInsuranceBusinessAddress::getAddressKana1).map(addressKana1 -> addressKana1.orElse(null)).map(PrimitiveValueBase::v).orElse(null);
        this.addressKana2                     = domain.getBasicInformation().getAddress().map(SocialInsuranceBusinessAddress::getAddressKana2).map(addressKana2 -> addressKana2.orElse(null)).map(PrimitiveValueBase::v).orElse(null);
        this.phoneNumber                      = domain.getBasicInformation().getAddress().map(SocialInsuranceBusinessAddress::getPhoneNumber).map(phoneNumber -> phoneNumber.orElse(null)).map(PrimitiveValueBase::v).orElse(null);
        this.welfarePensionFundNumber         = domain.getInsuranceMasterInformation().getWelfarePensionFundNumber().map(PrimitiveValueBase::v).orElse(null);
        this.welfarePensionOfficeNumber       = domain.getInsuranceMasterInformation().getWelfarePensionOfficeNumber().map(PrimitiveValueBase::v).orElse(null);
        this.healthInsuranceOfficeNumber      = domain.getInsuranceMasterInformation().getHealthInsuranceOfficeNumber().map(PrimitiveValueBase::v).orElse(null);
        this.healthInsuranceUnionOfficeNumber = domain.getInsuranceMasterInformation().getHealthInsuranceUnionOfficeNumber().map(PrimitiveValueBase::v).orElse(null);
        this.healthInsuranceOfficeNumber1     = domain.getInsuranceMasterInformation().getOfficeOrganizeNumber().getHealthInsuranceOfficeNumber1().map(PrimitiveValueBase::v).orElse(null);
        this.healthInsuranceOfficeNumber2     = domain.getInsuranceMasterInformation().getOfficeOrganizeNumber().getHealthInsuranceOfficeNumber2().map(PrimitiveValueBase::v).orElse(null);
        this.welfarePensionOfficeNumber1      = domain.getInsuranceMasterInformation().getOfficeOrganizeNumber().getWelfarePensionOfficeNumber1().map(PrimitiveValueBase::v).orElse(null);
        this.welfarePensionOfficeNumber2      = domain.getInsuranceMasterInformation().getOfficeOrganizeNumber().getWelfarePensionOfficeNumber2().map(PrimitiveValueBase::v).orElse(null);
        this.healthInsuranceCityCode          = domain.getInsuranceMasterInformation().getForMagneticMedia().getHealthInsuranceCityCode().map(PrimitiveValueBase::v).orElse(null);
        this.healthInsuranceOfficeCode        = domain.getInsuranceMasterInformation().getForMagneticMedia().getHealthInsuranceCityCode().map(PrimitiveValueBase::v).orElse(null);
        this.welfarePensionCityCode           = domain.getInsuranceMasterInformation().getForMagneticMedia().getWelfarePensionCityCode().map(PrimitiveValueBase::v).orElse(null);
        this.welfarePensionOfficeCode         = domain.getInsuranceMasterInformation().getForMagneticMedia().getWelfarePensionOfficeCode().map(PrimitiveValueBase::v).orElse(null);
        this.healthInsurancePrefectureNo      = domain.getInsuranceMasterInformation().getForMagneticMedia().getHealthInsurancePrefectureNo().orElse(null);
        this.welfarePensionPrefectureNo       = domain.getInsuranceMasterInformation().getForMagneticMedia().getWelfarePensionPrefectureNo().orElse(null);
    }
}
