/**
 * 
 */
package nts.uk.ctx.pereg.app.find.person.setting.matrix.matrixdisplayset;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.dom.person.setting.matrix.matrixdisplayset.MatrixDisplaySetting;
import nts.uk.ctx.pereg.dom.person.setting.matrix.matrixdisplayset.MatrixDisplaySettingRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author hieult
 *
 */
@Stateless
public class MatrixDisplaySetFinder {

	@Inject
	private MatrixDisplaySettingRepo repo;

	public Optional<MatrixDisplaySetting> findByKey() {
		String companyID = AppContexts.user().companyId();
		String userID = AppContexts.user().userId();
		Optional<MatrixDisplaySetting> optData = repo.find(companyID, userID);
		return optData;
	}

}
