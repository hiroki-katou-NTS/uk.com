package nts.uk.ctx.at.request.infra.entity.application.holidayshipment.brkoffsupchangemng;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.holidayshipment.brkoffsupchangemng.BrkOffSupChangeMng;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 振休申請休出変更管理
 * 
 * @author sonnlb
 */
@Entity
@Table(name = "KRQDT_BRK_OFF_SUP_CHG_MNG")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KrqdtBrkOffSupChangeMng extends ContractUkJpaEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrqdtBrkOffSupChangeMngPK pk;

	@Override
	protected Object getKey() {
		return pk;
	}
	
	public BrkOffSupChangeMng toDomain(){
		return new BrkOffSupChangeMng(this.pk.getLeaveAppID(), this.pk.getAbsenceLeaveAppID());
	}
}
