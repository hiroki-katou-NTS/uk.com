package nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.dto;

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
public class MandatoryRetireTermDto {

	private String empCommonMasterItemId;
	
	private Boolean usageFlg;
	
	private List<EnableRetirePlanCourseDto> enableRetirePlanCourse;
	
	public MandatoryRetireTerm toDomain() {
		return new MandatoryRetireTerm(
				this.empCommonMasterItemId, 
				this.usageFlg, 
				enableRetirePlanCourse.stream().map(c -> new EnableRetirePlanCourse(c.getRetirePlanCourseId())).collect(Collectors.toList()));
	}

	public MandatoryRetireTermDto(MandatoryRetireTerm domain) {
		super();
		this.empCommonMasterItemId = domain.getEmpCommonMasterItemId();
		this.usageFlg = domain.isUsageFlg();
		this.enableRetirePlanCourse = domain.getEnableRetirePlanCourse().stream().map(c-> new EnableRetirePlanCourseDto(c.getRetirePlanCourseId())).collect(Collectors.toList());
	}
	
}
