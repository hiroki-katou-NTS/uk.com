package nts.uk.ctx.at.function.infra.entity.alarm.mailsettings;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity: アラームメール送信ロール
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "KFNMT_ALST_SELECT_ROLE")
public class KfnmtAlstSelectRole extends ContractUkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private KfnmtAlstSelectRolePK pk;

    @ManyToOne
    @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false)
    public KfnmtAlstMailSetRole mailSetRole;

    @Override
    protected Object getKey() {
        return pk;
    }

    public KfnmtAlstSelectRole(KfnmtAlstSelectRolePK pk) {
        this.pk = pk;
    }
}
