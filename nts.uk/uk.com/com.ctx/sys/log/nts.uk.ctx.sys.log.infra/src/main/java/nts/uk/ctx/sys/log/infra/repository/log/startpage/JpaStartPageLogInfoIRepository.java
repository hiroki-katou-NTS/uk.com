package nts.uk.ctx.sys.log.infra.repository.log.startpage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.security.audittrail.start.StartPageLog;
import nts.uk.shr.com.security.audittrail.start.StartPageLogRepository;
import nts.uk.shr.com.security.audittrail.start.StartPageLogStorageRepository;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class JpaStartPageLogInfoIRepository extends JpaRepository implements StartPageLogStorageRepository, StartPageLogRepository{

	@Override
	public Optional<StartPageLog> find(String operationId) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<StartPageLog> finds(String companyId) {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	@Override
	public List<StartPageLog> finds(GeneralDate targetDate) {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	@Override
	public List<StartPageLog> finds(DatePeriod targetDate) {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	@Override
	public List<StartPageLog> findBySid(String sId) {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	@Override
	public List<StartPageLog> findBySid(List<String> sIds) {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	@Override
	public void save(StartPageLog log) {
		// TODO Auto-generated method stub
		
	}

}
