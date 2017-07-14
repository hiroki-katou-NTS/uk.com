package nts.uk.ctx.sys.portal.app.find.toppagesetting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.portal.dom.toppage.TopPage;
import nts.uk.ctx.sys.portal.dom.toppage.TopPageRepository;
import nts.uk.ctx.sys.portal.dom.toppagesetting.JobPosition;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageSelfSetRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author hoatt
 *
 */
@Stateless
public class TopPageSelfSettingFinder {
	@Inject
	private TopPageRepository topPageRepository;
	@Inject
	private TopPageSelfSetRepository repository;
	
	/**
	 * Find all top page
	 * @return
	 */
	public List<SelectMyPageDto> findSelectMyPage() {
		List<SelectMyPageDto> result = new ArrayList<>();
		
		String companyId = AppContexts.user().companyId();
		
		// get top page
		List<TopPage> topPages = topPageRepository.findAll(companyId);
		if (!topPages.isEmpty()) {
			topPages.stream()
					.forEach(x -> {
						result.add(new SelectMyPageDto(x.getTopPageCode().v(), x.getTopPageName().v()));
					});
		}
		return result;
	}
	/**
	 * Find top page self set
	 * @return
	 */
	public TopPageSelfSettingDto getTopPageSelfSet(){
		//lay employeeId
		String employeeId = AppContexts.user().employeeId();
		Optional<TopPageSelfSettingDto> lst = this.repository.getTopPageSelfSet(employeeId)
				.map(c->TopPageSelfSettingDto.fromDomain(c));
		if(!lst.isPresent()){
			return null;
		}
		return lst.get();
		
	}
	/**
	 * get job position
	 * @param employeeId
	 * @return
	 */
	public JobPositionDto getJobPosition(String employeeId){
		Date date = new Date();
		GeneralDate systemDate = GeneralDate.legacyDate(date);
		Optional<JobPosition> jp = repository.getJobPosition(employeeId, systemDate);
		JobPositionDto jobPosition = null;
		if(jp.isPresent()){
			jobPosition = JobPositionDto.fromDomain(jp.get());
		}
		return jobPosition;
	}
}
