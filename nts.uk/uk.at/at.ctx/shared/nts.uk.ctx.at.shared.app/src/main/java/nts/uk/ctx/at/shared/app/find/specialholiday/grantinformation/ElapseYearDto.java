package nts.uk.ctx.at.shared.app.find.specialholiday.grantinformation;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYear;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYearMonth;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYearMonthTbl;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTbl;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantElapseYearMonth;

@Value
@AllArgsConstructor
public class ElapseYearDto {

	/** 付与テーブルコード */
	private String grantDateCode;

	/** 経過年数 */
	private int elapseNo;

	/** 付与日数 */
	private Integer grantedDays;

	/** 月 */
	private Integer months;

	/** 年 */
	private Integer years;

	public ElapseYearDto() {
		grantDateCode = "";
		elapseNo = -1;
		grantedDays = -1;
		months = -1;
		years = -1;
	}

	/**
	 * ドメインから変換
	 * @param grantDateTbl 特別休暇付与日数テーブル
	 * @param grantElapseYearMonth 経過年数に対する付与日数
	 * @param elapseYearMonthTbl 経過年数テーブル
	 * @return
	 */
	public static ElapseYearDto fromDomain(
			GrantDateTbl grantDateTbl,
			GrantElapseYearMonth grantElapseYearMonth,
			ElapseYearMonthTbl elapseYearMonthTbl) {
		if(grantDateTbl == null
				|| grantElapseYearMonth == null
				|| elapseYearMonthTbl == null) {
				return null;
			}
		return new ElapseYearDto(
				grantDateTbl.getGrantDateCode().v(),
				elapseYearMonthTbl.getGrantCnt(),
				grantElapseYearMonth.getGrantedDays().v(),
				elapseYearMonthTbl.getElapseYearMonth().getMonth(),
				elapseYearMonthTbl.getElapseYearMonth().getYear()
		);
	}
}
