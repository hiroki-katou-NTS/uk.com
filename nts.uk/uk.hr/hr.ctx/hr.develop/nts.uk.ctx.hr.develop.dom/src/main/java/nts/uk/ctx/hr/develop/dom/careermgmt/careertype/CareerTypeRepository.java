package nts.uk.ctx.hr.develop.dom.careermgmt.careertype;

import java.util.List;

public interface CareerTypeRepository {

	List<CareerType> getCareerTypeList();
	
	void getCommonCareerTypeId();
}
