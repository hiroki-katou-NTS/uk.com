package nts.uk.ctx.at.request.infra.entity.application.stamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrqdtAppSampNRPk {
	
	
	@Column(name="CID")
	public String companyID;
	
	@Column(name="APP_ID")
	public String appID;
}
