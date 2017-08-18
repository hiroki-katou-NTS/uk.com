package nts.uk.ctx.at.request.infra.entity.application.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KafdtApplicationPK implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 会社ID
	 */
	@NotNull
	@Column(name = "CID")
	public String companyID;
	/**
	 * 申請ID
	 */
	@NotNull
	@Column(name = "APP_ID")
	public String applicationID;
	
}
