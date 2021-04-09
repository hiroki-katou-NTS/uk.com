package nts.uk.ctx.at.shared.dom.specialholiday.grantinformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;
import nts.uk.ctx.at.shared.dom.specialholiday.periodinformation.SpecialVacationMonths;

/**
 * 特別休暇付与経過年数テーブル
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ElapseYear extends AggregateRoot {
	
	/** 会社ID */
	private String companyId;

	/** 特別休暇コード */
	private SpecialHolidayCode specialHolidayCode;
	
	/** 経過年数テーブル */
	private List<ElapseYearMonthTbl> elapseYearMonthTblList;
	
	/** テーブル以降の固定付与をおこなう */
	private boolean fixedAssign;
	
	/** テーブル以降の付与周期 */
	private Optional<GrantCycleAfterTbl> grantCycleAfterTbl;
	
	
	@Override
	public void validate() {
		super.validate();
	}
	
	public List<String> validateInput() {
		
		List<String> errors = new ArrayList<>();
		List<YearMonth> yearMonth = new ArrayList<>();
		
		for (int i = 0; i < this.elapseYearMonthTblList.size(); i++) {
			ElapseYearMonthTbl elapseYearMonthTbl = this.elapseYearMonthTblList.get(i);
			YearMonth currentYearMonth = YearMonth.of(elapseYearMonthTbl.getElapseYearMonth().getYear(), elapseYearMonthTbl.getElapseYearMonth().getMonth());
			
			
			if (currentYearMonth.year() == 0 && currentYearMonth.month() == 0) {
				errors.add("Msg_95");
			}
	
			if (yearMonth.stream().anyMatch(x -> x.equals(currentYearMonth))) {
				errors.add("Msg_96");
			}
			
			yearMonth.add(currentYearMonth);
		}
		
		return errors;
	}
	
	// 	[1]付与日数テーブルの付与回数を合わせる
	public List<GrantDateTbl> matchNumberOfGrantsInGrantDaysTable(List<GrantDateTbl> listGrantDateTbl) {
		
		listGrantDateTbl.forEach(e -> {
			e.deleteMoreTableThanElapsedYearsTable(this.elapseYearMonthTblList.size());
			e.addLessTableThanElapsedYearsTable(this.elapseYearMonthTblList.size());
		});
		
		return listGrantDateTbl;
	}

	public static ElapseYear createFromJavaType(
			String companyId, 
			int specialHolidayCode, 
			boolean fixedAssign, 
			Integer years, Integer months ) {
		
		return new ElapseYear(
				companyId, 
				new SpecialHolidayCode(specialHolidayCode), 
				new ArrayList<ElapseYearMonthTbl>(),
				fixedAssign, 
				years == null && months == null ? Optional.empty() 
						: Optional.of(GrantCycleAfterTbl.createFromJavaType(years, months)));
	}
	
	
	
}
