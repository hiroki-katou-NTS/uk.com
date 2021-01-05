package nts.uk.screen.at.app.query.knr.knr002.g;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * UKDesign.UniversalK.就業.KNR_就業情報端末.KNR002_就業情報端末の監視.Ｇ：送受信内容の設定ダイアログ.メニュー別OCD.Ｇ：送信予定の就業時間帯を取得する（Ｊデータ取得）
 * @author xuannt
 *
 */
@Stateless
public class GetWorkTime {
	//	WorkTimeSettingRepository.findByCompanyId(companyId)
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;
	//	就業情報端末のリクエスト一覧Repository.[6]就業時間帯コードListを取得する
	@Inject
	private TimeRecordReqSettingRepository timeRecordReqSettingRepository;
	
	/**
	 * @param empInforTerCode
	 * @return
	 */
	public GetWorkTimeDto getWorkTimes(String empInforTerCode) {
		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		String companyId = AppContexts.user().companyId();
		GetWorkTimeDto dto = new GetWorkTimeDto();
		//	1. get*(会社ID、廃止区分): 就業時間帯の設定<List>
		List<WorkTimeSetting> posibleWorkTimes = this.workTimeSettingRepository.findActiveItems(companyId);
		//	2. 就業時間帯コードを取得する(契約コード、就業情報端末コード): 就業時間帯コード<List>
		List<WorkTimeCode> workTimes = this.timeRecordReqSettingRepository.
											getWorkTimeCodeList(contractCode, new EmpInfoTerminalCode(empInforTerCode));
		dto.setPosibleWorkTimes(posibleWorkTimes
							    .stream().map(e -> e.getWorktimeCode().v()).collect(Collectors.toList()));
		dto.setWorkTimeCodes(workTimes
				   			.stream().map(e -> e.v()).collect(Collectors.toList()));
		return dto;
		
	}

}
