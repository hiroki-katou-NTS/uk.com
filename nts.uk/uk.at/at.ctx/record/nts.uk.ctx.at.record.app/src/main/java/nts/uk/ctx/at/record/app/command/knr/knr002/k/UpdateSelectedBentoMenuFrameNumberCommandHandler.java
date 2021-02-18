package nts.uk.ctx.at.record.app.command.knr.knr002.k;

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
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingSendReservationRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.就業情報端末.NRリモート.App.Ｋ：選択した弁当メニュー枠番を送信データにする.Ｋ：選択した弁当メニュー枠番をUpdateする
 * @author xuannt
 *
 */
@Stateless
public class UpdateSelectedBentoMenuFrameNumberCommandHandler extends CommandHandler<UpdateSelectedBentoMenuFrameNumberCommand>{

	//	就業情報端末のリクエスト一覧Repository.[1]  就業情報端末のリクエスト一覧を取得する
	@Inject
	TimeRecordReqSettingRepository timeRecordReqSettingRepository;
	
	@Inject
	TimeRecordReqSettingSendReservationRepository timeRecordReqSettingSendReservationRepository;
	
	@Override
	protected void handle(CommandHandlerContext<UpdateSelectedBentoMenuFrameNumberCommand> context) {
		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		UpdateSelectedBentoMenuFrameNumberCommand command = context.getCommand();
		EmpInfoTerminalCode terminalCode = command.getTerminalCode();
		List<Integer> selectedBentoMenuFrameNumbers = command.getSelectedBentoMenuFrameNumbers();
		//	1. get*(契約コード、就業情報端末コード)
		Optional<TimeRecordReqSetting> timeRecordReqSetting = this.timeRecordReqSettingRepository
																  .getTimeRecordReservation(terminalCode, contractCode);
		if(!timeRecordReqSetting.isPresent())
			return;
		TimeRecordReqSetting timeRecordReqSettingVal = timeRecordReqSetting.get();
		//	2. set(契約コード、就業情報端末コード、選択した弁当メニュー枠番<List>)
		TimeRecordReqSetting timeRecordReqSettingUpdate = new TimeRecordReqSetting.ReqSettingBuilder(
																	timeRecordReqSettingVal.getTerminalCode(),
																	timeRecordReqSettingVal.getContractCode(),
																	timeRecordReqSettingVal.getCompanyId(),
																	timeRecordReqSettingVal.getCompanyCode(),
																	Collections.emptyList(),
																	selectedBentoMenuFrameNumbers,
																	Collections.emptyList())
														  .build();
		//	3. persist
		this.timeRecordReqSettingSendReservationRepository.delete(timeRecordReqSettingVal);
		this.timeRecordReqSettingSendReservationRepository.insert(timeRecordReqSettingUpdate);
	}
}
