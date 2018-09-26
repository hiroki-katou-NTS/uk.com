package nts.uk.ctx.at.shared.dom.dailyperformanceformat.businesstype;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * join object of KrcmtBusinessTypeOfHistory and KrcmtBusinessTypeOfEmployee
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
@Data
public class BusinessTypeOfEmpDto {

	private String companyId;

	private String employeeId;

	private String historyId;

	private GeneralDate startDate;
	
	private GeneralDate endDate;

	private String businessTypeCd;
	
	@Value
	public static class List {
		private final java.util.List<BusinessTypeOfEmpDto> list;

		/**
		 * 勤務種別変更者を再作成するか判定する
		 * 
		 * @param empId
		 * @param targetDate
		 * @param reWorkTypeChange
		 * @param workTypeCode
		 * @return
		 */
		public boolean isReWorkerTypeChangePerson(String empId, GeneralDate targetDate, Boolean reWorkTypeChange,
				String businessTypeCd) {
			if (!reWorkTypeChange)
				return true;
			
			Optional<BusinessTypeOfEmpDto> businessTypeOfEmpHis = this.list.stream()
					.filter(x -> (x.getEmployeeId().equals(empId) && x.getStartDate().beforeOrEquals(targetDate)
							&& x.getEndDate().afterOrEquals(targetDate)))
					.findFirst();
			
			if (!businessTypeOfEmpHis.isPresent()) {
				return true;
			}

			if (!businessTypeOfEmpHis.get().getBusinessTypeCd().equals(businessTypeCd))
				return true;

			return false;
		}
	}
}
