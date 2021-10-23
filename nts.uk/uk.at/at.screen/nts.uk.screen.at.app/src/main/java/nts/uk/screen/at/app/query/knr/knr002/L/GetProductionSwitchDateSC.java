package nts.uk.screen.at.app.query.knr.knr002.L;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author dungbn
 *	UKDesign.UniversalK.就業.KNR_就業情報端末.KNR002_就業情報端末の監視.Ｌ：本番切替日の設定.メニュー別OCD.本番切替日の取得する.Ｌ：本番切替日の取得する
 *	本番切替日の取得する
 */
@Stateless
public class GetProductionSwitchDateSC {
	
	@Inject
	private TimeRecordReqSettingRepository timeRecordReqSettingRepository;
	
	public List<GetProductionSwitchDateDto> getProductionSwitchDate(GetProductionSwitchDateInput input) {
		
		String contractCode = AppContexts.user().contractCode();
		List<GetProductionSwitchDateDto> result;
		
		// 1: [モード＝一括]: get*(契約コード): 就業情報端末のリクエスト一覧
		if (input.getMode().equalsIgnoreCase("bulk")) {
			result = timeRecordReqSettingRepository.getListTrRequest(new ContractCode(contractCode))
					.stream()
					.map(e -> GetProductionSwitchDateDto.toDto(e))
					.collect(Collectors.toList());
			return result;
		}
		
		// 2: [モード＝個別]: get(ログイン契約コード、就業情報端末コード): 就業情報端末のリクエスト一覧
		Optional<TimeRecordReqSetting> timeRecordReqSetting = timeRecordReqSettingRepository.getTrRequest(new EmpInfoTerminalCode(input.getEmpInfoTerminalCode()), new ContractCode(contractCode));
		
		result = new ArrayList<GetProductionSwitchDateDto>();
		
		if (timeRecordReqSetting.isPresent()) {
		
			result.add(GetProductionSwitchDateDto.toDto(timeRecordReqSetting.get()));
			
			return result;
		}
		
		return result;
	}
	
}
