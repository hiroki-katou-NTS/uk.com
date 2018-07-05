package nts.uk.ctx.at.shared.infra.entity.specialholiday.specialholidayevent;

import java.io.Serializable;

import javax.persistence.Column;

public class KshstRelationshipPK implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	/* 会社ID */
	@Column(name = "CID")
	public String companyId;

	/* 続柄コード */
	@Column(name = "RELATIONSHIP_CD")
	public String relationshipCd;

}
