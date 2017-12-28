package nts.uk.ctx.at.record.infra.repository.shorttimework;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.shorttimework.ShortTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.shorttimework.repo.ShortTimeOfDailyPerformanceRepository;

@Stateless
public class JpaShortTimeOfDailyPerformanceRepo extends JpaRepository implements ShortTimeOfDailyPerformanceRepository{

	@Override
	public Optional<ShortTimeOfDailyPerformance> find(String employeeId, GeneralDate ymd) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<ShortTimeOfDailyPerformance> findByListEmployeeId(List<String> employeeIds, List<GeneralDate> ymds) {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

	@Override
	public void updateByKey(ShortTimeOfDailyPerformance shortWork) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insert(ShortTimeOfDailyPerformance shortWork) {
		// TODO Auto-generated method stub
		
	}

}
