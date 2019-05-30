package nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.algorithm.careerpath;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.CareerPath;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.CareerPathRepository;

@Stateless
public class CareerPathService {

	@Inject
	private CareerPathRepository careerPathRepo;

	// キャリアパスの取得
	public Optional<CareerPath> getCareerPath(String cId, String hisId) {
		return careerPathRepo.getCareerPath(cId, hisId);
	}

	// キャリアパスの登録
	public void addCareerPath(CareerPath careerPath) {
		careerPathRepo.addCareerPath(careerPath);
	}

}
