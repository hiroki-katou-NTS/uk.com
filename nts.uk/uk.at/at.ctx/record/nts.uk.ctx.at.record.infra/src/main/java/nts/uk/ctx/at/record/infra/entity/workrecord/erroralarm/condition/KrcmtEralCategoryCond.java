package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition;

import java.util.Optional;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErAlCategory;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.anyperiod.ErrorAlarmAnyPeriod;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.AttendanceItemCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.ExtractionCondWeekly;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcmtEralstCndexpiptchk;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmMessage;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * エラーアラームのカテゴリ別抽出条件
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KRCMT_ERAL_CATEGORY_COND")
public class KrcmtEralCategoryCond extends ContractUkJpaEntity {
    @EmbeddedId
    public KrcmtEralCategoryCondPK pk;

    /** アラームリスト抽出条件の名称 */
    @Column(name = "ERAL_ALARM_NAME")
    public String name;

    /** メッセージ */
    @Column(name ="COND_MESSAGE")
    public String message;

    /** グループ2を利用する */
    @Column(name = "GROUP2_USE_ATR")
    public boolean group2UseAtr;

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

    public static final JpaEntityMapper<KrcmtEralCategoryCond> MAPPER = new JpaEntityMapper<>(KrcmtEralCategoryCond.class);
    
    @Override
    protected Object getKey() {
        return this.pk;
    }

    public ExtractionCondWeekly toDomainWeekly(){
        return new ExtractionCondWeekly(
                new ErrorAlarmWorkRecordCode(pk.code),
                new ErrorAlarmWorkRecordName(name),
                message,
                getAttendanceItemCondition());
    }

    public ErrorAlarmAnyPeriod toDomainAnyPeriod(){
        return new ErrorAlarmAnyPeriod(
        		pk.cid,
                new ErrorAlarmWorkRecordCode(pk.code),
                new ErrorAlarmWorkRecordName(name),
                getAttendanceItemCondition(),
                Optional.of(new ErrorAlarmMessage(message)));
    } 
    
	private AttendanceItemCondition getAttendanceItemCondition() {
		AttendanceItemCondition atdItemCondition = AttendanceItemCondition.init(
                this.operatorBetweenGroups, this.group2UseAtr);
        Optional.ofNullable(this.krcstErAlConGroup1).ifPresent(group1 ->
                atdItemCondition.setGroup1(group1.toDomain(this.pk.cid, pk.code)));
        Optional.ofNullable(this.krcstErAlConGroup2).ifPresent(group2 ->
                atdItemCondition.setGroup1(group2.toDomain(this.pk.cid, pk.code)));
		return atdItemCondition;
	}
	
	public static KrcmtEralCategoryCond toEntity(ErrorAlarmAnyPeriod anyPeriod) {
		val pk = new KrcmtEralCategoryCondPK(
					anyPeriod.getCompanyId(), 
					ErAlCategory.ANY_PERIOD.value, 
					anyPeriod.getCode().v());
				
		return new KrcmtEralCategoryCond(
				pk, 
				anyPeriod.getName().v(), 
				anyPeriod.getMessage().map(m -> m.v()).orElse(""), 
				anyPeriod.getCondition().isUseGroup2(), 
				anyPeriod.getCondition().getGroup1().getAtdItemConGroupId(), 
				anyPeriod.getCondition().getGroup2().getAtdItemConGroupId(), 
				anyPeriod.getCondition().getOperatorBetweenGroups().value, 
				KrcmtEralstCndexpiptchk.toEntity(anyPeriod.getCondition().getGroup1(), true), 
				KrcmtEralstCndexpiptchk.toEntity(anyPeriod.getCondition().getGroup2(), false));
	}
}
