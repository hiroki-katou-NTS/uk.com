package nts.uk.ctx.at.shared.infra.entity.specialholiday.grantinformation;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateCode;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateName;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantElapseYearMonth;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantedDays;

/**
 * 特別休暇経過付与日数テーブル PK
 * @author masaaki_jinno
 */
@AllArgsConstructor
@Embeddable
@NoArgsConstructor
public class KshmtHdspElapsedGrantDaysTblPK implements Serializable {
	private static final long serialVersionUID = 1L;

	/* 会社ID */
	@Column(name = "CID")
	public String companyId;

	/* 特別休暇コード */
	@Column(name = "SPHD_CD")
	public int specialHolidayCode;

	/* テーブルコード */
	@Column(name = "GD_TBL_CD")
	public String grantDateCd;

	/* 付与回数 */
	@Column(name = "GRANT_CNT")
	public int grantCnt;
}