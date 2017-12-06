package nts.uk.ctx.sys.auth.app.find.grant.rolesetjob;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

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
		if (companyId == null)
			return null;

		// get Job Title by date, companyId
		List<JobTitleDto> listJobTitle = jobTitleAdapter.findAll(companyId, refDate).stream().map(item -> new JobTitleDto(item.getPositionId(), item.getPositionCode(), item.getPositionName())).collect(Collectors.toList());

		// get Sequence Master return List <position ID, position code, position name, ranking code, sort order>
        //no result => throw new BusinessException("Msg_712");
		if (listJobTitle == null || listJobTitle.isEmpty()) {
			throw new BusinessException("Msg_712");
		}
		// get Role Set by companyId, sort ASC
		List<RoleSetDto> listRoleSet = roleSetRepo.findByCompanyId(companyId).stream()
				.map(item -> new RoleSetDto(item.getRoleSetCd().v(), item.getRoleSetName().v())).collect(Collectors.toList());
		if (listRoleSet == null || listRoleSet.isEmpty()){
			throw new BusinessException("Msg_713");
		}
		listRoleSet.sort((rs1, rs2) -> rs1.getCode().compareTo(rs2.getCode()));

		// get Role Set Granted Job Title
		Optional<RoleSetGrantedJobTitleDto> roleSetJobOpt = roleSetJobRepo.getOneByCompanyId(companyId)
				.map(item -> RoleSetGrantedJobTitleDto.fromDomain(item));
		RoleSetGrantedJobTitleDto roleSetJob = roleSetJobOpt.isPresent()? roleSetJobOpt.get() : null;

		return new GrantRoleSetJobDto(listRoleSet, roleSetJob, listJobTitle);
	}

}
