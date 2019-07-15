package nts.uk.ctx.pereg.infra.entity.person.info.ctg;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PPECT_PER_INFO_CHK_CTG")
public class PpectPerInfoChkCtg extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
    public PpectPerInfoChkCtgPK ppectPerInfoChkCtgPK;
	
	@Basic(optional = false)
	@Column(name = "SCHEDULE_MNG_REQUIRED")
	public int scheduleMngReq;
	
	@Basic(optional = false)
	@Column(name = "YEARLY_MNG_REQUIRED")
	public int yearMngReq;
	
	@Basic(optional = false)
	@Column(name = "DAILY_ACTUAL_MNG_REQUIRED")
	public int dailyActualMngReq;
	
	@Basic(optional = false)
	@Column(name = "MONTH_ACTUAL_MNG_REQUIRED")
	public int monthActualMngReq;
	
	@Basic(optional = false)
	@Column(name = "MONTH_CALC_MNG_REQUIRED")
	public int monthCalcMngReq;
	
	@Basic(optional = false)
	@Column(name = "PAYROLL_MNG_REQUIRED")
	public int payMngReq;
	
	@Basic(optional = false)
	@Column(name = "BONUS_MNG_REQUIRED")
	public int bonusMngReq;
	
	@Basic(optional = false)
	@Column(name = "JOB_SYS_REQUIRED")
	public int jobSysReq;
	
	@Basic(optional = false)
	@Column(name = "PAYROLL_SYS_REQUIRED")
	public int paySysReq;
	
	@Basic(optional = false)
	@Column(name = "HUMAN_SYS_REQUIRED")
	public int humanSysReq;
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return ppectPerInfoChkCtgPK;
	}
	
}
