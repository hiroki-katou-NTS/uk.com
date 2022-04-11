package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.weekly;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.*;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionAtr;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ErrorAlarmConditionType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.*;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.ContinuousPeriod;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.ExtractionCondWeekly;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.WeeklyCheckItemType;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.*;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmMessage;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 週別実績の抽出条件 Entity
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KRCDT_WEEK_COND_ALARM")
public class KrcdtWeekCondAlarm extends ContractUkJpaEntity {
    @EmbeddedId
    public KrcdtWeekCondAlarmPk pk;

    /** アラームリスト抽出条件の名称 */
    @Column(name = "ERROR_ALARM_NAME")
    public String name;

    /** グループ2を利用する */
    @Column(name = "GROUP2_USE_ATR")
    public int group2UseAtr;

    /** グループ1 */
    @Basic(optional = true)
    @Column(name = "ATD_ITEM_CONDITION_GROUP1")
    public String atdItemConditionGroup1;

    /** グループ2 */
    @Basic(optional = true)
    @Column(name = "ATD_ITEM_CONDITION_GROUP2")
    public String atdItemConditionGroup2;

    /** グループ間の演算子 */
    @Column(name = "OPERATOR_BETWEEN_GROUPS")
    public int operatorBetweenGroups;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
    @JoinColumn(name = "ATD_ITEM_CONDITION_GROUP1", referencedColumnName = "CONDITION_GROUP_ID", insertable = false, updatable = false)
    public KrcmtEralstCndexpiptchk krcstErAlConGroup1;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
    @JoinColumn(name = "ATD_ITEM_CONDITION_GROUP2", referencedColumnName = "CONDITION_GROUP_ID", insertable = false, updatable = false)
    public KrcmtEralstCndexpiptchk krcstErAlConGroup2;

    @Override
    protected Object getKey() {
        return this.pk;
    }

    public ExtractionCondWeekly toDomain(String errorAlarmCode){
        AttendanceItemCondition atdItemCondition = AttendanceItemCondition.init(this.operatorBetweenGroups, this.group2UseAtr == 1);
        Optional.ofNullable(this.krcstErAlConGroup1).ifPresent(group1 ->
                atdItemCondition.setGroup1(group1.toDomain(this.pk.cid, errorAlarmCode)));
        Optional.ofNullable(this.krcstErAlConGroup2).ifPresent(group2 ->
                atdItemCondition.setGroup1(group2.toDomain(this.pk.cid, errorAlarmCode)));

        return new ExtractionCondWeekly(
        		new ErrorAlarmWorkRecordCode(pk.code),
                new ErrorAlarmWorkRecordName(name),
                atdItemCondition);
    }
}
