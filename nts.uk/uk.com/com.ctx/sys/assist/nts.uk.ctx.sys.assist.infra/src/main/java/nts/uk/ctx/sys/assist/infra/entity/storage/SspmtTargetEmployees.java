package nts.uk.ctx.sys.assist.infra.entity.storage;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* データ保存の対象社員
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SSPMT_TARGET_EMPLOYEES")
public class SspmtTargetEmployees extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public SspmtTargetEmployeesPk targetEmployeesPk;
    
    /**
    * ビジネスネーム
    */
    @Basic(optional = false)
    @Column(name = "BusinessName")
    public String businessname;
    
    @Override
    protected Object getKey()
    {
        return targetEmployeesPk;
    }

    public TargetEmployees toDomain() {
        return new TargetEmployees(this.targetEmployeesPk.storeProcessingId, this.targetEmployeesPk.employeeId, this.businessname);
    }
    public static SspmtTargetEmployees toEntity(TargetEmployees domain) {
        return new SspmtTargetEmployees(new SspmtTargetEmployeesPk(domain.getStoreProcessingId(), domain.getEmployeeId()), domain.getBusinessname());
    }

}
