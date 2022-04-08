package nts.uk.ctx.at.record.infra.entity.supportmanagement.supportalloworg;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportalloworg.SupportAllowOrganization;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.shr.infra.data.entity.ContractCompanyUkJpaEntity;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 応援許可する組織
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "KSHMT_SUPPORT_PERMITTED_ORG")
public class KshmtSupportPermittedOrg extends ContractUkJpaEntity implements Serializable {
    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    public KshmtSupportPermittedOrgPk permittedOrgPk;
    @Column(name = "CID")
    public String companyId;
    @Override
    public Object getKey() {
        return permittedOrgPk;
    }
    public  static KshmtSupportPermittedOrg  toEntity(SupportAllowOrganization domain,String companyId){
        return new KshmtSupportPermittedOrg(
                new KshmtSupportPermittedOrgPk(
                        domain.getTargetOrg().getUnit().value,
                        domain.getTargetOrg().getUnit().value == TargetOrganizationUnit.WORKPLACE.value ?
                                domain.getTargetOrg().getWorkplaceId().orElse(null) :
                                domain.getTargetOrg().getWorkplaceGroupId().orElse(null),
                        domain.getSupportableOrganization().getUnit().value,
                        domain.getSupportableOrganization().getUnit().value == TargetOrganizationUnit.WORKPLACE.value ?
                                domain.getSupportableOrganization().getWorkplaceId().orElse(null) :
                                domain.getSupportableOrganization().getWorkplaceGroupId().orElse(null)
                ),
                companyId
        );
    }

}
