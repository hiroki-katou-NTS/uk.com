package nts.uk.ctx.at.shared.app.find.specialholiday;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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

	/**
	 * コンストラクタ
	 */
	public ElapseYearDto() {
		specialHolidayCode = -1;
		grantDateCode = "";
		elapseNo = -1;
		grantedDays = -1;
		months = 0;
		years = 0;
	}

	public static ElapseYearDto fromDomain(ElapseYear elapseYear, int grantCnt) {

//		return new ElapseYearDto(
//				elapseYear.getSpecialHolidayCode(),
//				elapseYear.ge
//				elapseYear.getElapseNo(),
//				elapseYear.getGrantedDays().v(),
//				elapseYear.getMonths().v(),
//				elapseYear.getYears().v()
//		);

		return new ElapseYearDto();
	}
}
