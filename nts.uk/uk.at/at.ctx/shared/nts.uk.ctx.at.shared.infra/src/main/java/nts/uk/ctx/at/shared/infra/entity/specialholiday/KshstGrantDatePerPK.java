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

	/*付与日のID*/
	@Column(name = "SPHD_CD")
	public String specialHolidayCode;

	/*特別休暇コード*/
	@Column(name = "PERSONAL_GRANT_DATE_CD")
	public String personalGrantDateCode;
}