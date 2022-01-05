package nts.uk.screen.at.app.query.knr.knr002.k;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingRepository;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistRepository;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistory;
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
	//	弁当メニュー履歴Repository.getBentoMenu(String companyID, GeneralDate date)
	@Inject
	private BentoMenuHistRepository bentoMenuHistRepository;
	
	/**
	 * @param empInforTerCode
	 * @return
	 */
	public GetBentoMenuDto getBentoMenu(String empInforTerCode) {
		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		String companyId = AppContexts.user().companyId();
		GeneralDate date = GeneralDate.today();
		GetBentoMenuDto dto = new GetBentoMenuDto();
		//	1. get(会社ID、日付): 履歴ID
		Optional<BentoMenuHistory> opBentoMenuHistory = bentoMenuHistRepository.findByCompanyDate(companyId, date);
		if(opBentoMenuHistory.isPresent()) {
			dto.setBentoMenuList(opBentoMenuHistory.get().getMenu().stream().map(e -> new BentoMenuDto(e.getFrameNo(), e.getName().v())).collect(Collectors.toList()));
		} 
		//	2. get*(履歴ID): 枠番、弁当名
		
		//BentoMenu bentoMenu = this.bentoMenuRepository.getBentoMenu(companyId, GeneralDate.today());
		dto.setBentoMenu(this.timeRecordReqSettingRepository
				              .getbentoMenuFrameNumbers(contractCode, new EmpInfoTerminalCode(empInforTerCode)));
		return dto;	
	}

}
