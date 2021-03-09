package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.schedule.daily;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.AlarmCheckClassification;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.FixedCheckSDailyItems;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.FixedExtractionSDailyItems;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.NameAlarmExtractCond;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmMessage;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Optional;

/**
 * スケジュール日次の固有抽出項目
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="KSCDT_SCHE_FIX_ITEM_DAY")
public class KscdtScheFixItemDay extends JpaEntity {
    @EmbeddedId
    public KscdtScheFixItemDayPk pk;

    /* 名称 */
    @Column(name = "ITEM_NAME")
    public String itemName;

    /* 区分 */
    @Column(name = "ITEM_ATR")
    public int itemAtr;

    /* 初期メッセージ */
    @Column(name = "ITEM_MESSAGE")
    public String itemMsg;

    @Override
    protected Object getKey() {
        return this.pk;
    }

    public FixedExtractionSDailyItems toDomain(){
        return new FixedExtractionSDailyItems(EnumAdaptor.valueOf(pk.No, FixedCheckSDailyItems.class),EnumAdaptor.valueOf(itemAtr, AlarmCheckClassification.class),new NameAlarmExtractCond(itemName), Optional.ofNullable(itemMsg == null ? null : new ErrorAlarmMessage(itemMsg)));
    }
}
