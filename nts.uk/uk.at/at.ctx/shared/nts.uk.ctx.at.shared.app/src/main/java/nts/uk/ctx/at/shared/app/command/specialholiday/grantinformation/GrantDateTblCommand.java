package nts.uk.ctx.at.shared.app.command.specialholiday.grantinformation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYear;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYearMonthTbl;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantCycleAfterTbl;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateCode;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateName;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTbl;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantElapseYearMonth;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantedDays;

@Value
public class GrantDateTblCommand {
	
	/** 特別休暇コード */
	private int specialHolidayCode;
	
	// for GrantDateTbl

	/** コード */
	private String grantDateCode; 

	/** 名称 */
	private String grantDateName; 
	
	/** 付与日数  */
	private List<GrantElapseYearMonthCommand> elapseYear; 

	/** 規定のテーブルとする */
	private boolean isSpecified; 

	/** テーブル以降の付与日数 */
	private Integer grantedDays; 
	
	
	// for ElapseYear 
	
	/** 経過年数テーブル */
	private List<ElapseYearMonthTblCommand> elapseYearMonthTblList; 
	
	/** テーブル以降の固定付与をおこなう */
	private boolean fixedAssign; 
	
	private Integer year; 
	
	private Integer month; 
	
	private boolean isUpdate;
	
	public static GrantDateTbl toGrantDateTblDomain(GrantDateTblCommand command, String companyId) {
		
		List<GrantElapseYearMonth> elapseYear = command.getElapseYear().stream()
				.map(e -> GrantElapseYearMonth.createFromJavaType(e.getElapseNo(), e.getGrantedDays()))
				.collect(Collectors.toList());
		
		return new GrantDateTbl(
				companyId,
				new SpecialHolidayCode(command.specialHolidayCode),
				new GrantDateCode(command.grantDateCode),
				new GrantDateName(command.grantDateName),
				elapseYear,
				command.isSpecified,
				Optional.ofNullable(command.getGrantedDays() == null ?
						null : new GrantedDays(command.getGrantedDays())));
	}
	
	public static ElapseYear toElapseYearDomain(GrantDateTblCommand command, String companyId) {
		
		List<ElapseYearMonthTbl> elapseYearMonthTblList = command.getElapseYearMonthTblList().stream()
				.map(e -> ElapseYearMonthTbl.createFromJavaType(e.getGrantCnt(), e.getElapseYearMonth().getYear(), e.getElapseYearMonth().getMonth()))
				.collect(Collectors.toList());
		
		return new ElapseYear(
				companyId, 
				new SpecialHolidayCode(command.getSpecialHolidayCode()), 
				elapseYearMonthTblList,
				command.isFixedAssign(), 
				command.getYear() == null && command.getMonth() == null ? Optional.empty() 
					: Optional.of(GrantCycleAfterTbl.createFromJavaType(command.getYear(), command.getMonth())));
	}
	
	
}
