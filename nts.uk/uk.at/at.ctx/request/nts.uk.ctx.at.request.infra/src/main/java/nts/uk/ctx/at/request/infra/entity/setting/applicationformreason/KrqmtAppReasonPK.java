package nts.uk.ctx.at.request.infra.entity.setting.applicationformreason;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrqmtAppReasonPK implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**会社ID*/
	@Column(name = "CID")
	public String companyId;
	/**申請種類*/
	@Column(name = "APP_TYPE")
	public int appType;
	/** 理由ID */
	@Column(name ="REASON_ID")
	public String reasonID;
}
