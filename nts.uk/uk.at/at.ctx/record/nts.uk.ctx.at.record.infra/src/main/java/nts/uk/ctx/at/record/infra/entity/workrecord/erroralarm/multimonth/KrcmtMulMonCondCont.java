package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.multimonth;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth.MulMonthCheckCondContinue;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcmtErAlAtdItemCon;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

//複数月のチェック条件(連続)
@Entity
@Table(name = "KRCMT_MUL_MON_COND_CONT")
@NoArgsConstructor
public class KrcmtMulMonCondCont extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@Column(name = "ERAL_CHECK_ID")
	public String errorAlarmCheckID;

	// 使用区分
	@Basic(optional = false)
	@Column(name = "IS_USE_FLG")
	public int isUseFlg;

	// 連続月数
	@Basic(optional = false)
	@Column(name = "CONTINUOUS_MONTH")
	public int continousMonth;

	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcmtMulMonCondCont", orphanRemoval=true)
	public KrcmtErAlAtdItemCon krcmtErAlAtdItemCon;

	@OneToOne
	@JoinColumns({ @JoinColumn(name = "ERAL_CHECK_ID", referencedColumnName = "ERAL_CHECK_ID", insertable = false, updatable = false) })
	public KrcmtMulMonAlarmCheck krcmtMulMonAlarmCheck;
	
	@Override
	protected Object getKey() {
		return this.errorAlarmCheckID;
	}

	public static KrcmtMulMonCondCont toEntity(MulMonthCheckCondContinue domain) {
		return new KrcmtMulMonCondCont(domain.getErrorAlarmCheckID(), domain.isUsedFlg() == true ? 1 : 0,
				domain.getContinuousMonths(),
				KrcmtErAlAtdItemCon.toEntity(domain.getErrorAlarmCheckID(), 
						domain.getErAlAttendanceItemCondition(), true));
	}

	public MulMonthCheckCondContinue toDomain() {
		return new MulMonthCheckCondContinue(this.errorAlarmCheckID, 
				this.isUseFlg == 1 ? true : false,
				this.continousMonth,
				this.krcmtErAlAtdItemCon.toDomain(this.krcmtErAlAtdItemCon, null, null));
	}

	public KrcmtMulMonCondCont(String errorAlarmCheckID, int isUseFlg, int continousMonth,
			KrcmtErAlAtdItemCon krcmtErAlAtdItemCon) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.isUseFlg = isUseFlg;
		this.continousMonth = continousMonth;
		this.krcmtErAlAtdItemCon = krcmtErAlAtdItemCon;
	}
}
