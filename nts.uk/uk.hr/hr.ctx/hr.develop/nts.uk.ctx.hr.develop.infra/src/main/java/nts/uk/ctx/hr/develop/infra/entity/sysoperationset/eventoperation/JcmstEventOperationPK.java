package nts.uk.ctx.hr.develop.infra.entity.sysoperationset.eventoperation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * 
 * @author yennth
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class JcmstEventOperationPK implements Serializable{
	private static final long serialVersionUID = 1L;
	/* 会社ID */
	@Column(name = "CID")
	public String companyId;
	
	/* イベントID */
	@Column(name = "EVENT_ID")
	public int eventId;

}
