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
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth.MulMonthCheckCondAverage;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcmtErAlAtdItemCon;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

//複数月のチェック条件(平均)
@Table(name = "KRCMT_MUL_MON_COND_AVG")
@AllArgsConstructor
public class KrcmtMulMonCondAvg extends UkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@Column(name = "ERAL_CHECK_ID")	
	public String errorAlarmCheckID;
	
	//使用区分
	@Basic(optional = false)
	@Column(name = "IS_USE_FLG")
	public int isUseFlg;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
	@JoinColumn(name = "ERAL_CHECK_ID", referencedColumnName = "CONDITION_GROUP_ID", insertable = false, updatable = false)
	public KrcmtErAlAtdItemCon krcmtErAlAtdItemCon;
	
	@Override
	protected Object getKey() {
		return this.errorAlarmCheckID;
	}
	
	public static KrcmtMulMonCondAvg toEntity(MulMonthCheckCondAverage domain) {
		return new  KrcmtMulMonCondAvg(
				domain.getErrorAlarmCheckID(),
				domain.isUsedFlg() == true ? 1 : 0,
				KrcmtErAlAtdItemCon.toEntity(domain.getErrorAlarmCheckID(), 
						domain.getErAlAttendanceItemCondition()));
	}
	
	public MulMonthCheckCondAverage toDomain() {
		return new MulMonthCheckCondAverage(
				this.errorAlarmCheckID,
				this.isUseFlg == 1 ? true : false,
				this.krcmtErAlAtdItemCon.toDomain(this.krcmtErAlAtdItemCon, null, null));
	}
}
