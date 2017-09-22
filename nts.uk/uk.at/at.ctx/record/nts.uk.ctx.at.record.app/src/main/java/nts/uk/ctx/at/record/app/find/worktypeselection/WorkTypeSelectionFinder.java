package nts.uk.ctx.at.record.app.find.worktypeselection;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypesRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * @author anhvd
 *
 */
@Stateless
public class WorkTypeSelectionFinder {

	@Inject
	private BusinessTypesRepository businessTypeRepository;

	public List<WorkTypeSelectionDto> getListWorkTypeSelection() {
		String companyId = AppContexts.user().companyId();
		List<WorkTypeSelectionDto> workTypeSelectionList = businessTypeRepository.findAll(companyId).stream()
				.map(item -> {
					return new WorkTypeSelectionDto(item.getBusinessTypeCode().v(), item.getBusinessTypeName().v());
				}).collect(Collectors.toList());
		return workTypeSelectionList;

	}
}
