package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.multimonth;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import nts.uk.shr.infra.data.entity.UkJpaEntity;

//複数月のチェック条件(連続)
@Table(name = "KRCMT_MUL_MON_COND_CONT")
public class KrcmtMulMonCondCont extends UkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@Column(name = "MUL_MON_ALARM_CON_ID")	
	public String mulMonAlarmConID;
	
	//使用区分
	@Basic(optional = false)
	@Column(name = "IS_USE_FLG")
	public int isUseFlg;
	
	// 連続月数
	@Basic(optional = false)
	@Column(name = "CONTINOUS_MONTH")
	public int continousMonth;
		
	@Override
	protected Object getKey() {
		return this.mulMonAlarmConID;
	}
}
