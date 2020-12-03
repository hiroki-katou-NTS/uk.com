package nts.uk.ctx.at.shared.app.find.specialholiday;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYear;

@Value
public class ElapseYearDto {
	/** 特別休暇コード */
	private int specialHolidayCode;

	/** 付与テーブルコード */
	private String grantDateCode;

	private int elapseNo;

	/** 付与テーブルコード */
	private int grantedDays;

	/** 付与テーブルコード */
	private int months;

	/** 付与テーブルコード */
	private int years;

	public static ElapseYearDto fromDomain(ElapseYear elapseYear, int grantCnt) {
		return new ElapseYearDto(
				elapseYear.getSpecialHolidayCode(),
				elapseYear.get
				elapseYear.getElapseNo(),
				elapseYear.getGrantedDays().v(),
				elapseYear.getMonths().v(),
				elapseYear.getYears().v()
		);
	}
}
