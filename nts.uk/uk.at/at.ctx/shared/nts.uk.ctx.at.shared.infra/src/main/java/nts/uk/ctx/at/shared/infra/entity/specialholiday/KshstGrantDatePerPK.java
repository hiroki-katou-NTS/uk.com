package nts.uk.ctx.at.shared.infra.entity.specialholiday;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KshstGrantDatePerPK implements Serializable {
	private static final long serialVersionUID = 1L;

	/* 会社ID */
	@Column(name = "CID")
	public String companyId;

	/* 付与日のID */
	@Column(name = "GRANT_DATE_ID")
	public String grantDateId;

	/* コード */
	@Column(name = "GRANT_DATE_CD")
	public String grantDateCode;
}