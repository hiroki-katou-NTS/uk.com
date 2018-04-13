package nts.uk.ctx.at.record.dom.workrecord.manageactualsituation.identity.monthly;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.Identification;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.IdentificationRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.IdentityProcess;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.IdentityProcessRepository;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class IdentityConfirmProcess {

	@Inject
	IdentityProcessRepository identityProcessRepo;
	@Inject
	IdentificationRepository identificationRepository;
	/**
	 * 対象日の本人確認が済んでいるかチェックする
	 * @param employeeId: 社員ID
	 * @param lstDate: 対象日一覧：List＜年月日＞
	 * @return 対象日一覧の確認が済んでいる：boolean
	 */
	public boolean identityConfirmCheck(String cId, String employeeId, DatePeriod duration){
		//対応するドメインモデル「本人確認処理の利用設定」を取得する
		Optional<IdentityProcess> identityOp = identityProcessRepo.getIdentityProcessById(cId);
		if(!identityOp.isPresent())
			return false;
		//取得したドメインモデル「本人確認処理の利用設定．日の本人確認を利用する」チェックする
		if(identityOp.get().getUseDailySelfCk() == 0){
			//利用しない場合
			return false;
		}
		//対応するドメインモデル「日の本人確認」がすべて存在するかチェックする		
		return checkConfirm(employeeId, duration.start(), duration.end());
	}
	
	public boolean checkConfirm(String employeeID,GeneralDate startDate,GeneralDate endDate) {
		//ドメインモデル「日の本人確認」を取得する
		List<Identification> listIdentification = identificationRepository.findByEmployeeID(employeeID, startDate, endDate);
		//取得した「日の本人確認」をもとにListを作成する
		GeneralDate date = GeneralDate.localDate(startDate.localDate());
		while(date.before(endDate)) {
			boolean checkExist = false;
			//対応するドメインモデル「日の本人確認」がすべて存在するかチェックする
			for(Identification identification : listIdentification) {
				if(date.equals(identification.getIndentificationYmd())){
					checkExist = true;
					continue;
				}
			}
			if(!checkExist)
				return false;
			date = date.addDays(1);
		}
		return false;
	}
}
