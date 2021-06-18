package nts.uk.ctx.at.schedule.infra.entity.displaysetting.authcontrol;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.ScheModifyAuthCtrlCommon;
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
@Table(name = "KSCMT_AUTH_COMMON")
public class KscmtAuthCommon extends ContractUkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final JpaEntityMapper<KscmtAuthCommon> MAPPER = new JpaEntityMapper<>(KscmtAuthCommon.class);

    @EmbeddedId
    public KscmtAuthCommonPk kscmtAuthCommonPk;

    /**
     * 利用できるかどうか
     */
    @Column(name = "AVAILABLE_ATR")
    public int availableAtr;

    @Override
    protected Object getKey() {
        return this.kscmtAuthCommonPk;
    }

    /**
     * Convert to entity
     *
     * @param domain
     * @return
     */
    public static KscmtAuthCommon of(ScheModifyAuthCtrlCommon domain) {
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
    public ScheModifyAuthCtrlCommon toDomain() {
        return new ScheModifyAuthCtrlCommon(
                this.kscmtAuthCommonPk.companyId
                , this.kscmtAuthCommonPk.roleId
                , this.kscmtAuthCommonPk.functionNo
                , this.availableAtr == 1
        );
    }
}
