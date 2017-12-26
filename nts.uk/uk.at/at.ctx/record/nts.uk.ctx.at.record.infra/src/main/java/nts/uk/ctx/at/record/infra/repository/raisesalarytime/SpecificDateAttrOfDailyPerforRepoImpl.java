package nts.uk.ctx.at.record.infra.repository.raisesalarytime;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.raisesalarytime.SpecificDateAttrOfDailyPerfor;
import nts.uk.ctx.at.record.dom.raisesalarytime.repo.SpecificDateAttrOfDailyPerforRepo;

@Stateless
public class SpecificDateAttrOfDailyPerforRepoImpl extends JpaRepository implements SpecificDateAttrOfDailyPerforRepo{

	@Override
	public Optional<SpecificDateAttrOfDailyPerfor> find(String employeeId, GeneralDate baseDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SpecificDateAttrOfDailyPerfor> find(String employeeId, List<GeneralDate> baseDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SpecificDateAttrOfDailyPerfor> find(String employeeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(SpecificDateAttrOfDailyPerfor domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(SpecificDateAttrOfDailyPerfor domain) {
		// TODO Auto-generated method stub
		
	}

}
