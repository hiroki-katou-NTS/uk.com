package nts.uk.ctx.at.record.infra.entity.log;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
//import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_EXEC_ERR_MSG")
public class KrcdtExecErrMsg extends UkJpaEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtErrMessageInfoPK krcdtErrMessageInfoPK;
	
	
	
	@Column(name = "MESSAGE_ERROR")
	public String messageError;
	
	

	@Override
	protected Object getKey() {
		return this.krcdtErrMessageInfoPK;
	}

}
