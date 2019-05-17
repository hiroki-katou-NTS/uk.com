package nts.uk.ctx.hr.develop.infra.repository.careermgmt.careerpath;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.CareerPathHistory;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.CareerPathHistoryRepository;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.DateHistoryItem;
import nts.uk.ctx.hr.develop.infra.entity.careermgmt.careerpath.JhcmtCareerPath;
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
				entity.stream().map(c -> new DateHistoryItem(new DatePeriod(c.strD, c.endD), c.PK_JHCMT_CAREER_PATH.HistId))
				.collect(Collectors.toList())));
	}

	@Override
	public void getCareerPathStartDate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCareerPathHist() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCareerPathHist() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeCareerPathHist() {
		// TODO Auto-generated method stub
		
	}
	

}
