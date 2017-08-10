package nts.uk.ctx.at.request.infra.entity.setting.applicationapproval.gobackdirectlycommon;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KrqstGoBackDirectSetPK {
	@Column(name = "CID")
	public String companyID;

	@Column(name = "APP_ID")
	public String appID;

}
