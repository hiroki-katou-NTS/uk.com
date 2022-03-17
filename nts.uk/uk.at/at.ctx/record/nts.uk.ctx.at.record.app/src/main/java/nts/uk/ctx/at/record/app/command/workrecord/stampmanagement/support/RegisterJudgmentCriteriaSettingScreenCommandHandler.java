package nts.uk.ctx.at.record.app.command.workrecord.stampmanagement.support;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.JudCriteriaSameStampOfSupportRepo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.JudgmentCriteriaSameStampOfSupport;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.RangeRegardedSupportStamp;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;

/**
 * 応援同一打刻の判断基準設定の登録を行う
 * @author NWS
 *
 */
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class RegisterJudgmentCriteriaSettingScreenCommandHandler extends CommandHandler<RegisterJudgmentCriteriaSettingScreenCommand> {

	@Inject
	private JudCriteriaSameStampOfSupportRepo repository;
	
	@Override
	protected void handle(CommandHandlerContext<RegisterJudgmentCriteriaSettingScreenCommand> context) {
		RegisterJudgmentCriteriaSettingScreenCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();

		// Step 1: 応援の同一打刻の判断基準
		JudgmentCriteriaSameStampOfSupport jcSameStampOfSupport = repository.get(companyId);
		RangeRegardedSupportStamp stamp = new RangeRegardedSupportStamp(command.getSameStampRanceInMinutes());
		if(jcSameStampOfSupport != null) {
			jcSameStampOfSupport = new JudgmentCriteriaSameStampOfSupport(jcSameStampOfSupport.getCid(), stamp);
			List<JudgmentCriteriaSameStampOfSupport> domains = Arrays.asList(jcSameStampOfSupport);
			repository.update(domains);
		} else {
			jcSameStampOfSupport = new JudgmentCriteriaSameStampOfSupport(companyId, stamp);
			List<JudgmentCriteriaSameStampOfSupport> domains = Arrays.asList(jcSameStampOfSupport);
			repository.insert(domains);
		}
	}

}
