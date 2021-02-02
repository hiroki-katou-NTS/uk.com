package nts.uk.ctx.at.shared.infra.entity.specialholiday.grantinformation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 特別休暇経過年数テーブル　PK
 * @author masaaki_jinno
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KshstElapseYearsTblPK implements Serializable {
	private static final long serialVersionUID = 1L;

	/* 会社ID */
	@Column(name = "CID")
	public String companyId;

	/* 特別休暇コード */
	@Column(name = "SPHD_CD")
	public int specialHolidayCode;
	
	/* 付与回数 */
	@Column(name = "GRANT_CNT")
	public int grantCnt;
}

