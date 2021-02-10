package nts.uk.screen.at.app.query.knr.knr002.c;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetUpdateList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.repo.TimeRecordSetFormatListRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.repo.TimeRecordSetUpdateListRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KNR_就業情報端末.KNR002_就業情報端末の監視.Ｃ：就業情報端末のリモート設定.メニュー別OCD.Ｃ：リモート設定を取得する.Ｃ：リモート設定を取得する
 * @author dungbn
 *
 */
@Stateless
public class GetRemoteSettings {

	@Inject
	private TimeRecordSetFormatListRepository timeRecordSetFormatListRepository;
	
	@Inject
	private TimeRecordSetUpdateListRepository timeRecordSetUpdateListRepository;

	public List<RemoteSettingsDto> getRemoteSettings(String empInfoTerminalCode) {
		
		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		
		// 1: タイムレコード設定フォーマットリストを取得する(契約コード、就業情報端末コード): タイムレコード設定フォーマットリスト
		Optional<TimeRecordSetFormatList> timeRecordSetFormatList = timeRecordSetFormatListRepository.findSetFormat(new EmpInfoTerminalCode(empInfoTerminalCode), contractCode);
		
		// 2:タイムレコード設定更新リストを取得する(パラメータ．契約コード、パラメータ．就業情報端末コード): タイムレコード設定更新
		Optional<TimeRecordSetUpdateList> timeRecordSetUpdateList = timeRecordSetUpdateListRepository.findSettingUpdate(new EmpInfoTerminalCode(empInfoTerminalCode), contractCode);
		
		if (!timeRecordSetFormatList.isPresent()) {
			return Collections.emptyList();
		}
		
		return RemoteSettingsDto.toDto(timeRecordSetFormatList.get(), timeRecordSetUpdateList);
	}
}
