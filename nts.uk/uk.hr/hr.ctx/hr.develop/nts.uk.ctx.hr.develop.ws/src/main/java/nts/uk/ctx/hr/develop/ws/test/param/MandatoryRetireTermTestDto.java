package nts.uk.ctx.hr.develop.ws.test.param;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.EnableRetirePlanCourse;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.MandatoryRetireTerm;

@Getter
@NoArgsConstructor
@Setter
public class MandatoryRetireTermTestDto {

	private String empCommonMasterItemId;
	
	private Boolean usageFlg;
	
	private List<EnableRetirePlanCourseTestDto> enableRetirePlanCourse;
	
	public MandatoryRetireTerm toDomain() {
		return new MandatoryRetireTerm(
				this.empCommonMasterItemId, 
				this.usageFlg, 
				enableRetirePlanCourse.stream().map(c -> new EnableRetirePlanCourse(c.getRetirePlanCourseId())).collect(Collectors.toList()));
	}

	public MandatoryRetireTermTestDto(MandatoryRetireTerm domain) {
		super();
		this.empCommonMasterItemId = domain.getEmpCommonMasterItemId();
		this.usageFlg = domain.isUsageFlg();
		this.enableRetirePlanCourse = domain.getEnableRetirePlanCourse().stream().map(c-> new EnableRetirePlanCourseTestDto(c.getRetirePlanCourseId())).collect(Collectors.toList());
	}
	
}
