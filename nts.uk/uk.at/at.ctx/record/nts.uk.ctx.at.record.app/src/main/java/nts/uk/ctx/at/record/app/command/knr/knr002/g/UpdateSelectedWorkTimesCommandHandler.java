package nts.uk.ctx.at.record.app.command.knr.knr002.g;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingSendWorkTimeRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.就業情報端末.NRリモート.App.Ｇ：選択した就業時間帯を送信データにする（Ｊデータ登録）.Ｇ：選択した就業時間帯をUpdateする（Ｊデータ登録）
 * @author xuannt
 *
 */
@Stateless
public class UpdateSelectedWorkTimesCommandHandler extends CommandHandler<UpdateSelectedWorkTimesCommand> {

	//	就業情報端末のリクエスト一覧Repository.[1] 就業情報端末のリクエスト一覧を取得する
	@Inject
	TimeRecordReqSettingRepository timeRecordReqSettingRepository;
	@Inject
	TimeRecordReqSettingSendWorkTimeRepository timeRecordReqSettingSendWorkTimeRepository;
	
	@Override
	protected void handle(CommandHandlerContext<UpdateSelectedWorkTimesCommand> context) {
		UpdateSelectedWorkTimesCommand command = context.getCommand();
		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		EmpInfoTerminalCode terminalCode = command.getTerminalCode();
		List<WorkTimeCode> selectedWorkTimes = command.getSelectedWorkTimes();
		// 1. get*(契約コード、就業情報端末コード)
		Optional<TimeRecordReqSetting> timeRecordReqSetting = this.timeRecordReqSettingRepository
																  .getTimeRecordReqSetting(terminalCode, contractCode);
		if (!timeRecordReqSetting.isPresent())
			return;
		TimeRecordReqSetting timeRecordReqSettingVal = timeRecordReqSetting.get();
		// 2. set(契約コード、就業情報端末コード、選択した就業時間帯コード<List>)
		TimeRecordReqSetting timeRecordReqSettingUpdate = new TimeRecordReqSetting.ReqSettingBuilder(
																timeRecordReqSettingVal.getTerminalCode(),
																timeRecordReqSettingVal.getContractCode(),
																timeRecordReqSettingVal.getCompanyId(),
																timeRecordReqSettingVal.getCompanyCode(),
																timeRecordReqSettingVal.getEmployeeIds(),
																timeRecordReqSettingVal.getBentoMenuFrameNumbers(),
																timeRecordReqSettingVal.getWorkTypeCodes())
														 .workTime(selectedWorkTimes)
														 .build();
		// 3. persist
		this.timeRecordReqSettingSendWorkTimeRepository.delete(timeRecordReqSettingVal);
		this.timeRecordReqSettingSendWorkTimeRepository.insert(timeRecordReqSettingUpdate);
	}

}
