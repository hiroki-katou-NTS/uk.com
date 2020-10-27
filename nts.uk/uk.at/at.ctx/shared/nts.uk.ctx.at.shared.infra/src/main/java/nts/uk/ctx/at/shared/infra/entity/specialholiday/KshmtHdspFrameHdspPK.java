package nts.uk.ctx.at.shared.infra.entity.specialholiday;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KshmtHdspFrameHdspPK implements Serializable {
private static final long serialVersionUID = 1L;
	
	/* 会社ID */
	@Column(name = "CID")
	public String companyId;
	
	/* 特別休暇コード */
	@Column(name = "SPHD_CD")
	public int specialHolidayCode;
	
	/* 特別休暇枠NO */
	@Column(name = "SPHD_NO")
	public Integer sphdNo;
}
