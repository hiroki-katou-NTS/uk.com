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
	public List<CareerType> getCareerTypeList(String cId, GeneralDate referDate) {
		return careerTypeRepo.getLisCareerType(cId, referDate);
	}
	
	//共通職務IDの取得
	public String getCommonCareerTypeId(String cId, GeneralDate referDate) {
		return careerTypeRepo.getCareerTypeId(cId, referDate);
	}


}
