package nts.uk.ctx.hr.develop.infra.repository.careermgmt.careerpath;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.CareerPathHistory;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.CareerPathHistoryRepository;
import nts.uk.ctx.hr.develop.infra.entity.careermgmt.careerpath.JhcmtCareerPath;
import nts.uk.ctx.hr.develop.infra.entity.careermgmt.careerpath.JhcmtCareerPathPK;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class CareerPathHistoryRepositoryImpl extends JpaRepository implements CareerPathHistoryRepository{

	private static final String SELECT_BY_CID = "SELECT c FROM JhcmtCareerPath c WHERE c.PK_JHCMT_CAREER_PATH.companyID = :cId";
	
	@Override
	public CareerPathHistory getLatestCareerPathHist() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<CareerPathHistory> getCareerPathHist(String cId) {
		List<JhcmtCareerPath> entity =  this.queryProxy().query(SELECT_BY_CID, JhcmtCareerPath.class)
				.setParameter("cId", cId)
				.getList();
		if(entity.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(new CareerPathHistory(cId,
				entity.stream().map(c -> new DateHistoryItem(c.PK_JHCMT_CAREER_PATH.HistId, new DatePeriod(c.strD, c.endD)))
				.collect(Collectors.toList())));
	}

	@Override
	public void getCareerPathStartDate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional
	public void addCareerPathHist(CareerPathHistory domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	@Transactional
	public void updateCareerPathHist(CareerPathHistory domain) {
		this.commandProxy().update(toEntity(domain));
	}

	@Override
	@Transactional
	public void removeCareerPathHist(String cId, String historyId) {
		this.commandProxy().remove(JhcmtCareerPath.class, new JhcmtCareerPathPK(cId,historyId));
	}
	
	private JhcmtCareerPath toEntity(CareerPathHistory domain) {
		if(domain.getCareerPathHistory().isEmpty()) {
			throw new BusinessException("HistoryId not null - ThanhPV");
		}
		return new JhcmtCareerPath(
				new JhcmtCareerPathPK(domain.getCompanyId(), domain.getCareerPathHistory().get(0).identifier()),
				domain.getCareerPathHistory().get(0).start(), 
				domain.getCareerPathHistory().get(0).end(),
				null);
	}

}
