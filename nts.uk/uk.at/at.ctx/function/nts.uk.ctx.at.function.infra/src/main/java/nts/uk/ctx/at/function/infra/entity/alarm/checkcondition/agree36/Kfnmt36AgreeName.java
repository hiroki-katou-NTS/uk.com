package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.agree36;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNCT_ALST_FXITM_36AGR")
public class Kfnmt36AgreeName extends ContractUkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public Kfnmt36AgreeNamePK kfnmt36AgreeNamePK;
	
	/** 名称 */
	@Column(name = "NAME")
	public String name;
	
	@Override
	protected Object getKey() {
		return kfnmt36AgreeNamePK;
	}

}
