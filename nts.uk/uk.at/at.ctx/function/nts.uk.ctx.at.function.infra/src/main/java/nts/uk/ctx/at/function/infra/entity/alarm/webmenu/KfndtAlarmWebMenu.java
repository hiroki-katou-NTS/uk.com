package nts.uk.ctx.at.function.infra.entity.alarm.webmenu;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.webmenu.*;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.webmenu.System;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionCode;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmCategory;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckType;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public static AlarmListWebMenu toDomain(List<KfndtAlarmWebMenu> entities) {
        if (CollectionUtil.isEmpty(entities)) return null;
        return new AlarmListWebMenu(
                entities.get(0).pk.cid,
                entities.get(0).pk.conditionCode,
                new AlarmCheckConditionCode(entities.get(0).pk.alarmCheckCode),
                EnumAdaptor.valueOf(entities.get(0).pk.category, AlarmCategory.class),
                EnumAdaptor.valueOf(entities.get(0).pk.checkAtr, AlarmListCheckType.class),
                entities.stream().map(e -> new WebMenuInfo(
                        EnumAdaptor.valueOf(e.pk.system, System.class),
                        new WebMenuCode(e.pk.menuCode),
                        EnumAdaptor.valueOf(e.pk.menuCls, MenuClassification.class)
                )).collect(Collectors.toList())
        );
    }

    public KfndtAlarmWebMenuGroupKey getGroupKey() {
        return new KfndtAlarmWebMenuGroupKey(
                pk.conditionCode,
                pk.alarmCheckCode,
                pk.category,
                pk.checkAtr
        );
    }

    @AllArgsConstructor
    @EqualsAndHashCode
    public class KfndtAlarmWebMenuGroupKey {
        public String conditionCode;
        public String alarmCheckCode;
        public int category;
        public int checkAtr;
    }
}
