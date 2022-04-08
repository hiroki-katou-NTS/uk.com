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
    @Column(name = "COND_NAME")
    public String condName;

    /** 使用区分 */
    @Column(name = "USE_ATR")
    public boolean useAtr;

    /** 複数月チェック種類 */
    @Column(name = "TYPE_CHECK_ITEM")
    public int checkType;

    /** 連続期間 */
    @Column(name = "CONTINUOUS_MONTHS")
    public Integer conMonth;

    /** メッセージ */
    @Column(name = "COND_MESSAGE")
    public String condMsg;

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
        		pk.checkId,
        		EnumAdaptor.valueOf(checkType, WeeklyCheckItemType.class),
        		pk.condNo, useAtr,
        		new ErrorAlarmWorkRecordName(condName), 
        		Optional.ofNullable(condMsg == null ? null : new ErrorAlarmMessage(condMsg)),
        		Optional.ofNullable(conMonth == null ? null : new ContinuousPeriod(conMonth)),
                atdItemCondition);
    }
}
