package nts.uk.screen.at.app.query.knr.knr002.e;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormat;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.repo.TimeRecordSetFormatBakRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author dungbn
 * UKDesign.UniversalK.就業.KNR_就業情報端末.KNR002_就業情報端末の監視.Ｅ：就業情報端末のバックアップ.メニュー別OCD.Ｅ：バックアップの内容の取得
 *
 */
@Stateless
public class GetBackupContent {

	@Inject
	private TimeRecordSetFormatBakRepository repository;
	
	public List<BackupContentDto> handle(String code) {
		
		// 1: タイムレコード設定フォーマットを 取得する(契約コード、就業情報端末コード): タイムレコード設定フォーマット
		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		EmpInfoTerminalCode empInfoTerCode = new EmpInfoTerminalCode(code);
		List<TimeRecordSetFormat> listTimeRecordSetFormat = repository.getTimeRecordSetFormat(contractCode, empInfoTerCode);
		
		return listTimeRecordSetFormat.stream().map(e -> BackupContentDto.toDto(e)).collect(Collectors.toList());
	}
}
