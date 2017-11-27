/**
 * 
 */
package find.personemercontact;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.shr.pereg.app.find.PeregListFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.PeregDto;

/**
 * @author danpv
 *
 */
@Stateless
public class PersonEmerContactFinder implements PeregListFinder<PersonEmerContactDto>{

	@Override
	public String targetCategoryCode() {
		return "CS00015";
	}

	@Override
	public Class<PersonEmerContactDto> dtoClass() {
		return PersonEmerContactDto.class;
	}

	/* (non-Javadoc)
	 * @see nts.uk.shr.pereg.app.find.PeregListFinder#getCtgListleData(nts.uk.shr.pereg.app.find.PeregQuery)
	 */
	@Override
	public List<PeregDto> getCtgListleData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

}
