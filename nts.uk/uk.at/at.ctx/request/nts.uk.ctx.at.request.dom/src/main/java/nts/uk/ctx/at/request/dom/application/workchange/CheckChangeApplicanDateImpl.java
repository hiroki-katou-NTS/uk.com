package nts.uk.ctx.at.request.dom.application.workchange;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
@Stateless
public class CheckChangeApplicanDateImpl implements ICheckChangeApplicanDate{

	@Override
	public void CheckChangeApplicationDate(GeneralDate startDate, GeneralDate endDate) {
		if (startDate == null || endDate == null) {
			return;
		}
		//申請日付開始日を基準に共通アルゴリズム「申請日を変更する」を実行する
		// TODO: 申請日を変更する 
		//申請日付分　（開始日～終了日）
		//ループ処理を実行する
		while(startDate.beforeOrEquals(endDate)){
			//TODO: 共通アルゴリズム「申請日を変更する」を実行する
			//基準日　＝　基準日　＋　１
			startDate = startDate.addDays(1);
		}
	}

}
