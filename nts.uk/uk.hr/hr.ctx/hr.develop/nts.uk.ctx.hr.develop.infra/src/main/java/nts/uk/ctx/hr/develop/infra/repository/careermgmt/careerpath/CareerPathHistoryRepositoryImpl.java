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
import nts.arc.time.calendar.period.DatePeriod;

@Stateless
public class CareerPathHistoryRepositoryImpl extends JpaRepository implements CareerPathHistoryRepository{

	private static final String SELECT_BY_CID = "SELECT c FROM JhcmtCareerPath c WHERE c.PK_JHCMT_CAREER_PATH.companyID = :cId";
	
	private static final String SELECT_BY_KEY = "SELECT c FROM JhcmtCareerPath c WHERE c.PK_JHCMT_CAREER_PATH.companyID = :cId AND c.PK_JHCMT_CAREER_PATH.histId = :HistId";
	
	@Override
	public String getLatestCareerPathHist(String cId) {
		List<JhcmtCareerPath> entity =  this.queryProxy().query(SELECT_BY_CID, JhcmtCareerPath.class)
				.setParameter("cId", cId)
				.getList();
		if(entity.isEmpty()) {
			return "";
		}
		return entity.stream().sorted((x, y) -> x.strD.compareTo(y.strD)*(-1)).collect(Collectors.toList()).get(0).PK_JHCMT_CAREER_PATH.histId;
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
				entity.stream().map(c -> new DateHistoryItem(c.PK_JHCMT_CAREER_PATH.histId, new DatePeriod(c.strD, c.endD)))
				.collect(Collectors.toList())));
	}

	@Override
	public void getCareerPathStartDate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional
	public void add(CareerPathHistory domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	@Transactional
	public void update(CareerPathHistory domain) {
		Optional<JhcmtCareerPath> updateEntity = this.queryProxy().query(SELECT_BY_KEY, JhcmtCareerPath.class)
				.setParameter("cId", domain.getCompanyId())
				.setParameter("HistId", domain.getCareerPathHistory().get(0).identifier())
				.getSingle();
		if(!updateEntity.isPresent()) {
			return;
		}
		this.commandProxy().update(new JhcmtCareerPath(
				new JhcmtCareerPathPK(domain.getCompanyId(), domain.getCareerPathHistory().get(0).identifier()),
				domain.getCareerPathHistory().get(0).start(), 
				domain.getCareerPathHistory().get(0).end(),
				updateEntity.get().level));
	}

	@Override
	@Transactional
	public void delete(String cId, String historyId) {
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
