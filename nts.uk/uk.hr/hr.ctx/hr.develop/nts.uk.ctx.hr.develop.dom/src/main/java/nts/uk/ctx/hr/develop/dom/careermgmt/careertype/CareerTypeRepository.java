package nts.uk.ctx.hr.develop.dom.careermgmt.careertype;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface CareerTypeRepository {

	List<CareerType> getLisCareerType(String cId, GeneralDate referDate);
	
	String getCareerTypeId(String cId, GeneralDate referDate);
}
