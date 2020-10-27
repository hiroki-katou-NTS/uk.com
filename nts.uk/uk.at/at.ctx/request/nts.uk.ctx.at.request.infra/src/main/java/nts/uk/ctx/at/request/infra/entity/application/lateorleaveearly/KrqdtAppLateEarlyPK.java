package nts.uk.ctx.at.request.infra.entity.application.lateorleaveearly;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * 
 * @author hieult
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KrqdtAppLateEarlyPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(name = "CID")
	public String companyID;
	
	@NotNull
	@Column(name = "APP_ID")
	public String appID;



}
