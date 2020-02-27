package nts.uk.ctx.hr.develop.app.sysoperationset.businessrecognition.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ParamSearchDto {

	private String key;
	
	private Boolean checks;
	
	private String departmentId;
}
