package nts.uk.ctx.at.shared.infra.entity.yearholidaygrant;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.query.DBCharPaddingAs;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayCode;
/**
 * 
 * @author yennth
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KshstLengthServiceTblPK implements Serializable{
	private static final long serialVersionUID = 1L;
	/* 会社ID */
	@Column(name = "CID")
	public String companyId;
	
	/* 年休付与テーブル設定コード */
	@Column(name = "YEAR_HD_CD")
	@DBCharPaddingAs(YearHolidayCode.class)
	public String yearHolidayCode;
	
	/* 付与回数 */
	@Column(name = "GRANT_NUM")
	public int grantNum;
	
}
