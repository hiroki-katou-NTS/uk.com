package nts.uk.ctx.at.record.app.command.knr.knr002.g;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingSendWorkTypeRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.就業情報端末.NRリモート.App.Ｇ：選択した勤務種類を送信データにする（Ｉデータ登録）.Ｇ：選択した勤務種類を登録する（Ｉデータ登録） 
 * @author xuannt
 *
 */
@Stateless
public class RegisterSelectedWorkTypesCommandHandler extends CommandHandler<RegisterSelectedWorkTypesCommand> {
	//	就業情報端末のリクエスト一覧Repository.[1] 就業情報端末のリクエスト一覧を取得する
	@Inject
	TimeRecordReqSettingRepository timeRecordReqSettingRepository;
	@Inject
	TimeRecordReqSettingSendWorkTypeRepository timeRecordReqSettingSendWorkTypeRepository;

	@Override
	protected void handle(CommandHandlerContext<RegisterSelectedWorkTypesCommand> context) {
		RegisterSelectedWorkTypesCommand command = context.getCommand();
		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		EmpInfoTerminalCode terminalCode = command.getTerminalCode();
		List<WorkTypeCode> selectedWorkTypes = command.getSelectedWorkTypes();
		// 1. get*(契約コード、就業情報端末コード)
		Optional<TimeRecordReqSetting> timeRecordReqSetting = this.timeRecordReqSettingRepository
																  .getTimeRecordWorkType(terminalCode, contractCode);
		if (!timeRecordReqSetting.isPresent())
			return;
		TimeRecordReqSetting timeRecordReqSettingVal = timeRecordReqSetting.get();
		// 2. set(契約コード、就業情報端末コード、選択した弁当メニュー枠番<List>)
		TimeRecordReqSetting timeRecordReqSettingUpdate = new TimeRecordReqSetting.ReqSettingBuilder(
																timeRecordReqSettingVal.getTerminalCode(),
																timeRecordReqSettingVal.getContractCode(),
																timeRecordReqSettingVal.getCompanyId(),
																timeRecordReqSettingVal.getCompanyCode(),
																Collections.emptyList(),
																Collections.emptyList(),
																selectedWorkTypes)
														 .build();
		// 3. persist
		this.timeRecordReqSettingSendWorkTypeRepository.delete(timeRecordReqSettingVal);
		this.timeRecordReqSettingSendWorkTypeRepository.insert(timeRecordReqSettingUpdate);

	}

}
