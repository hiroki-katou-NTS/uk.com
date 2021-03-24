package nts.uk.ctx.at.schedule.infra.entity.displaysetting.authcontrol;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.ScheModifyAuthCtrlByWorkplace;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
import org.apache.commons.lang3.BooleanUtils;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_AUTH_BYWKP")
public class KscmtAuthBywkp extends ContractUkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final JpaEntityMapper<KscmtAuthBywkp> MAPPER = new JpaEntityMapper<>(KscmtAuthBywkp.class);

    @EmbeddedId
    public KscmtAuthBywkpPk kscmtAuthBywkpPk;

    /**
     * 利用できるかどうか
     */
    @Column(name = "AVAILABLE_ATR")
    public int availableAtr;

    @Override
    protected Object getKey() {
        return this.kscmtAuthBywkpPk;
    }

    /**
     * Convert to entity
     *
     * @param domain
     * @return
     */
    public static KscmtAuthCommon of(ScheModifyAuthCtrlByWorkplace domain) {
        return new KscmtAuthCommon(
                new KscmtAuthCommonPk(domain.getCompanyId(), domain.getRoleId(), domain.getFunctionNo())
                , BooleanUtils.toInteger(domain.isAvailable())
        );
    }

    /**
     * Convert to domain
     *
     * @return
     */
    public ScheModifyAuthCtrlByWorkplace toDomain() {
        return new ScheModifyAuthCtrlByWorkplace(
                this.kscmtAuthBywkpPk.companyId
                , this.kscmtAuthBywkpPk.roleId
                , this.kscmtAuthBywkpPk.functionNo
                , this.availableAtr == 1
        );
    }
}
