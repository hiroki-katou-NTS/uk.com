package nts.uk.ctx.at.shared.app.find.specialholiday.specialholidayevent;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.EmploymentList;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmploymentListDto {

	/* 会社ID */
	String companyId;

	/* 特別休暇枠NO */
	int specialHolidayEventNo;

	/* 雇用コード */
	String employmentCd;

	public static List<EmploymentListDto> fromEmpList(List<EmploymentList> empLst) {
		return empLst.stream().map(x -> fromDomain(x)).collect(Collectors.toList());
	}

	private static EmploymentListDto fromDomain(EmploymentList domain) {
		return new EmploymentListDto(domain.getCompanyId(), domain.getSpecialHolidayEventNo(),
				domain.getEmploymentCd().v());
	}

}
