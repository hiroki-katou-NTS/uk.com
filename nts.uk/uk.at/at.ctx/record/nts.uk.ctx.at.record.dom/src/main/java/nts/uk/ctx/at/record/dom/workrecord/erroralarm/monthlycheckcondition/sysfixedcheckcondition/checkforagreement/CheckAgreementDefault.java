package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.sysfixedcheckcondition.checkforagreement;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.checkprincipalunconfirm.ValueExtractAlarmWR;

@Stateless
public class CheckAgreementDefault implements CheckAgreementService {

	@Override
	public Optional<ValueExtractAlarmWR> checkAgreement(String employeeID, int yearMonth) {
		//実績データに埋まっている「36協定エラー状態」を取得する
		
		return null;
	}

}
