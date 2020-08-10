package nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.displaysetting;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.DisplayControlPersonalCondition;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.PersonInforDisplayControl;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name="KSCMT_SYACND_DISPCTL")
@Getter
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
	
	@ManyToOne
    @PrimaryKeyJoinColumns({
    	@PrimaryKeyJoinColumn(name="CID", referencedColumnName="CID"),
    	@PrimaryKeyJoinColumn(name="CND_ATR", referencedColumnName="CND_ATR")
    })
	
	
	
	public static List<KscmtSyacndDispCtl> toEntity(DisplayControlPersonalCondition dom){
		List<KscmtSyacndDispCtl> lstCtl = new ArrayList<>();
		List<PersonInforDisplayControl> lst = dom.getListConditionDisplayControl();
		lst.forEach(x ->{
			KscmtSyacndDispCtlPK pk = new KscmtSyacndDispCtlPK(dom.getCompanyID() , x.getConditionATR().value);
			KscmtSyacndDispCtl entity = new KscmtSyacndDispCtl(pk,
					x.getDisplayCategory().value == 0 ? false : true,
					dom.getOtpWorkscheQualifi().isPresent() ? dom.getOtpWorkscheQualifi().get().getQualificationMark().v() : null );
			lstCtl.add(entity);
		});	
		return lstCtl;	
	}
	
	public DisplayControlPersonalCondition toDomain(){
		
		
		return new DisplayControlPersonalCondition(
				this.pk.cid, 
				listConditionDisplayControl,
				otpWorkscheQualifi)
	}
}
