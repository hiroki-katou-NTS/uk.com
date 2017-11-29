/**
 * 
 */
package nts.uk.ctx.bs.employee.app.find.classification.affiliate;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.classification.affiliate_ver1.AffClassHistItemRepository_ver1;
import nts.uk.ctx.bs.employee.dom.classification.affiliate_ver1.AffClassHistItem_ver1;
import nts.uk.ctx.bs.employee.dom.classification.affiliate_ver1.AffClassHistoryRepository_ver1;
import nts.uk.ctx.bs.employee.dom.classification.affiliate_ver1.AffClassHistory_ver1;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

/**
 * @author danpv
 *
 */
@Stateless
public class AffClassificationFinder implements PeregFinder<AffClassificationDto> {

	@Inject
	private AffClassHistoryRepository_ver1 affClassHistRepo;

	@Inject
	private AffClassHistItemRepository_ver1 affClassHistItemRepo;

	@Override
	public String targetCategoryCode() {
		return "CS00004";
	}

	@Override
	public Class<AffClassificationDto> dtoClass() {
		return AffClassificationDto.class;
	}

	@Override
	public DataClassification dataType() {
		return DataClassification.EMPLOYEE;
	}

	@Override
	public AffClassificationDto getSingleData(PeregQuery query) {
		Optional<AffClassHistItem_ver1> itemOption = affClassHistItemRepo.getByEmpIdAndReferDate(query.getEmployeeId(),
				query.getStandardDate());
		if ( itemOption.isPresent()) {
			AffClassHistItem_ver1 histItem = itemOption.get();
			AffClassHistory_ver1 history = affClassHistRepo.getByHistoryId(histItem.getHistoryId()).get();
			return AffClassificationDto.createFromDomain(histItem, history);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.shr.pereg.app.find.PeregFinder#getListData(nts.uk.shr.pereg.app.
	 * find.PeregQuery)
	 */
	@Override
	public List<PeregDomainDto> getListData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

}
