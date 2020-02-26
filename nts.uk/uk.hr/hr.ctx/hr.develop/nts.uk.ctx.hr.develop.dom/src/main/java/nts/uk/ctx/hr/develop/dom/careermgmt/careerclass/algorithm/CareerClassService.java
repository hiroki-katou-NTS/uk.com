package nts.uk.ctx.hr.develop.dom.careermgmt.careerclass.algorithm;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerclass.CareerClass;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerclass.CareerClassRepository;

@Stateless
public class CareerClassService {

	@Inject
	private CareerClassRepository careerClassRepo;
	
	//キャリアマスタリストの取得
	public List<CareerClass> getCareerPath(String cId, GeneralDate referDate) {
		return careerClassRepo.getCareerClassList(cId, referDate);
	}


}
