package nts.uk.ctx.hr.develop.app.careermgmt.careerpath.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CareerPartDto {

	public Boolean modeUpdate;
	
	public Integer maxClassLevel;
	
	public List<MasterCareer> careerType;
	
	public List<MasterCareer> careerClass;
	
	public List<Career> career;
}
