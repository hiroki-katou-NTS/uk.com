package nts.uk.ctx.at.shared.infra.entity.specialholiday.grantinformation;

import java.io.Serializable;

import javax.persistence.Column;

/**
 * 特別休暇経過付与日数テーブル PK
 * @author masaaki_jinno
 */
public class KshstGrantDateElapseYearsTblPK implements Serializable {
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
	
}