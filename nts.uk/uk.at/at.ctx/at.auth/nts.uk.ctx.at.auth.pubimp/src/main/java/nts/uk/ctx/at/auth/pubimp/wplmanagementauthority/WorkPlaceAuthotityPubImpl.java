package nts.uk.ctx.at.auth.pubimp.wplmanagementauthority;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.auth.dom.wplmanagementauthority.DailyPerformanceFunctionNo;
import nts.uk.ctx.at.auth.dom.wplmanagementauthority.WorkPlaceAuthority;
import nts.uk.ctx.at.auth.dom.wplmanagementauthority.WorkPlaceAuthorityRepository;
import nts.uk.ctx.at.auth.pub.wplmanagementauthority.WorkPlaceAuthorityExport;
import nts.uk.ctx.at.auth.pub.wplmanagementauthority.WorkPlaceAuthorityPub;

@Stateless
public class WorkPlaceAuthotityPubImpl implements WorkPlaceAuthorityPub  {

	@Inject
	private WorkPlaceAuthorityRepository repo;

	@Override
	public List<WorkPlaceAuthorityExport> getAllWorkPlaceAuthority(String companyId) {
		List<WorkPlaceAuthorityExport> data = repo.getAllWorkPlaceAuthority(companyId)
				.stream().map(c-> toDto(c)).collect(Collectors.toList());
		return data;
	}

	@Override
	public List<WorkPlaceAuthorityExport> getAllWorkPlaceAuthorityByRoleId(String companyId, String roleId) {
		List<WorkPlaceAuthorityExport> data = repo.getAllWorkPlaceAuthorityByRoleId(companyId, roleId)
				.stream().map(c-> toDto(c)).collect(Collectors.toList());
		return data;
	}

	@Override
	public Optional<WorkPlaceAuthorityExport> getWorkPlaceAuthorityById(String companyId, String roleId,
			int functionNo) {
		Optional<WorkPlaceAuthorityExport> data = repo.getWorkPlaceAuthorityById(companyId, roleId, functionNo)
				.map(c-> toDto(c));
		return data;
	}

	@Override
	public void addWorkPlaceAuthority(WorkPlaceAuthorityExport workPlaceAuthotity) {
		WorkPlaceAuthority data =  toDomain(workPlaceAuthotity);
		repo.addWorkPlaceAuthority(data);
		
	}

	@Override
	public void updateWorkPlaceAuthority(WorkPlaceAuthorityExport workPlaceAuthotity) {
		WorkPlaceAuthority data =  toDomain(workPlaceAuthotity);
		repo.updateWorkPlaceAuthority(data);
		
	}

	@Override
	public void deleteWorkPlaceAuthority(String companyId, String roleId) {
		repo.deleteWorkPlaceAuthority(companyId, roleId);
		
	}
	
	private WorkPlaceAuthority toDomain(WorkPlaceAuthorityExport entity) {
		return new WorkPlaceAuthority(
				entity.getRoleId(),
				entity.getCompanyId(),
				new DailyPerformanceFunctionNo(entity.getFunctionNo()),
				 entity.isAvailability()
				);
	}
	
	private WorkPlaceAuthorityExport toDto(WorkPlaceAuthority domain) {
		return new WorkPlaceAuthorityExport(
				domain.getRoleId(),
				domain.getCompanyId(),
				Integer.parseInt(domain.getFunctionNo().toString()),
				domain.isAvailability()
				);
	}
	

}
