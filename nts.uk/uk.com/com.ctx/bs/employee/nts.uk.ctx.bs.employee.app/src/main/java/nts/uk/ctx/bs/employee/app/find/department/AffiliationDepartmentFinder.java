/**
 * 
 */
package nts.uk.ctx.bs.employee.app.find.department;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

/**
 * @author danpv
 *
 */
@Stateless
public class AffiliationDepartmentFinder implements PeregFinder<AffiliationDepartmentDto>{

	@Override
	public String targetCategoryCode() {
		return "CS00011";
	}

	@Override
	public Class<AffiliationDepartmentDto> dtoClass() {
		return AffiliationDepartmentDto.class;
	}

	/* (non-Javadoc)
	 * @see nts.uk.shr.pereg.app.find.PeregSingleFinder#getSingleData(nts.uk.shr.pereg.app.find.PeregQuery)
	 */
	@Override
	public PeregDomainDto getSingleData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.shr.pereg.app.find.PeregFinder#getListData(nts.uk.shr.pereg.app.find.PeregQuery)
	 */
	@Override
	public List<PeregDomainDto> getListData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.shr.pereg.app.find.PeregFinder#dataType()
	 */
	@Override
	public DataClassification dataType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ComboBoxObject> getListFirstItems(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AffiliationDepartmentDto> getAllData(List<PeregQuery> query) {
		// TODO Auto-generated method stub
		return null;
	}

}
