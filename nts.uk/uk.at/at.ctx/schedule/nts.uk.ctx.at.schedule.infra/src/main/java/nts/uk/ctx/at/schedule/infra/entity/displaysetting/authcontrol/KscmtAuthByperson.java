package nts.uk.ctx.at.schedule.infra.entity.displaysetting.authcontrol;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.ScheModifyAuthCtrlByPerson;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
import org.apache.commons.lang3.BooleanUtils;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * KSCMT_AUTH_BYPERSON : 	スケジュール修正個人別の権限制御
 * @author viet.tx
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_AUTH_BYPERSON")
public class KscmtAuthByperson extends ContractUkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final JpaEntityMapper<KscmtAuthByperson> MAPPER = new JpaEntityMapper<>(KscmtAuthByperson.class);

    @EmbeddedId
    public KscmtAuthBypersonPk kscmtAuthBypersonPk;

    /**
     * 利用できるかどうか
     */
    @Column(name = "AVAILABLE_ATR")
    public int availableAtr;

    @Override
    protected Object getKey() {
        return this.kscmtAuthBypersonPk;
    }

    /**
     * Convert to entity
     * @param domain
     * @return
     */
    public static KscmtAuthByperson of(ScheModifyAuthCtrlByPerson domain) {
        return new KscmtAuthByperson(
                new KscmtAuthBypersonPk(domain.getCompanyId(), domain.getRoleId(), domain.getFunctionNo())
                , BooleanUtils.toInteger(domain.isAvailable())
        );
    }

    /**
     * Convert to domain
     * @return
     */
    public ScheModifyAuthCtrlByPerson toDomain() {
        return new ScheModifyAuthCtrlByPerson(
                this.kscmtAuthBypersonPk.companyId
                , this.kscmtAuthBypersonPk.roleId
                , this.kscmtAuthBypersonPk.functionNo
                , this.availableAtr == 1
        );
    }
}
