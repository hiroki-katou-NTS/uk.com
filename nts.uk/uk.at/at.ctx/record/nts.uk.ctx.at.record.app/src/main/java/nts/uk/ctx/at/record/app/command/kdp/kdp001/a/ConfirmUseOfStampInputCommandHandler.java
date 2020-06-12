package nts.uk.ctx.at.record.app.command.kdp.kdp001.a;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.dom.adapter.company.CompanyImport622;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeDataMngInfoImport;
import nts.uk.ctx.at.record.dom.stamp.application.SettingsUsingEmbossing;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditing;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardCreateResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.MakeUseJudgmentResults;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampFunctionAvailableService;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb
 * 
 *         UKDesign.UniversalK.就業.KDP_打刻.KDP001_打刻入力(ポータル).A:打刻入力(ポータル).メニュー別OCD.打刻入力(ポータル)の利用確認を行う
 *
 */
public class ConfirmUseOfStampInputCommandHandler
		extends CommandHandlerWithResult<ConfirmUseOfStampInputCommand, ConfirmUseOfStampInputResult> {

	@Inject
	private StampFunctionAvailableService stampAvailableService;

	@Override
	protected ConfirmUseOfStampInputResult handle(CommandHandlerContext<ConfirmUseOfStampInputCommand> context) {

		StampFunctionAvailableServiceRequireImpl require = new StampFunctionAvailableServiceRequireImpl();

		StampMeans stampMeans = EnumAdaptor.valueOf(context.getCommand().getStampMeans(), StampMeans.class);

		String employeeId = AppContexts.user().employeeId();
		// 1. 判断する(@Require, 社員ID, 打刻手段)
		MakeUseJudgmentResults jugResult = this.stampAvailableService.decide(require, employeeId, stampMeans);
		// not 打刻カード作成結果 empty
		Optional<StampCardCreateResult> cradResultOpt = jugResult.get().cardResult;

		if (cradResultOpt.isPresent()) {

			Optional<AtomTask> atom = cradResultOpt.get().getAtomTask();
			transaction.execute(() -> {
				atom.get().run();
			});
			return new ConfirmUseOfStampInputResult(cradResultOpt.get().getCardNumber());
		}
		return null;

	}

	@AllArgsConstructor
	private class StampFunctionAvailableServiceRequireImpl implements StampFunctionAvailableService.Require {
		@Override
		public List<EmployeeDataMngInfoImport> findBySidNotDel(List<String> sid) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<CompanyImport622> getCompanyNotAbolitionByCid(String cid) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<StampCardEditing> get(String companyId) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<Stamp> get(String contractCode, String stampNumber) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void add(StampCard domain) {
			// TODO Auto-generated method stub

		}

		@Override
		public List<StampCard> getListStampCard(String sid) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<SettingsUsingEmbossing> get() {
			// TODO Auto-generated method stub
			return null;
		}

	}
}
