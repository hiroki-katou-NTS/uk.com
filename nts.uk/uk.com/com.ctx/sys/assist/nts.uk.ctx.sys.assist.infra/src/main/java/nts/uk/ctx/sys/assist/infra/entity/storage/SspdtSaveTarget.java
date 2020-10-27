package nts.uk.ctx.sys.assist.infra.entity.storage;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.assist.dom.storage.BusinessName;
import nts.uk.ctx.sys.assist.dom.storage.EmployeeCode;
import nts.uk.ctx.sys.assist.dom.storage.TargetEmployees;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
* データ保存の対象社員
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SSPDT_SAVE_TARGET")
public class SspdtSaveTarget extends ContractUkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public SspdtSaveTargetPk targetEmployeesPk;
    
    /**
    * ビジネスネーム
    */
    @Basic(optional = false)
    @Column(name = "BUSINESS_NAME")
    public String businessname;
    
    /**
     * ビジネスネーム
     */
     @Basic(optional = false)
     @Column(name = "SCD")
     public String scd;
    
    @Override
    protected Object getKey()
    {
        return targetEmployeesPk;
    }

    public TargetEmployees toDomain() {
        return new TargetEmployees(targetEmployeesPk.storeProcessingId, targetEmployeesPk.Sid, new BusinessName(businessname), new EmployeeCode(scd));
    }
    
    public static SspdtSaveTarget toEntity(TargetEmployees domain) {
        return new SspdtSaveTarget(new SspdtSaveTargetPk(domain.getStoreProcessingId(), domain.getSid()), domain.getBusinessname().v(), domain.getScd().v());
    }

}
