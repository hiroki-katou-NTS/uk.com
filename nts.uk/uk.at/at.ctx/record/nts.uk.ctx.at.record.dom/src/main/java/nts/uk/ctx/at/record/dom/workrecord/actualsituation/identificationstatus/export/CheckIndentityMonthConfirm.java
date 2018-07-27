package nts.uk.ctx.at.record.dom.workrecord.actualsituation.identificationstatus.export;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.IdentityProcessUseSet;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.month.ConfirmationMonth;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.ConfirmationMonthRepository;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.IdentityProcessUseSetRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author thanhnx 対象月の本人確認が済んでいるかチェックする
 *
 */
@Stateless
public class CheckIndentityMonthConfirm {
	
	@Inject
	private IdentityProcessUseSetRepository identityProcessUseSetRepository;
	
	@Inject
	private ConfirmationMonthRepository confirmationMonthRepository;

	public boolean checkIndentityMonth(String employeeId, GeneralDate date) {
		String companyId = AppContexts.user().companyId();
		Optional<IdentityProcessUseSet> indentityOpt = identityProcessUseSetRepository.findByKey(companyId);
		if (indentityOpt.isPresent() && !indentityOpt.get().isUseIdentityOfMonth())
			return true;
		List<ConfirmationMonth> confirmMonths = confirmationMonthRepository.findBySidAndYM(employeeId,
				YearMonth.of(date.year(), date.month()).v());
		return !confirmMonths.isEmpty();
	}
}
