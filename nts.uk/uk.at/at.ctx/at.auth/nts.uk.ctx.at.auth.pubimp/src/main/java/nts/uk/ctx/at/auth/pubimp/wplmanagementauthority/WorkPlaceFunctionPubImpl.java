package nts.uk.ctx.at.auth.pubimp.wplmanagementauthority;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.auth.dom.wplmanagementauthority.WorkPlaceFunction;
import nts.uk.ctx.at.auth.dom.wplmanagementauthority.WorkPlaceFunctionRepository;
import nts.uk.ctx.at.auth.pub.wplmanagementauthority.WorkPlaceFunctionExport;
import nts.uk.ctx.at.auth.pub.wplmanagementauthority.WorkPlaceFunctionPub;

@Stateless
public class WorkPlaceFunctionPubImpl implements WorkPlaceFunctionPub {
	@Inject
	private WorkPlaceFunctionRepository repo;

	@Override
	public List<WorkPlaceFunctionExport> getAllWorkPlaceFunction() {
		List<WorkPlaceFunctionExport> data = repo.getAllWorkPlaceFunction().stream()
				.map(c->toDto(c)).collect(Collectors.toList());
		return data;
	}

	@Override
	public Optional<WorkPlaceFunctionExport> getWorkPlaceFunctionById(int functionNo) {
		Optional<WorkPlaceFunctionExport> data = repo.getWorkPlaceFunctionById(functionNo)
				.map(c->toDto(c));
		return data;
	}
	
	private WorkPlaceFunctionExport toDto(WorkPlaceFunction workPlaceFunction) {
		return new WorkPlaceFunctionExport(
				workPlaceFunction.getFunctionNo().v(),
				workPlaceFunction.isInitialValue(),
				workPlaceFunction.getDisplayName().v(),
				workPlaceFunction.getDisplayOrder(),
				workPlaceFunction.getDescription().v()
				);
	}

}
