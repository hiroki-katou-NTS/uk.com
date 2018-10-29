package nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

import java.util.Objects;
import java.util.Optional;

/**
 * 基本情報
 */
@Getter
public class BasicInformation extends DomainObject {

    /**
     * 略名
     */
    private Optional<SocialInsuranceOfficeShortName> shortName;

    /**
     * 代表者名
     */
    private Optional<SocialInsuranceOfficeRepresentName> representativeName;

    /**
     * 代表者職位
     */
    private SocialInsuranceOfficeRepresentPosition representativePosition;

    /**
     * 住所
     */
    private Optional<SocialInsuranceBusinessAddress> address;

    /**
     * メモ
     */
    private Optional<Memo> memo;

    /**
     * 基本情報
     *
     * @param shortName              略名
     * @param representativeName     代表者名
     * @param representativePosition 代表者職位
     * @param address                住所
     * @param memo                   メモ
     */
    public BasicInformation(String shortName, String representativeName, String representativePosition, Optional<SocialInsuranceBusinessAddress> address, String memo) {
        this.shortName              = Objects.isNull(shortName) ? Optional.empty() : Optional.of(new SocialInsuranceOfficeShortName(shortName));
        this.representativeName     = Objects.isNull(representativeName) ? Optional.empty() : Optional.of(new SocialInsuranceOfficeRepresentName(representativeName));
        this.representativePosition = new SocialInsuranceOfficeRepresentPosition(representativePosition);
        this.address                = address;
        this.memo                   = Objects.isNull(memo) ? Optional.empty() : Optional.of(new Memo(memo));
    }
}