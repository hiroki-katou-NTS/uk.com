package nts.uk.ctx.at.shared.app.find.specialholiday.grantinformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYear;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateCode;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateName;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTbl;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantElapseYearMonth;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantedDays;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantedYears;
import nts.uk.ctx.at.shared.dom.specialholiday.periodinformation.SpecialVacationMonths;
import nts.uk.shr.com.context.AppContexts;

@Value
public class GrantDateTblDto {

	/** 特別休暇コード */
	private int specialHolidayCode;

	/** 付与テーブルコード */
	private String grantDateCode;

	/** 付与テーブル名称 */
	private String grantDateName;

	/** 規定のテーブルとする */
	private boolean isSpecified;

	/** テーブル以降の固定付与をおこなう */
	private boolean fixedAssign;

	private Integer numberOfDays;

	/** 経過年数に対する付与日数 */
	private List<ElapseYearDto> elapseYear;

	/**
	 * コンストラクタ
	 */
	public GrantDateTblDto() {
		specialHolidayCode = -1;
		grantDateCode = "";
		grantDateName = "";
		isSpecified = false;
		fixedAssign = false;
		numberOfDays = 0;
		elapseYear = new ArrayList<ElapseYearDto>();
	}

	public GrantDateTbl toDomain() {

		String companyId = AppContexts.user().companyId();

		List<GrantElapseYearMonth> grantElapseYearMonth
			= this.elapseYear.stream().map(x-> {
				return GrantElapseYearMonth.createFromJavaType(
					x.getElapseNo(), x.getGrantedDays());
		}).collect(Collectors.toList());

		return new GrantDateTbl(
				companyId,
				new SpecialHolidayCode(specialHolidayCode),
				new GrantDateCode(grantDateCode),
				new GrantDateName(grantDateName),
				grantElapseYearMonth,
				isSpecified,
				Optional.of(new GrantedDays(numberOfDays))
				);
	}

	public static GrantDateTblDto fromDomain(GrantDateTbl domain) {
		if(domain == null) {
			return null;
		}

//		List<ElapseYearDto> elapseYearDto;
//			= domain.getElapseYear().stream()
//				.map(x-> ElapseYearDto.fromDomain(x))
//				.collect(Collectors.toList());

		GrantDateTblDto grantDateTblDto = new GrantDateTblDto();

//		grantDateTblDto.specialHolidayCode = domain.getSpecialHolidayCode().v();
//		grantDateTblDto.grantDateCode = domain.getGrantDateCode().v();
//		grantDateTblDto.grantDateName = domain.getGrantDateName().v();
//		grantDateTblDto.isSpecified = domain.isSpecified();
//		grantDateTblDto.fixedAssign = false;
//		grantDateTblDto.numberOfDays = 0;
//		grantDateTblDto.elapseYear = elapseYearDto;

		return grantDateTblDto;
	}
}
