package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.AbolitionAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.CurrProcessDate;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.CurrProcessDateRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpTiedProYear;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpTiedProYearRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ProcessInformation;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ProcessInformationRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AddRegisterProcessCommandHandler extends CommandHandler<AddRegisterProcessCommand> {
	@Inject
	private ProcessInformationRepository repoProcessInformation;
	@Inject
	private CurrProcessDateRepository repoCurrProcessDate;
	@Inject
	private EmpTiedProYearRepository repoEmpTiedProYear;
	@Override
	protected void handle(CommandHandlerContext<AddRegisterProcessCommand> context) {
		AddRegisterProcessCommand addCommand = context.getCommand();
		String cid = AppContexts.user().companyId();
		int deprecatedCategory = AbolitionAtr.NOT_ABOLITION.value;
		// ドメインモデル「処理区分基本情報」を取得する
		List<ProcessInformation> dataProcessInformation = repoProcessInformation
				.getProcessInformationByDeprecatedCategory(cid, deprecatedCategory);
		if (!dataProcessInformation.isEmpty()) {
			// 取得した処理区分NOで以下の処理をループする
			for (int i = 0; i < dataProcessInformation.size(); i++) {
				// ドメインモデル「現在処理年月」を更新する
				int processCateNo = dataProcessInformation.get(i).getProcessCateNo();
				int giveCurrTreatYear = 0;
				CurrProcessDate currProcessDate = new CurrProcessDate(cid, processCateNo, giveCurrTreatYear);
				repoCurrProcessDate.update(currProcessDate);
				//ドメインモデル「処理年月に紐づく雇用」を取得する
				Optional<EmpTiedProYear> dataEmpTiedProYear = repoEmpTiedProYear.getEmpTiedProYearById(cid, processCateNo);
			}
		}
	}
}
