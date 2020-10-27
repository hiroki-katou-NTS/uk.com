package nts.uk.ctx.at.request.infra.entity.setting.applicationformreason;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "KRQST_APP_REASON")
public class KrqstAppReason  extends ContractUkJpaEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*主キー*/
	@EmbeddedId
	public KrqstAppReasonPK krqstAppReasonPK; 
	
	@Column(name ="DISPORDER")
	public int dispOrder;
	
	
	@Column(name ="REASON_TEMP")
	public String reasonTemp;
	@Setter
	@Column(name ="DEFAULT_FLG")
	public int defaultFlg;
	
	@Override
	protected Object getKey() {
		return krqstAppReasonPK;
	}

}
