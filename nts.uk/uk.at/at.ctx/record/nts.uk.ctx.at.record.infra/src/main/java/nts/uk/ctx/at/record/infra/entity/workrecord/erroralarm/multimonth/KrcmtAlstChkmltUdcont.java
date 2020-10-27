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
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcmtEralstCndgrp;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

//複数月のチェック条件(連続)
@Entity
@Table(name = "KRCMT_ALST_CHKMLT_UDCONT")
@NoArgsConstructor
public class KrcmtAlstChkmltUdcont extends ContractUkJpaEntity implements Serializable {

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

	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcmtAlstChkmltUdcont", orphanRemoval=true)
	public KrcmtEralstCndgrp krcmtEralstCndgrp;

	@OneToOne
	@JoinColumns({ @JoinColumn(name = "ERAL_CHECK_ID", referencedColumnName = "ERAL_CHECK_ID", insertable = false, updatable = false) })
	public KrcmtAlstChkmltUd krcmtAlstChkmltUd;
	
	@Override
	protected Object getKey() {
		return this.errorAlarmCheckID;
	}

	public static KrcmtAlstChkmltUdcont toEntity(MulMonthCheckCondContinue domain) {
		return new KrcmtAlstChkmltUdcont(domain.getErrorAlarmCheckID(), domain.isUsedFlg() == true ? 1 : 0,
				domain.getContinuousMonths(),
				KrcmtEralstCndgrp.toEntity(domain.getErrorAlarmCheckID(), 
						domain.getErAlAttendanceItemCondition(), true));
	}

	public MulMonthCheckCondContinue toDomain() {
		return new MulMonthCheckCondContinue(this.errorAlarmCheckID, 
				this.isUseFlg == 1 ? true : false,
				this.continousMonth,
				this.krcmtEralstCndgrp.toDomain(this.krcmtEralstCndgrp, null, null));
	}

	public KrcmtAlstChkmltUdcont(String errorAlarmCheckID, int isUseFlg, int continousMonth,
			KrcmtEralstCndgrp krcmtEralstCndgrp) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.isUseFlg = isUseFlg;
		this.continousMonth = continousMonth;
		this.krcmtEralstCndgrp = krcmtEralstCndgrp;
	}
}
