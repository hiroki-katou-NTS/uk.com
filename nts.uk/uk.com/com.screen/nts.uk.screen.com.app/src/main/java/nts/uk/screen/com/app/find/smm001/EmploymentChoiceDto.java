package nts.uk.screen.com.app.find.smm001;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmploymentChoiceDto {
	
	// 会社を指定した雇用一覧
	private List<EmploymentAndLinkedMonthSettingDto> employmentListWithSpecifiedCompany;
	
	// 支払日指定した雇用一覧
	private List<EmploymentAndLinkedMonthSettingDto> employmentListWithSpecifiedPaymentDate;
	
	// 選択可能雇用一覧
	private List<EmploymentDto> employments;
}
