/**
 * 
 */
package nts.uk.ctx.pereg.app.find.person.setting.matrix.personinfomatrixitem;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.dom.person.setting.matrix.personinfomatrixitem.PersonInfoMatrixData;
import nts.uk.ctx.pereg.dom.person.setting.matrix.personinfomatrixitem.PersonInfoMatrixItem;
import nts.uk.ctx.pereg.dom.person.setting.matrix.personinfomatrixitem.PersonInfoMatrixItemRepo;

/**
 * @author hieult
 *
 */
@Stateless
public class DisplayItemColumnSetFinder {

	@Inject
	private PersonInfoMatrixItemRepo repo;

	public List<PersonInfoMatrixItem> get(String pInfoCategoryID) {
		return repo.findByCategoryID(pInfoCategoryID);
	}

	public List<PersonInfoMatrixData> getData(String pInfoCategoryID) {
		return repo.findInfoData(pInfoCategoryID);
	}
	
	public List<PersonInfoMatrixData> getData(DisplayItemParam params) {
		return repo.findInfoData(params.getCategoryId(), params.getItemIds());
	}
}
