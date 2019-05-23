package nts.uk.ctx.hr.develop.dom.careermgmt.careerclass;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface CareerClassRepository {

	List<CareerClass> getCareerClassList(String companyId, GeneralDate referenceDate);
}
