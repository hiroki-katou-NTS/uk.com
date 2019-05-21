package nts.uk.ctx.hr.develop.infra.repository.careermgmt.careerpath;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.CareerPath;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.CareerPathRepository;
import nts.uk.ctx.hr.develop.infra.entity.careermgmt.careerpath.JhcmtCareerPathCareer;
import nts.uk.ctx.hr.develop.infra.entity.careermgmt.careerpath.JhcmtCareerPathCareerPK;
import nts.uk.ctx.hr.develop.infra.entity.careermgmt.careerpath.JhcmtCareerPathReq;

@Stateless
public class CareerPathRepositoryImpl extends JpaRepository implements CareerPathRepository{

	private static final String SELECT_BY_CID = "SELECT c FROM JhcmtCareerPath c WHERE c.PK_JHCMT_CAREER_PATH.companyID = :cId";
	
	private static final String SELECT_BY_KEY = "SELECT c FROM JhcmtCareerPath c WHERE c.PK_JHCMT_CAREER_PATH.companyID = :cId AND c.PK_JHCMT_CAREER_PATH.HistId = :HistId";
	
	
	
	@Override
	public CareerPath getCareerPath(String companyId, String historyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addCareerPath(CareerPath domain) {
		this.commandProxy().insertAll(toEntity(domain));
	}

	@Override
	public void updateCareerPath(CareerPath domain) {
		this.commandProxy().updateAll(toEntity(domain));
	}

	@Override
	public void removeCareerPath() {
		// TODO Auto-generated method stub
		
	}
	
	private List<JhcmtCareerPathCareer> toEntity(CareerPath domain) {
		if(domain.getCareerList().isEmpty()) {
			throw new BusinessException("CareerList not null - ThanhPV");
		}
		return domain.getCareerList().stream().map(c-> {
			String careerId = IdentifierUtil.randomUniqueId();
			return new JhcmtCareerPathCareer(
				new JhcmtCareerPathCareerPK(domain.getCompanyId(), domain.getHistoryId(), careerId), 
				c.getCareerTypeItem(), 
				c.getCareerLevel().v(), 
				c.getCareerClassItem(), 
				c.getCareerClassRole().isPresent()?c.getCareerClassRole().get().v():null, 
				c.getCareerRequirementList().stream().map(d->
					new JhcmtCareerPathReq(d, domain.getCompanyId(), domain.getHistoryId(), careerId)
					).collect(Collectors.toList()));
		}).collect(Collectors.toList());
	}
}
