package nts.uk.ctx.at.function.infra.entity.alarm.webmenu;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Entity: アラームリストのWebメニュー
 * @author viet.tx
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KFNDT_ALARM_WEB_MENU")
public class KfndtAlarmWebMenu extends ContractUkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    public KfndtAlarmWebMenuPk pk;

    @Override
    protected Object getKey() {
        return pk;
    }
}
