/**
 * 
 */
package nts.uk.ctx.pereg.dom.person.setting.matrix.personinfomatrixitem;

import java.util.Optional;

/**
 * @author hieult
 *
 */
public interface PersonInfoMatrixItemRepo {
        
	Optional<PersonInfoMatrixItem> findbyKey(String pInfoCategoryID , String pInfoDefiID);
    
	void update (PersonInfoMatrixItem newSetting);
}
