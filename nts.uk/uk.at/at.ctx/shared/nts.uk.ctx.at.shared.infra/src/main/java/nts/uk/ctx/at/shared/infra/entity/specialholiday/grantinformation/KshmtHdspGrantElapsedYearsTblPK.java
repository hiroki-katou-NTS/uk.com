package nts.uk.ctx.at.shared.infra.entity.specialholiday.grantinformation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 特別休暇付与経過年数テーブル PK
 * @author masaaki_jinno
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KshmtHdspGrantElapsedYearsTblPK implements Serializable {
	private static final long serialVersionUID = 1L;

	/* 会社ID */
	@Column(name = "CID")
	public String companyId;

	/* 特別休暇コード */
	@Column(name = "SPHD_CD")
	public int specialHolidayCode;

}
