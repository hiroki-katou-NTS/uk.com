package nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.displaysetting;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.DisplayControlPersonalCondition;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name="KSCMT_SYACND_DISPCTL")
public class KscmtSyacndDispCtl extends ContractUkJpaEntity {
	 
	@EmbeddedId
	public KscmtSyacndDispCtlPK pk;
	
	/**表示区分**/
	@Column ( name = "DISP_ATR")
	public boolean dispAtr;
	
	/** 表示記号 **/ 
	@Column ( name = "SYNAME")
	public String  syname;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}
	
	public static KscmtSyacndDispCtl toEntity(DisplayControlPersonalCondition displayControlPersonalCondition){
		return null;
		
	}
}
