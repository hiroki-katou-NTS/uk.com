package nts.uk.ctx.at.record.infra.repository.daily.attendanceleavinggate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.repo.PCLogOnInfoOfDailyRepo;

@Stateless
public class PCLogOnInfoOfDailyRepoImpl extends JpaRepository implements PCLogOnInfoOfDailyRepo {

	@Override
	public Optional<PCLogOnInfoOfDaily> find(String employeeId, GeneralDate baseDate) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<PCLogOnInfoOfDaily> find(String employeeId, List<GeneralDate> baseDate) {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	@Override
	public List<PCLogOnInfoOfDaily> find(String employeeId) {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	@Override
	public void update(PCLogOnInfoOfDaily domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(PCLogOnInfoOfDaily domain) {
		// TODO Auto-generated method stub
		
	}

}
