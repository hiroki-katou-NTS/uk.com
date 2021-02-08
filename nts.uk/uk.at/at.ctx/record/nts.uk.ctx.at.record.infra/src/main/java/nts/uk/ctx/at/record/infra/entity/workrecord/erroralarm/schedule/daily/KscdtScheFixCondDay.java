package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.schedule.daily;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.FixedCheckSDailyItems;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.FixedExtractionSDailyCon;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmMessage;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Optional;

/**
 * スケジュール日次の固有抽出条件
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="KSCDT_SCHE_FIX_COND_DAY")
public class KscdtScheFixCondDay extends ContractUkJpaEntity {
    @EmbeddedId
    public KscdtScheFixCondDayPk pk;

    /* 使用区分 */
    @Column(name = "USE_ATR")
    public boolean useAtr;

    /* メッセージ */
    @Column(name = "COND_MESSAGE")
    public String condMsg;

    @Override
    protected Object getKey() {
        return this.pk;
    }

    public FixedExtractionSDailyCon toDomain(){
        return new FixedExtractionSDailyCon(pk.checkId, EnumAdaptor.valueOf(pk.No, FixedCheckSDailyItems.class), Optional.ofNullable(condMsg == null ? null : new ErrorAlarmMessage(condMsg)),useAtr);
    }
}
