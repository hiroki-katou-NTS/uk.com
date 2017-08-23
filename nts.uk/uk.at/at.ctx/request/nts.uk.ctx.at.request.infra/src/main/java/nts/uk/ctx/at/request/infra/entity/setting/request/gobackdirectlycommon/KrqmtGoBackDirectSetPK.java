package nts.uk.ctx.at.request.infra.entity.setting.request.gobackdirectlycommon;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KrqmtGoBackDirectSetPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 会社ID */
	@Column(name = "CID")
	public String companyID;
	/** 申請種類 */
	@Column(name = "APP_ID")
	public String appID;

}
