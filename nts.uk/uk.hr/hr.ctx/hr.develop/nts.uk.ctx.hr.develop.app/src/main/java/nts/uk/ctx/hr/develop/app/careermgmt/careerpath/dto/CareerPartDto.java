package nts.uk.ctx.hr.develop.app.careermgmt.careerpath.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CareerPartDto {

	public Boolean modeUpdate;
	
	public Integer maxClassLevel;
	
	public List<MasterCareerDto> careerType;
	
	public List<MasterCareerDto> careerClass;
	
	public List<CareerDto> career;
}
