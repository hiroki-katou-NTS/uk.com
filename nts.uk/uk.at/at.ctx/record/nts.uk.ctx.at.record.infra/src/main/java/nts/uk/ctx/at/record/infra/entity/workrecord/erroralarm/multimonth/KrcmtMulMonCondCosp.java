package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.multimonth;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth.MulMonthCheckCondCosp;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcmtErAlAtdItemCon;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

//複数月のチェック条件(該当月数)
@Table(name = "KRCMT_MUL_MON_COND_COSP")
@AllArgsConstructor
public class KrcmtMulMonCondCosp extends UkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@Column(name = "ERAL_CHECK_ID")	
	public String errorAlarmCheckID;
	
	//使用区分
	@Basic(optional = false)
	@Column(name = "IS_USE_FLG")
	public int isUseFlg;
	
	// 回数
	@Basic(optional = false)
	@Column(name = "TIMES")
	public int times;

	// 比較演算子
	@Basic(optional = false)
	@Column(name = "COMPARE_OPERATOR")
	public int compareOperator;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
	@JoinColumn(name = "ERAL_CHECK_ID", referencedColumnName = "CONDITION_GROUP_ID", insertable = false, updatable = false)
	public KrcmtErAlAtdItemCon krcmtErAlAtdItemCon;
	
	@Override
	protected Object getKey() {
		return this.errorAlarmCheckID;
	}
	
	public static KrcmtMulMonCondCosp toEntity(MulMonthCheckCondCosp domain) {
		return new KrcmtMulMonCondCosp(domain.getErrorAlarmCheckID(), 
				domain.isUsedFlg() == true ? 1 : 0,
				domain.getTimes(),
				domain.getCompareOperator(),
				KrcmtErAlAtdItemCon.toEntity(domain.getErrorAlarmCheckID(), domain.getErAlAttendanceItemCondition()));
	}

	public MulMonthCheckCondCosp toDomain() {
		return new MulMonthCheckCondCosp(this.errorAlarmCheckID, 
				this.isUseFlg == 1 ? true : false,
				this.times,
				this.compareOperator,
				this.krcmtErAlAtdItemCon.toDomain(this.krcmtErAlAtdItemCon, null, null));
	}
}
