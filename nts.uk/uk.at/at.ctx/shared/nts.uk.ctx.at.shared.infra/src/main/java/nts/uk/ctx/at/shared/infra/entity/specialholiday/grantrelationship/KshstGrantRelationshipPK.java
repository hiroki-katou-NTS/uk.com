package nts.uk.ctx.at.shared.infra.entity.specialholiday.grantrelationship;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KshstGrantRelationshipPK implements Serializable{
	private static final long serialVersionUID = 1L;
	/*会社ID*/
	@Column(name = "CID")
	public String companyId;
	/*コード*/
	@Column(name = "SPHD_CD")
	public String specialHolidayCode;
	/*コード*/
	@Column(name = "RELATIONSHIP_CD")
	public String relationshipCode;
}
