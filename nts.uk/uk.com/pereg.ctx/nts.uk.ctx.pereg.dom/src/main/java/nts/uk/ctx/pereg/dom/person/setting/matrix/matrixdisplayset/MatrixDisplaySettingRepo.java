/**
 * 
 */
package nts.uk.ctx.pereg.dom.person.setting.matrix.matrixdisplayset;

import java.util.Optional;


/**
 * @author hieult
 *
 */
public interface MatrixDisplaySettingRepo {

	  Optional<MatrixDisplaySetting> find(String companyID, String userID);
	  
	  void update(MatrixDisplaySetting newSetting);
	  
}
