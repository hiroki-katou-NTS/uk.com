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
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth.MulMonthCheckCond;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcmtEralstCndgrp;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

//複数月のﾁｪｯｸ条件
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_ALST_CHKMLT_UDCSUM")
public class KrcmtAlstChkmltUdcsum extends ContractUkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@Column(name = "ERAL_CHECK_ID")	
	public String errorAlarmCheckID;
	
	//使用区分
	@Basic(optional = false)
	@Column(name = "IS_USE_FLG")
	public int isUseFlg;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcmtAlstChkmltUdcsum", orphanRemoval=true)
	public KrcmtEralstCndgrp krcmtEralstCndgrp;
	
	@OneToOne
	@JoinColumns({ @JoinColumn(name = "ERAL_CHECK_ID", referencedColumnName = "ERAL_CHECK_ID", insertable = false, updatable = false) })
	public KrcmtAlstChkmltUd krcmtAlstChkmltUd;
	
	@Override
	protected Object getKey() {
		return this.errorAlarmCheckID;
	}
	
	
	
	public static KrcmtAlstChkmltUdcsum toEntity(MulMonthCheckCond domain) {
		return new  KrcmtAlstChkmltUdcsum(
				domain.getErrorAlarmCheckID(),
				domain.isUsedFlg() == true ? 1 : 0,
				KrcmtEralstCndgrp.toEntity(domain.getErrorAlarmCheckID(), 
						domain.getErAlAttendanceItemCondition(), true));
	}
	
	public MulMonthCheckCond toDomain() {
		return new MulMonthCheckCond(
				this.errorAlarmCheckID,
				this.isUseFlg == 1 ? true : false,
				this.krcmtEralstCndgrp.toDomain(this.krcmtEralstCndgrp, null, null));
	}

	public KrcmtAlstChkmltUdcsum(String errorAlarmCheckID, int isUseFlg, KrcmtEralstCndgrp krcmtEralstCndgrp) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.isUseFlg = isUseFlg;
		this.krcmtEralstCndgrp = krcmtEralstCndgrp;
	}
}
