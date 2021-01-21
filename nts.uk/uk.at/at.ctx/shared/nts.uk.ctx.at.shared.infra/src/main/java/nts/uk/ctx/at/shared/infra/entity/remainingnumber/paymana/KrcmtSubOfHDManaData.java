package nts.uk.ctx.at.shared.infra.entity.remainingnumber.paymana;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name="KRCDT_HD_SUB_MNG")
public class KrcmtSubOfHDManaData extends ContractUkJpaEntity{
	
	@Id
	@Column(name="SUBOFHD_ID")
	public String subOfHDID;
	
	@Column(name="CID")
	public String cID;
	
	@Column(name="SID")
	public String sID;
	
	@Column(name= "UNKNOWN_DATE")
	public boolean unknownDate;

	@Column(name = "DAYOFF_DATE")
	public GeneralDate dayOff;
	
	@Column(name = "NUMBER_OF_DAYS")
	public Double requiredDays;	
	
	@Column(name = "REMAIN_DAYS")
	public Double remainDays;

	@Override
	protected Object getKey() {
		return this.subOfHDID;
	}
	
}
