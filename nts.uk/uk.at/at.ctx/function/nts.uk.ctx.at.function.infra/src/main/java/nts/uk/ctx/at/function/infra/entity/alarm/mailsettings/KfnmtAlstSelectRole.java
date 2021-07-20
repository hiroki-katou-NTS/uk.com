package nts.uk.ctx.at.function.infra.entity.alarm.mailsettings;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.AlarmMailSendingRole;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Entity: アラームメール送信ロール
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "KFNMT_ALST_SELECT_ROLE")
public class KfnmtAlstSelectRole extends ContractUkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private KfnmtAlstSelectRolePK pk;

    @Override
    protected Object getKey() {
        return pk;
    }

//    public AlarmMailSendingRole toDomain(){
//        return new AlarmMailSendingRole();
//    }
}
