package nts.uk.ctx.office.infra.entity.reference.auth;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class OfimtAuthReferPK implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	// column 会社ID
	@NotNull
	@Column(name = "CID")
	private String cid;

	// column 就業ロールID
	@NotNull
	@Column(name = "ATT_ROLE_ID")
	private String employmentRoleId;

	// column 見られる職位ID
	@NotNull
	@Column(name = "JOB_TITLE_ID")
	private String positionIdSeen;
}
