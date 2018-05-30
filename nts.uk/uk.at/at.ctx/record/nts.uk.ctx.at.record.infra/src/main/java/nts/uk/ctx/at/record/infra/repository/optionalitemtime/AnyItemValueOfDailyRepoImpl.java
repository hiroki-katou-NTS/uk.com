package nts.uk.ctx.at.record.infra.repository.optionalitemtime;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDaily;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDailyRepo;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class AnyItemValueOfDailyRepoImpl extends JpaRepository implements AnyItemValueOfDailyRepo {

	@Override
	public Optional<AnyItemValueOfDaily> find(String employeeId, GeneralDate baseDate) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<AnyItemValueOfDaily> find(String employeeId, List<GeneralDate> baseDate) {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

	@Override
	public List<AnyItemValueOfDaily> find(String employeeId) {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

	@Override
	public List<AnyItemValueOfDaily> finds(List<String> employeeId, DatePeriod baseDate) {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

	@Override
	public List<AnyItemValueOfDaily> finds(Map<String, GeneralDate> param) {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

	@Override
	public void update(AnyItemValueOfDaily domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(AnyItemValueOfDaily domain) {
		// TODO Auto-generated method stub
		
	}

}
