package nts.uk.screen.at.app.dailyperformance.correction.error;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.screen.at.app.dailyperformance.correction.dto.DPErrorDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.OperationOfDailyPerformanceDto;

@Stateless
public class ShowDialogError {

	// エラーアラーム参照を起動するかチェックする
	public boolean showDialogError(List<DPErrorDto> lstError, Boolean showError, OperationOfDailyPerformanceDto settingMaster) {
		if (lstError.isEmpty())
			return false;
		if (showError != null)
			return showError;

		return settingMaster == null ? false : settingMaster.isShowError();
	}

}
