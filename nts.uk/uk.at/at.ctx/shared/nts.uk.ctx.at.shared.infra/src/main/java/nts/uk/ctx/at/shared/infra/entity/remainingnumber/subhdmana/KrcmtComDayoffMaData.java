package nts.uk.ctx.at.shared.infra.entity.remainingnumber.subhdmana;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Table(name="KRCMT_COM_DAYOFF_MA_DATA")
@Entity
public class KrcmtComDayoffMaData extends ContractUkJpaEntity{

	@Column(name="COM_DAYOFF_ID")
	@Id
	public String comDayOffID;
		
	@Column(name="CID")
	public String cID;
	
	@Column(name="SID")
	public String sID;
	
	@Column(name= "UNKNOWN_DATE")
	public boolean unknownDate;

	@Column(name = "DAYOFF_DATE")
	public GeneralDate dayOff;
	
	@Column(name = "REQUIRED_DAYS")
	public Double requiredDays;
	
	@Column(name = "REQUIRED_TIMES")
	public int requiredTimes;
	
	@Column(name = "REMAIN_DAYS")
	public Double remainDays;
	
	@Column(name = "REMAIN_TIMES")
	public int remainTimes;
	
	@Override
	protected Object getKey() {
		return this.comDayOffID;
	}

}
