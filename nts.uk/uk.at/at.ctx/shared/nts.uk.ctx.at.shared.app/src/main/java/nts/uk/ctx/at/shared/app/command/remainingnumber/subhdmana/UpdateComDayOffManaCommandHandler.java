package nts.uk.ctx.at.shared.app.command.remainingnumber.subhdmana;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.AddSubHdManagementService;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.ComDayOffManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author sang.nv
 *
 */
@Stateless
public class UpdateComDayOffManaCommandHandler
		extends CommandHandlerWithResult<CompensatoryDayOffManaDataCommand, List<String>> {

	@Inject
	private ComDayOffManaDataRepository comDayRepo;
	
	@Inject
	private AddSubHdManagementService addSubHdManaService;

	@Override
	protected List<String> handle(CommandHandlerContext<CompensatoryDayOffManaDataCommand> context) {
		return this.updateComDayOffManaProcess(context.getCommand());
	}

	/**
	 * Ｍ．代休管理データの修正（代休設定）登録処理
	 * 
	 * @return
	 */
	private List<String> updateComDayOffManaProcess(CompensatoryDayOffManaDataCommand command) {
		// アルゴリズム「代休（年月日）チェック処理」を実行する
		List<String> errorList = this.addSubHdManaService.checkDateHoliday(Optional.empty(), command.getDayOffDate(), Optional.empty(), command.getClosureId(), false,Optional.empty(), false );

		if (!errorList.isEmpty()) {
			return errorList;
		}

		// ドメインモデル「代休管理データ」の選択データを更新する
		CompensatoryDayOffManaData domain = new CompensatoryDayOffManaData(command.getComDayOffID(),
				AppContexts.user().companyId(), command.getEmployeeId(), command.getUnknownDate() == 1, command.getDayOffDate(),
				command.getRequireDays(), 0, command.getRemainDays(), 0);
		comDayRepo.update(domain);

		//結果情報に「エラーなし」を返す
		return Collections.emptyList();
	}
}
