package nts.uk.ctx.at.schedule.infra.repository.shift.basicworkregister;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassifiBasicWorkRepository;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassificationBasicWork;

@Stateless
public class JpaClassifiBasicWorkRepository implements ClassifiBasicWorkRepository {

	@Override
	public void insert(ClassificationBasicWork classificationBasicWork) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(ClassificationBasicWork classificationBasicWork) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String companyId, String classificationCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<ClassificationBasicWork> find(String companyId, String classificationCode) {
		// TODO Auto-generated method stub
		return null;
	}

}
