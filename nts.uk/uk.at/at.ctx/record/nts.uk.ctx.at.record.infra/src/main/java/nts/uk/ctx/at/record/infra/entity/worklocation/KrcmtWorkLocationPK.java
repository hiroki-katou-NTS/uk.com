package nts.uk.ctx.at.record.infra.entity.worklocation;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
/**
 * 
 * @author hieult
 *
 */
public class KrcmtWorkLocationPK implements Serializable {

	private static final long serialVersionUID = 1L;

	/** CompanyID */
	@NotNull
	@Column(name = "CID")
	public String companyID;

	/** Work Location Code */
	@NotNull
	@Column(name = "WORK_LOCATION_CD")
	public String workLocationCD;

}
