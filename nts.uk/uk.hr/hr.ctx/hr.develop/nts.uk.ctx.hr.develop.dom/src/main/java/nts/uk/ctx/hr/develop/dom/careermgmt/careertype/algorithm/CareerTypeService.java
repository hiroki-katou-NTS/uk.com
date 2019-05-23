package nts.uk.ctx.hr.develop.dom.careermgmt.careertype.algorithm;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.careermgmt.careertype.CareerType;
import nts.uk.ctx.hr.develop.dom.careermgmt.careertype.CareerTypeRepository;

@Stateless
public class CareerTypeService {

	@Inject
	private CareerTypeRepository careerTypeRepo;
	
	//職務マスタリストの取得
	public List<CareerType> getCareerPath(String cId, GeneralDate referDate) {
		return careerTypeRepo.getLisHisId(cId, referDate);
	}


}
