package nts.uk.ctx.at.shared.app.find.specialholiday;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTbl;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrantDateTblDto {
	
	/** 会社ID */
	private String companyId;
	
	/** 特別休暇コード */
	private Integer specialHolidayCode;
	
	/** コード */
	private String grantDateCode;
	
	/** 名称 */
	private String grantDateName;
	
	/** 付与日数  */
	private List<GrantElapseYearMonthDto> elapseYear;
	
	/** 規定のテーブルとする */
	private boolean isSpecified;
	
	/** テーブル以降の付与日数 */
	private Integer grantedDays;
	
	public static GrantDateTblDto fromDomain(GrantDateTbl domain) {
		
		List<GrantElapseYearMonthDto> elapseYear = domain.getElapseYear().stream()
								.map(e -> new GrantElapseYearMonthDto(e.getElapseNo(), e.getGrantedDays().v()))
								.collect(Collectors.toList());
		
		return new GrantDateTblDto(
				domain.getCompanyId(),
				domain.getSpecialHolidayCode().v(),
				domain.getGrantDateCode().v(),
				domain.getGrantDateName().v(),
				elapseYear,
				domain.isSpecified(),
				domain.getGrantedDays().isPresent() ? domain.getGrantedDays().get().v() : null);
	}
}
