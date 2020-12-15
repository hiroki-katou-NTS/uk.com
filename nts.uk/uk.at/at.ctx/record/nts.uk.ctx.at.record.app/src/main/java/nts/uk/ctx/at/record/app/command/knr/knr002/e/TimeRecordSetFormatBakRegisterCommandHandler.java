package nts.uk.ctx.at.record.app.command.knr.knr002.e;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatBak;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.repo.TimeRecordSetFormatBakRepository;

/**
 * 
 * @author dungbn
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.就業情報端末.NRリモート.App.Ｅ：バックアップに書き込む.Ｅ：タイムレコード設定フォーマットのバックアップに登録する
 *
 */
@Stateless
public class TimeRecordSetFormatBakRegisterCommandHandler extends CommandHandler<TimeRecordSetFormatBakRegisterCommand> {

	@Inject
	private TimeRecordSetFormatBakRepository repository;
	
	@Override
	protected void handle(CommandHandlerContext<TimeRecordSetFormatBakRegisterCommand> context) {
		
		TimeRecordSetFormatBakRegisterCommand command = context.getCommand();
		TimeRecordSetFormatList timeRecordSetFormatList = command.getTimeRecordSetFormatList();
		
		GeneralDateTime systemDate = GeneralDateTime.now();
		
		// 1: create(タイムレコード設定フォーマットリスト、システム日時)
		TimeRecordSetFormatBak timeRecordSetFormatBak = new TimeRecordSetFormatBak(
				command.getEmpInfoTerminalCode(), timeRecordSetFormatList.getEmpInfoTerName(),
				timeRecordSetFormatList.getRomVersion(), timeRecordSetFormatList.getModelEmpInfoTer(),
				timeRecordSetFormatList.getLstTRSetFormat(), systemDate);
		
		// 2: persist()
		repository.insert(timeRecordSetFormatBak);
	}
}
