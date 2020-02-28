package nts.uk.ctx.hr.develop.infra.repository.careermgmt.careerpath;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.Career;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.CareerPath;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.CareerPathRepository;
import nts.uk.ctx.hr.develop.infra.entity.careermgmt.careerpath.JhcmtCareerPath;
import nts.uk.ctx.hr.develop.infra.entity.careermgmt.careerpath.JhcmtCareerPathCareer;
import nts.uk.ctx.hr.develop.infra.entity.careermgmt.careerpath.JhcmtCareerPathCareerPK;
import nts.uk.ctx.hr.develop.infra.entity.careermgmt.careerpath.JhcmtCareerPathPK;
import nts.uk.ctx.hr.develop.infra.entity.careermgmt.careerpath.JhcmtCareerPathReq;

@Stateless
public class CareerPathRepositoryImpl extends JpaRepository implements CareerPathRepository{

	private static final String SELECT_CAREER_ID_BY_CID_HISTID = "SELECT c.PK_JHCMT_CAREER_PATH_CAREER.careerId FROM JhcmtCareerPathCareer c WHERE c.PK_JHCMT_CAREER_PATH_CAREER.companyID = :companyID AND c.PK_JHCMT_CAREER_PATH_CAREER.histId =:histId";
	
	@Override
	public Optional<CareerPath> getCareerPath(String companyId, String historyId) {
		List<String> listCareerId = this.getCareerId(companyId, historyId);
		if(listCareerId.isEmpty()) {
			return Optional.empty();
		}
		List<Career> careerList = new ArrayList<>(); 
		for (String careerId : listCareerId) {
			JhcmtCareerPathCareer etity = this.getEntityManager().find(JhcmtCareerPathCareer.class, new JhcmtCareerPathCareerPK(companyId, historyId, careerId));
			careerList.add(etity.toDomain());
		}
		Integer maxClassLevel = this.getMaxClassLevel(companyId, historyId);
		if(maxClassLevel == null) {
			return Optional.empty();
		}
		return Optional.ofNullable(CareerPath.createFromJavaType(companyId, historyId, maxClassLevel, careerList));
	}

	@Override
	@Transactional
	public void addCareerPath(CareerPath domain) {
		JhcmtCareerPath careerPathEntity = this.getCareerPathEntity(domain.getCompanyId(), domain.getHistoryId());
		if(careerPathEntity == null) {
			throw new BusinessException("MsgJ_49");
		}else {
			List<String> listCareerId = this.getCareerId(domain.getCompanyId(), domain.getHistoryId());
			List<JhcmtCareerPathCareerPK> careerPathCareerPK = listCareerId.stream().map(c -> new JhcmtCareerPathCareerPK(domain.getCompanyId(), domain.getHistoryId(), c)).collect(Collectors.toList()); 
			
			//remove all CareerPathCareer
			this.commandProxy().removeAll(JhcmtCareerPathCareer.class, careerPathCareerPK);
			
			//set MaxClassLevel careerPath
			careerPathEntity.level = domain.getMaxClassLevel().v();
			
			//Update career path
			this.commandProxy().update(careerPathEntity);
	
			//add List<JhcmtCareerPathCareer>
			this.commandProxy().insertAll(toEntity(domain));
		}
	}

	@Override
	public void updateCareerPath(CareerPath domain) {
		this.commandProxy().updateAll(toEntity(domain));
	}

	@Override
	@Transactional
	public void removeCareerPath(String companyId, String historyId) {
		List<String> careerIds = this.getCareerId(companyId, historyId);
		for (String careerId : careerIds) {
			this.commandProxy().remove(JhcmtCareerPathCareer.class, new JhcmtCareerPathCareerPK(companyId, historyId, careerId));
		}
	}
	
	private JhcmtCareerPath getCareerPathEntity(String companyId, String historyId) {
		return this.getEntityManager().find(JhcmtCareerPath.class, new JhcmtCareerPathPK(companyId,historyId));
	}
	
	private List<JhcmtCareerPathCareer> toEntity(CareerPath domain) {
		if(domain.getCareerList().isEmpty()) {
			throw new BusinessException("MsgJ_50");
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
	
	private List<String> getCareerId(String companyId, String historyId){
		return this.queryProxy().query(SELECT_CAREER_ID_BY_CID_HISTID, String.class)
				.setParameter("companyID", companyId)
				.setParameter("histId", historyId)
				.getList();
	}
	
	private Integer getMaxClassLevel(String companyId, String historyId){
		return this.getEntityManager().find(JhcmtCareerPath.class, new JhcmtCareerPathPK(companyId,historyId)).level;
	}
}
