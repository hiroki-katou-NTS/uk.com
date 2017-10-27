package nts.uk.ctx.at.request.infra.entity.application.common.approveaccepted;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KafdtApproveAcceptedPK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Column(name = "CID")
	public String companyID;

	@NotNull
	@Column(name = "APP_ACCEPTED_ID")
	public String appAccedtedID;
}
