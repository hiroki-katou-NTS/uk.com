package nts.uk.ctx.at.shared.dom.specialholiday.grantinformation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;

/**
 * 特別休暇付与テーブル
 * 
 * @author tanlv
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GrantDateTbl extends AggregateRoot {
	/** 会社ID */
	private String companyId;

	/** 特別休暇コード */
	private SpecialHolidayCode specialHolidayCode;
	
	/** 付与テーブルコード */
	private GrantDateCode grantDateCode;
	
	/** 付与テーブル名称 */
	private GrantDateName grantDateName;
	
	/** 規定のテーブルとする */
	private boolean isSpecified;
	
	/** テーブル以降の固定付与をおこなう */
	private boolean fixedAssign;
	
	/** テーブル以降の固定付与をおこなう */
	private Integer numberOfDays;
	
	/** 経過年数に対する付与日数 */
	private List<ElapseYear> elapseYear;
	
	@Override
	public void validate() {
		super.validate();
	}

	/**
	 * Validate input data
	 */
	public List<String> validateInput() {
		List<String> errors = new ArrayList<>();
		Set<Integer> years = new HashSet<>();
		
		for (int i = 0; i < this.elapseYear.size(); i++) {
			ElapseYear currentElapseYear = this.elapseYear.get(i);
			
			// 同じ経過年数の場合は登録不可
			if(!years.add(currentElapseYear.getYears().v())){
				errors.add("Msg_96");
			}
			
			// 付与日数が入力されていても、年数、月数ともに未入力の場合登録不可
			if ((currentElapseYear.getMonths() == null && currentElapseYear.getYears() == null) 
					&& (currentElapseYear.getGrantedDays() != null || currentElapseYear.getGrantedDays().v() != 0)) {
				errors.add("Msg_100");
			}
			
			// 経過年数が入力されており、付与日数が未入力の場合登録不可
			if ((currentElapseYear.getGrantedDays() == null) 
					&& ((currentElapseYear.getYears() != null && currentElapseYear.getMonths() != null) 
							|| (currentElapseYear.getYears().v() != 0 && currentElapseYear.getMonths().v() != 0))) {
				errors.add("Msg_101");
			}
		}
		
		return errors;
	}
	
	public GrantDateTbl(GrantDateCode grantDateCode, GrantDateName grantDateName, boolean isSpecified, boolean fixedAssign, Integer numberOfDays) {
		this.grantDateCode = grantDateCode;
		this.grantDateName = grantDateName;
		this.isSpecified = isSpecified;
		this.fixedAssign = fixedAssign;
		this.numberOfDays = numberOfDays;
	}

	public static GrantDateTbl createFromJavaType(String grantDateCode, String grantDateName, boolean isSpecified, boolean fixedAssign, Integer numberOfDays) {
		return new GrantDateTbl(new GrantDateCode(grantDateCode), new GrantDateName(grantDateName), isSpecified, fixedAssign, numberOfDays);
	}
	
	/**
	 * Create from Java Type
	 * 
	 * @param companyId
	 * @param specialHolidayCode
	 * @param grantDateCode
	 * @param grantDateName
	 * @param isSpecified
	 * @param fixedAssign
	 * @param numberOfDays
	 * @param elapseYear
	 * @return
	 */
	public static GrantDateTbl createFromJavaType(String companyId, int specialHolidayCode, String grantDateCode, String grantDateName, 
			boolean isSpecified, boolean fixedAssign, Integer numberOfDays, List<ElapseYear> elapseYear) {
		return new GrantDateTbl(companyId, 
				new SpecialHolidayCode(specialHolidayCode),
				new GrantDateCode(grantDateCode),
				new GrantDateName(grantDateName),
				isSpecified,
				fixedAssign,
				numberOfDays,
				elapseYear);
	}
}
