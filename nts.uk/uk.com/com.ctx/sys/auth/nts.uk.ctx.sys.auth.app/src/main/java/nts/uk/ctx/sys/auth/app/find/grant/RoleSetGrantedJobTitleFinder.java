package nts.uk.ctx.sys.auth.app.find.grant;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.app.find.roleset.RoleSetDto;
import nts.uk.ctx.sys.auth.dom.grant.RoleSetGrantedJobTitleRepository;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSetRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT
 *
 */
@Stateless
public class RoleSetGrantedJobTitleFinder {

	@Inject
	private RoleSetRepository roleSetRepo;

	@Inject
	private RoleSetGrantedJobTitleRepository roleSetJobRepo;

	public GrantRoleSetJobDto getAllData(GeneralDate refDate) {
		String companyId = AppContexts.user().companyId();
		if (StringUtils.isNoneEmpty(companyId))
			return null;

		// get Job Title by date, companyId
		/*List<JobTitleInfo> jobs = this.jobTitleInfoRepository.findAll(companyId, baseDate);

		return jobs.stream()
				.map(job -> JobTitleItemDto.builder().id(job.getJobTitleId())
				.code(job.getJobTitleCode().v()).name(job.getJobTitleName().v()).build())
				.sorted((job1, job2) -> job1.getCode().compareTo(job2.getCode()))
				.collect(Collectors.toList());
		*/

		// get Sequence Master

		// get Role Set by companyId, sort ASC
		List<RoleSetDto> listRoleSet = roleSetRepo.findByCompanyId(companyId).stream()
				.map(item -> RoleSetDto.build(item)).collect(Collectors.toList());
		listRoleSet.sort((rs1, rs2) -> rs1.getRoleSetCd().compareTo(rs2.getRoleSetCd()));

		// get Role Set Granted Job Title
		RoleSetGrantedJobTitleDto roleSetJob = roleSetJobRepo.getOneByCompanyId(companyId)
				.map(item -> RoleSetGrantedJobTitleDto.fromDomain(item)).get();

		return new GrantRoleSetJobDto(listRoleSet, roleSetJob);
	}

}
