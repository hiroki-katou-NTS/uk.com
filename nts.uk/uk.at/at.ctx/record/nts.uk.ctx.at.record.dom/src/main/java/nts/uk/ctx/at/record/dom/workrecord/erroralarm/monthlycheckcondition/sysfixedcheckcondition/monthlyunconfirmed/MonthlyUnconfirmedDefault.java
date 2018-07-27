package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.sysfixedcheckcondition.monthlyunconfirmed;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.checkprincipalunconfirm.ValueExtractAlarmWR;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.month.ConfirmationMonth;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.ConfirmationMonthRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.IdentityProcess;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.IdentityProcessRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class MonthlyUnconfirmedDefault implements MonthlyUnconfirmedService {

	@Inject
	private IdentityProcessRepository identityProcessRepo;
	
	@Inject
	private ConfirmationMonthRepository confirmationMonthRepo;
	
	
	@Override
	public Optional<ValueExtractAlarmWR> checkMonthlyUnconfirmed(String employeeID,int yearMonth) {
		String companyID = AppContexts.user().companyId();
		//ドメインモデル「本人確認処理の利用設定」を取得する
		Optional<IdentityProcess> identityProcess = identityProcessRepo.getIdentityProcessById(companyID);
		if(!identityProcess.isPresent()) {
			return Optional.empty();
		}
		//「月の本人確認を利用する」をチェックする  :  利用する場合
		if(identityProcess.get().getUseMonthSelfCK() == 1) {
			//ドメインモデル「月の本人確認」を取得する
			List<ConfirmationMonth> listConfirmationMonth = confirmationMonthRepo.findBySidAndYM(employeeID, yearMonth);
			
			//取得できた場合
			if(!listConfirmationMonth.isEmpty()) {
				return Optional.empty();
			}
			//取得できなかった場合
			GeneralDate date = GeneralDate.fromString(String.valueOf(yearMonth).substring(0, 4) + '-' + String.valueOf(yearMonth).substring(4, 6) + '-' +"01", "yyyy-MM-dd");
			return Optional.of(new ValueExtractAlarmWR(null,
														employeeID,
														date,
														TextResource.localize("KAL010_100"),
														TextResource.localize("KAL010_271"),	
														TextResource.localize("KAL010_272"),
														null));
			
		}
		return Optional.empty();
	}

}
