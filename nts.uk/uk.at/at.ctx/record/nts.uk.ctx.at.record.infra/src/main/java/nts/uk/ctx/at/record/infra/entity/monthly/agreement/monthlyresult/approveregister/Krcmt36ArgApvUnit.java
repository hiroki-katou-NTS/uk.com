package nts.uk.ctx.at.record.infra.entity.monthly.agreement.monthlyresult.approveregister;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.daily.dailyperformance.classification.DoWork;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.approveregister.UnitOfApprover;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author quang.nh1
 */
@Entity
@Table(name = "KRCMT_36AGR_APV_UNIT")
@AllArgsConstructor
@NoArgsConstructor
public class Krcmt36ArgApvUnit  extends UkJpaEntity implements Serializable {

    @Id
    @Column(name = "CID")
    public String companyID;

    @Column(name = "CONTRACT_CD")
    public String contractCd;

    @Column(name = "WKP_USE_ATR")
    public Boolean useWorkplace;

    @Override
    protected Object getKey() {
        return this.companyID;
    }

    public static Krcmt36ArgApvUnit fromDomain(UnitOfApprover domain){
        return new Krcmt36ArgApvUnit(domain.getCompanyID(),
                AppContexts.user().contractCode(),
                domain.getUseWorkplace() != DoWork.NOTUSE);
    }

    public static UnitOfApprover toDomain(Krcmt36ArgApvUnit entity){
        return new UnitOfApprover(entity.companyID, EnumAdaptor.valueOf(!entity.useWorkplace ? 0 : 1, DoWork.class));
    }
}
