package nts.uk.ctx.at.request.infra.entity.setting.request.gobackdirectlycommon;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KrqmtGoBackDirectSetPK {
	@Column(name = "CID")
	public String companyID;

	@Column(name = "APP_ID")
	public String appID;

}
