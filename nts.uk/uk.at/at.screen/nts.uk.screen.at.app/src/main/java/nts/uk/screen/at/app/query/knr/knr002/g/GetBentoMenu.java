package nts.uk.screen.at.app.query.knr.knr002.g;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * UKDesign.UniversalK.就業.KNR_就業情報端末.KNR002_就業情報端末の監視.Ｋ：予約選択ダイアログ.メニュー別OCD.Ｋ：送信予定の弁当メニュー枠番を取得する
 * @author xuannt
 *
 */
@Stateless
public class GetBentoMenu {
	//	就業情報端末のリクエスト一覧Repository.[7]弁当メニュー枠番Listを取得する
	@Inject
	private TimeRecordReqSettingRepository timeRecordReqSettingRepository;
	
	public GetBentoMenuDto getBentoMenu(String empInforTerCode) {
		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		GetBentoMenuDto dto = new GetBentoMenuDto();
		dto.setBentoMenu(this.timeRecordReqSettingRepository
				              .getbentoMenuFrameNumbers(contractCode, new EmpInfoTerminalCode(empInforTerCode)));
		return dto;	
	}

}
