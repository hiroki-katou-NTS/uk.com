package nts.uk.ctx.sys.auth.app.find.grant.rolesetjob;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.adapter.employee.JobTitleAdapter;
import nts.uk.ctx.sys.auth.dom.grant.rolesetjob.RoleSetGrantedJobTitleRepository;
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
	
	@Inject
	private JobTitleAdapter jobTitleAdapter;

	public GrantRoleSetJobDto getAllData(GeneralDate refDate) {
		String companyId = AppContexts.user().companyId();
		if (StringUtils.isNoneEmpty(companyId))
			return null;

		// get Job Title by date, companyId
		List<JobTitleDto> listJobTitle = jobTitleAdapter.findAll(companyId, refDate).stream().map(item -> new JobTitleDto(item.getPositionId(), item.getPositionCode(), item.getPositionName())).collect(Collectors.toList());

		// get Sequence Master

		// get Role Set by companyId, sort ASC
		List<RoleSetDto> listRoleSet = roleSetRepo.findByCompanyId(companyId).stream()
				.map(item -> new RoleSetDto(item.getRoleSetCd().v(), item.getRoleSetName().v())).collect(Collectors.toList());
		if (listRoleSet == null || listRoleSet.size() == 0){
			throw new BusinessException("Msg_713");
		}
		listRoleSet.sort((rs1, rs2) -> rs1.getCode().compareTo(rs2.getCode()));

		// get Role Set Granted Job Title
		RoleSetGrantedJobTitleDto roleSetJob = roleSetJobRepo.getOneByCompanyId(companyId)
				.map(item -> RoleSetGrantedJobTitleDto.fromDomain(item)).get();

		return new GrantRoleSetJobDto(listRoleSet, roleSetJob, listJobTitle);
	}

}
