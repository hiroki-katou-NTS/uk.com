package nts.uk.ctx.at.record.app.command.knr.knr002.h;

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
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingSendEmployeeRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.就業情報端末.NRリモート.App.Ｈ：選択した社員を送信データにする.Ｈ：送信データを選択した社員にUpdateする
 * 
 * @author xuannt
 *
 */
@Stateless
public class UpdateSelectedEmployeeCommandHandler extends CommandHandler<UpdateSelectedEmployeeCommand> {
	//	就業情報端末のリクエスト一覧Repository.[1] 就業情報端末のリクエスト一覧を取得する
	@Inject
	TimeRecordReqSettingRepository timeRecordReqSettingRepository;
	@Inject
	TimeRecordReqSettingSendEmployeeRepository timeRecordReqSettingSendEmployeeRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateSelectedEmployeeCommand> context) {
		UpdateSelectedEmployeeCommand command = context.getCommand();
		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		EmpInfoTerminalCode empInfoTerCode = command.getTerminalCode();
		List<EmployeeId> employeeID = command.getSelectedEmployeeIDs();
		// 1. get*(契約コード、就業情報端末コード、)
		Optional<TimeRecordReqSetting> timeRecordSetting = this.timeRecordReqSettingRepository
															   .getTimeRecordEmployee(empInfoTerCode, contractCode);
		if (!timeRecordSetting.isPresent())
			return;
		TimeRecordReqSetting timeRecordSettingValue = timeRecordSetting.get();
		// 2. set(契約コード、就業情報端末コード、画面で指定した社員<List>)
		TimeRecordReqSetting timeRecordSettingUpdate = new TimeRecordReqSetting.ReqSettingBuilder(
															timeRecordSettingValue.getTerminalCode(),
															timeRecordSettingValue.getContractCode(),
															timeRecordSettingValue.getCompanyId(),
															timeRecordSettingValue.getCompanyCode(),
															employeeID,
															Collections.emptyList(),
															Collections.emptyList())
														.build();
		// 3. persist()
		this.timeRecordReqSettingSendEmployeeRepository.insert(timeRecordSettingUpdate);
	}

}
