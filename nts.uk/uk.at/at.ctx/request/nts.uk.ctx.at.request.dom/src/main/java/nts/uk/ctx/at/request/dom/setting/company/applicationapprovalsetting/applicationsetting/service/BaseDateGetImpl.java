package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.service;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.RecordDate;

@Stateless
public class BaseDateGetImpl implements BaseDateGet {

	@Override
	public GeneralDate getBaseDate(Optional<GeneralDate> date) {
		// ドメインモデル「申請設定」．承認ルートの基準日をチェックする ( Domain model "application setting". Check base date of approval root )
		RecordDate recordDate = RecordDate.SYSTEM_DATE;
		if(recordDate == RecordDate.APP_DATE){
			// 申請対象日のパラメータがあるかチェックする ( Check if there is a parameter on the application target date )
			if(date.isPresent()){
				// 基準日 = 申請対象日 ( Base date = application target date )
				return date.get();
			} else {
				// 基準日 = システム日付 ( Base date = system date )
				return GeneralDate.today();
			}
		} else {
			// 基準日 = システム日付 ( Base date = system date )
			return GeneralDate.today();
		}
	}
}
