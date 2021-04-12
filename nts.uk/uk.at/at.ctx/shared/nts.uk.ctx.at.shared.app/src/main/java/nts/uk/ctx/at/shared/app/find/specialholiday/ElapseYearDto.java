package nts.uk.ctx.at.shared.app.find.specialholiday;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYear;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElapseYearDto {
	
	/** 会社ID */
	private String companyId;
	
	/** 特別休暇コード */
	private Integer specialHolidayCode;

	/** 経過年数テーブル */
	private List<ElapseYearMonthTblDto> elapseYearMonthTblList;
	
	/** テーブル以降の固定付与をおこなう */
	private boolean fixedAssign;
	
	/** テーブル以降の付与周期 */
	private GrantCycleAfterTblDto grantCycleAfterTbl;
	
	public static ElapseYearDto fromDomain(ElapseYear domain) {
		
		List<ElapseYearMonthTblDto> elapseYearMonthTblList = domain.getElapseYearMonthTblList().stream()
																.map(e -> new ElapseYearMonthTblDto(e.getGrantCnt(), e.getElapseYearMonth().getYear(), e.getElapseYearMonth().getMonth()))
																.collect(Collectors.toList());
		GrantCycleAfterTblDto grantCycleAfterTbl = new GrantCycleAfterTblDto();
		
		if (domain.getGrantCycleAfterTbl().isPresent()) {
			grantCycleAfterTbl.setYear(domain.getGrantCycleAfterTbl().get().getElapseYearMonth().getYear());
			grantCycleAfterTbl.setMonth(domain.getGrantCycleAfterTbl().get().getElapseYearMonth().getMonth());
		}
		
		return new ElapseYearDto(
				domain.getCompanyId(),
				domain.getSpecialHolidayCode().v(),
				elapseYearMonthTblList,
				domain.isFixedAssign(),
				grantCycleAfterTbl);
	}

}
