/**
 * 
 */
package nts.uk.ctx.pereg.dom.person.setting.matrix.personinfomatrixitem;

import java.util.List;
import java.util.Optional;

/**
 * @author hieult
 *
 */
public interface PersonInfoMatrixItemRepo {

	Optional<PersonInfoMatrixItem> findbyKey(String pInfoCategoryID, String pInfoDefiID);

	void update(PersonInfoMatrixItem newSetting);

	List<PersonInfoMatrixItem> findByCategoryID(String pInfoCategoryID);

	void save(PersonInfoMatrixItem newSetting);

	void insertAll(List<PersonInfoMatrixItem> listNewSetting);

	List<PersonInfoMatrixData> findInfoData(String pInfoCategoryID);
	
	List<PersonInfoMatrixData> findInfoData(String categoryId, List<String> itemIds);
}
