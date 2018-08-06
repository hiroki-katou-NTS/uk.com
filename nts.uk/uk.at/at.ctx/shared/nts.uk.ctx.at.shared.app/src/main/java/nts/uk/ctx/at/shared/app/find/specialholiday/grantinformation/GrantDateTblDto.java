package nts.uk.ctx.at.shared.app.find.specialholiday.grantinformation;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYear;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTbl;
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
	
	/** テーブル以降の固定付与をおこなう */
	private Integer numberOfDays;
	
	/** 経過年数に対する付与日数 */
	private List<ElapseYearDto> elapseYear;

	public GrantDateTbl toDomain() {
		String companyId = AppContexts.user().companyId();
		
		List<ElapseYear> elapseYear = this.elapseYear.stream().map(x-> {
			return new ElapseYear(companyId, specialHolidayCode, grantDateCode, x.getElapseNo(), 
					new GrantedDays(x.getGrantedDays() != null ? x.getGrantedDays() : 0), 
					new SpecialVacationMonths(x.getMonths() != null ? x.getMonths() : 0), 
					new GrantedYears(x.getYears() != null ? x.getYears() : 0));
		}).collect(Collectors.toList());
		
		return  GrantDateTbl.createFromJavaType(companyId, specialHolidayCode, grantDateCode, grantDateName, 
				isSpecified, fixedAssign, numberOfDays, elapseYear);
	}

	public static GrantDateTblDto fromDomain(GrantDateTbl domain) {
		if(domain == null) {
			return null;
		}
		
		List<ElapseYearDto> elapseYearDto = domain.getElapseYear().stream()
				.map(x-> ElapseYearDto.fromDomain(x))
				.collect(Collectors.toList());
		
		return new GrantDateTblDto(
				domain.getSpecialHolidayCode().v(), 
				domain.getGrantDateCode().v(),
				domain.getGrantDateName().v(),
				domain.isSpecified(),
				domain.isFixedAssign(),
				domain.getNumberOfDays(),
				elapseYearDto
		);
	}
}
