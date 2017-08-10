package nts.uk.ctx.at.shared.infra.entity.relationship;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
@AllArgsConstructor
public class KshstRelationshipPK implements Serializable{
	private static final long serialVersionUID = 1L;
	/*会社ID*/
	@Column(name = "CID")
	public String companyId;
	/*コード*/
	@Column(name = "RELATIONSHIP_CD")
	public String relationshipcd;
}
