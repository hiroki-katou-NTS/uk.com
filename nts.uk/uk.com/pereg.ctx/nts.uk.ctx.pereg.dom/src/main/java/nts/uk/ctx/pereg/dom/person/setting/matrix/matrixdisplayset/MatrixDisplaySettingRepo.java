/**
 * 
 */
package nts.uk.ctx.pereg.dom.person.setting.matrix.matrixdisplayset;

import java.util.List;
import java.util.Optional;

/**
 * @author hieult
 *
 */
public interface MatrixDisplaySettingRepo {

	Optional<MatrixDisplaySetting> find(String companyID, String userID);

	void update(MatrixDisplaySetting newSetting);

	void insert(MatrixDisplaySetting newSetting);

	void insertAll(List<MatrixDisplaySetting> listSetting);
}
