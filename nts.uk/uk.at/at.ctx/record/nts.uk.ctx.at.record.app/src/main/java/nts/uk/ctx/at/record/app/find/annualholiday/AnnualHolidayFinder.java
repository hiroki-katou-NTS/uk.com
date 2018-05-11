package nts.uk.ctx.at.record.app.find.annualholiday;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;

@Stateless
public class AnnualHolidayFinder {

	public AnnualHolidayDto startPage(int selectionMode, GeneralDate refDate, List<String> selectedIDs) {
		// アルゴリズム「3.年休取得日一覧の作成」を実行する
		// Imported（申請承認）「年休残数情報取得」を実行する
		return null;
	}

}
