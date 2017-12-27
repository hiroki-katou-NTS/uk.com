package nts.uk.ctx.at.request.infra.entity.application.common;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class KrqdpApplicationPK_New {
	
	@Column(name = "CID")
	public String companyID;
	
	@Column(name = "APP_ID")
	public String appID;
}
