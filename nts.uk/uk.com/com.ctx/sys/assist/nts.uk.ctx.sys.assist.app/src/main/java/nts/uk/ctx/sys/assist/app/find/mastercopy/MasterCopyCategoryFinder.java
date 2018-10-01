package nts.uk.ctx.sys.assist.app.find.mastercopy;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategory;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategoryRepository;

/**
 * The Class MasterCopyCategoryFinder.
 */
@Stateless
public class MasterCopyCategoryFinder {

	/** The repository. */
	@Inject
	private MasterCopyCategoryRepository repository;

	/**
	 * Gets the all master copy category.
	 *
	 * @return the all master copy category
	 */
	public List<MasterCopyCategoryFindDto> getAllMasterCopyCategory() {
		// get list category from database
		List<MasterCopyCategory> listMasterCopyCategory = this.repository.findAllMasterCopyCategory();

		// check empty
		if (listMasterCopyCategory.isEmpty()) {
			return null;
		}

		// get list category dto
		List<MasterCopyCategoryFindDto> listMasterCopyCategoryDto = listMasterCopyCategory.stream().map(e -> new MasterCopyCategoryFindDto(e.getSystemType().value, e.getCategoryName().v(), e.getOrder().v(), e.getCategoryNo().v())).collect(Collectors.toList());
		// sort by system then order;
		return listMasterCopyCategoryDto.stream().sorted(Comparator.comparing(MasterCopyCategoryFindDto::getSystemType).thenComparing(MasterCopyCategoryFindDto::getOrder)).collect(Collectors.toList());
		
	}
}
