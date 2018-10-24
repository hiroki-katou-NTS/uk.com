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
        return new QpbmtSocialInsuranceOffice(new QpbmtSocialInsuranceOfficePk(domain.getCompanyID(), domain.getCode().v()),
                domain.getName().v(),
                domain.getBasicInformation().getRepresentativePosition().v(),
                domain.getBasicInformation().getMemo().map(PrimitiveValueBase::v).orElse(null),
                domain.getBasicInformation().getRepresentativeName().map(PrimitiveValueBase::v).orElse(null),
                domain.getBasicInformation().getShortName().map(PrimitiveValueBase::v).orElse(null),
                domain.getBasicInformation().getAddress().map(SocialInsuranceBusinessAddress::getAddress1).map(x -> x.orElse(null)).map(PrimitiveValueBase::v).orElse(null),
                domain.getBasicInformation().getAddress().map(SocialInsuranceBusinessAddress::getAddress2).map(x -> x.orElse(null)).map(PrimitiveValueBase::v).orElse(null),
                domain.getBasicInformation().getAddress().map(SocialInsuranceBusinessAddress::getAddressKana1).map(x -> x.orElse(null)).map(PrimitiveValueBase::v).orElse(null),
                domain.getBasicInformation().getAddress().map(SocialInsuranceBusinessAddress::getAddressKana2).map(x -> x.orElse(null)).map(PrimitiveValueBase::v).orElse(null),
                domain.getBasicInformation().getAddress().map(SocialInsuranceBusinessAddress::getPhoneNumber).map(x -> x.orElse(null)).map(PrimitiveValueBase::v).orElse(null),
                domain.getBasicInformation().getAddress().map(SocialInsuranceBusinessAddress::getPostalCode).map(x -> x.orElse(null)).map(PrimitiveValueBase::v).orElse(null),
                domain.getInsuranceMasterInformation().getOfficeOrganizeNumber().getHealthInsuranceOfficeNumber1().map(PrimitiveValueBase::v).orElse(null),
                domain.getInsuranceMasterInformation().getOfficeOrganizeNumber().getHealthInsuranceOfficeNumber2().map(PrimitiveValueBase::v).orElse(null),
                domain.getInsuranceMasterInformation().getOfficeOrganizeNumber().getWelfarePensionOfficeNumber1().map(PrimitiveValueBase::v).orElse(null),
                domain.getInsuranceMasterInformation().getOfficeOrganizeNumber().getWelfarePensionOfficeNumber2().map(PrimitiveValueBase::v).orElse(null),
                domain.getInsuranceMasterInformation().getForMagneticMedia().getHealthInsuranceOfficeCode().map(PrimitiveValueBase::v).orElse(null),
                domain.getInsuranceMasterInformation().getForMagneticMedia().getHealthInsuranceCityCode().map(PrimitiveValueBase::v).orElse(null),
                domain.getInsuranceMasterInformation().getForMagneticMedia().getHealthInsurancePrefectureNo().orElse(null),
                domain.getInsuranceMasterInformation().getForMagneticMedia().getWelfarePensionOfficeCode().map(PrimitiveValueBase::v).orElse(null),
                domain.getInsuranceMasterInformation().getForMagneticMedia().getWelfarePensionCityCode().map(PrimitiveValueBase::v).orElse(null),
                domain.getInsuranceMasterInformation().getForMagneticMedia().getWelfarePensionPrefectureNo().orElse(null),
                domain.getInsuranceMasterInformation().getHealthInsuranceOfficeNumber().map(PrimitiveValueBase::v).map(Integer::new).orElse(null),
                domain.getInsuranceMasterInformation().getHealthInsuranceUnionOfficeNumber().map(PrimitiveValueBase::v).orElse(null),
                domain.getInsuranceMasterInformation().getWelfarePensionFundNumber().map(PrimitiveValueBase::v).map(Integer::new).orElse(null),
                domain.getInsuranceMasterInformation().getWelfarePensionOfficeNumber().map(PrimitiveValueBase::v).orElse(null)
        );
    }
}
