package nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.algorithm.careerpath;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.Career;
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
	
	//キャリア条件の取得
	public List<Career> getCareerPathRequirement(String cId, String hisId, String careerTypeItem) {
		Optional<CareerPath> careerPath = careerPathRepo.getCareerPath(cId, hisId);
		if(careerPath.isPresent()) {
			return careerPath.get().getCareerList().stream().filter(c -> c.getCareerTypeItem().equals(careerTypeItem)).collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

}
