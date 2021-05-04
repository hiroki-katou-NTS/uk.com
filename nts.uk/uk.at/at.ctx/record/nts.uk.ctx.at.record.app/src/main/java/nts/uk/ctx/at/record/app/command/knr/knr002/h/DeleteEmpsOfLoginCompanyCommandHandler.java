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
 * @author xuannt
 * 	UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.就業情報端末.NRリモート.App.Ｈ：選択した社員を送信データにする.H：ログイン会社の社員を削除する
 *
 */
@Stateless
public class DeleteEmpsOfLoginCompanyCommandHandler extends CommandHandler<DeleteEmpsOfLoginCompanyCommand>{
	@Inject
	TimeRecordReqSettingRepository timeRecordReqSettingRepository;
	@Inject
	TimeRecordReqSettingSendEmployeeRepository timeRecordReqSettingSendEmployeeRepository;
	
	@Override
	protected void handle(CommandHandlerContext<DeleteEmpsOfLoginCompanyCommand> context) {
		//	1. 指定社員IDListを削除する(契約コード、就業端末コード、ログイン会社の社員ID(List))
		DeleteEmpsOfLoginCompanyCommand command = context.getCommand();
		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		EmpInfoTerminalCode empInfoTerCode = command.getTerminalCode();
		List<EmployeeId> loginCompanyEmpList = command.getLoginCompanyEmpIds();
		// get*(契約コード、就業情報端末コード)
		Optional<TimeRecordReqSetting> timeRecordSetting = this.timeRecordReqSettingRepository
															   .getTimeRecordEmployee(empInfoTerCode, contractCode);
		if (!timeRecordSetting.isPresent())
			return;
		TimeRecordReqSetting timeRecordSettingValue = timeRecordSetting.get();
		if(null == loginCompanyEmpList || loginCompanyEmpList.isEmpty())
			return;
		// 2. set(契約コード、就業情報端末コード、画面で指定した社員<List>)
		TimeRecordReqSetting timeRecordSettingUpdate = new TimeRecordReqSetting.ReqSettingBuilder(
															timeRecordSettingValue.getTerminalCode(),
															timeRecordSettingValue.getContractCode(),
															timeRecordSettingValue.getCompanyId(),
															timeRecordSettingValue.getCompanyCode(),
															loginCompanyEmpList,
															Collections.emptyList(),
															Collections.emptyList())
														.build();
		// 3. persist()
		this.timeRecordReqSettingSendEmployeeRepository.delete(timeRecordSettingUpdate);
	}
}
