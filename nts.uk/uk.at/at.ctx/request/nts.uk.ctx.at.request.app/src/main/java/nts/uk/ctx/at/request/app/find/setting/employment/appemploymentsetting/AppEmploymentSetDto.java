package nts.uk.ctx.at.request.app.find.setting.employment.appemploymentsetting;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSet;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class AppEmploymentSetDto {
	
	/**
	 * 会社ID
	 */
	private String companyID;
	
	/**
	 * 雇用区分コード
	 */
	private String employmentCD;
	
	/**
	 * 申請別対象勤務種類
	 */
	private List<TargetWorkTypeByAppDto> targetWorkTypeByAppLst;
	
	public static AppEmploymentSetDto fromDomain(AppEmploymentSet appEmploymentSet) {
		return new AppEmploymentSetDto(
				appEmploymentSet.getCompanyID(), 
				appEmploymentSet.getEmploymentCD(), 
				appEmploymentSet.getTargetWorkTypeByAppLst().stream().map(x -> TargetWorkTypeByAppDto.fromDomain(x)).collect(Collectors.toList()));
	}
	
	public AppEmploymentSet toDomain() {
		return new AppEmploymentSet(
				companyID, 
				employmentCD, 
				targetWorkTypeByAppLst.stream().map(x -> x.toDomain()).collect(Collectors.toList()));
	}
	
}
