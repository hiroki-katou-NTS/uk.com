package nts.uk.ctx.sys.auth.app.find.grant;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.app.find.roleset.RoleSetDto;
import nts.uk.ctx.sys.auth.dom.adapter.employee.JobTitleAdapter;
import nts.uk.ctx.sys.auth.dom.employee.dto.JobTitleValueImport;
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
	
	@Inject
	private JobTitleAdapter jobTitleAdapter;

	public GrantRoleSetJobDto getAllData(GeneralDate refDate) {
		String companyId = AppContexts.user().companyId();
		if (StringUtils.isNoneEmpty(companyId))
			return null;

		// get Job Title by date, companyId
		List<JobTitleValueImport> listJobTitle = jobTitleAdapter.findAll(companyId, refDate);

		// get Sequence Master

		// get Role Set by companyId, sort ASC
		List<RoleSetDto> listRoleSet = roleSetRepo.findByCompanyId(companyId).stream()
				.map(item -> RoleSetDto.build(item)).collect(Collectors.toList());
		listRoleSet.sort((rs1, rs2) -> rs1.getRoleSetCd().compareTo(rs2.getRoleSetCd()));

		// get Role Set Granted Job Title
		RoleSetGrantedJobTitleDto roleSetJob = roleSetJobRepo.getOneByCompanyId(companyId)
				.map(item -> RoleSetGrantedJobTitleDto.fromDomain(item)).get();

		return new GrantRoleSetJobDto(listRoleSet, roleSetJob, listJobTitle);
	}

}
