package nts.uk.ctx.at.record.infra.repository.daily.attendanceleavinggate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.AttendanceLeavingGateOfDaily;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.repo.AttendanceLeavingGateOfDailyRepo;

@Stateless
public class AttendanceLeavingGateOfDailyRepoImpl extends JpaRepository implements AttendanceLeavingGateOfDailyRepo {

	@Override
	public Optional<AttendanceLeavingGateOfDaily> find(String employeeId, GeneralDate baseDate) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<AttendanceLeavingGateOfDaily> find(String employeeId, List<GeneralDate> baseDate) {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

	@Override
	public List<AttendanceLeavingGateOfDaily> find(String employeeId) {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

	@Override
	public void update(AttendanceLeavingGateOfDaily domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(AttendanceLeavingGateOfDaily domain) {
		// TODO Auto-generated method stub
		
	}

}
