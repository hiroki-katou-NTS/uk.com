package nts.uk.screen.at.app.query.knr.knr002.g;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * UKDesign.UniversalK.就業.KNR_就業情報端末.KNR002_就業情報端末の監視.Ｇ：送受信内容の設定ダイアログ.メニュー別OCD.Ｇ：送信予定の勤務種類を取得する（Ｉデータ取得）
 * @author xuannt
 *
 */
@Stateless
public class GetWorkType {
	//	勤務種類Repository.findWorkByDeprecate
	@Inject
	private WorkTypeRepository workTypeRepository;
	//	就業情報端末のリクエスト一覧Repository.[5]勤務種類コードListを取得する
	@Inject
	private TimeRecordReqSettingRepository timeRecordReqSettingRepository;
	
	/**
	 * @param empInforTerCode
	 * @return
	 */
	public GetWorkTypeDto getWorkTypes(String empInforTerCode) {
		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		String companyId = AppContexts.user().companyId();
		//	1. get*(会社ID、廃止区分＝false): 勤務種類<List>
		List<WorkType> posibleWorkTypes = this.workTypeRepository.findWorkByDeprecate(companyId, 0);
		//	2.    勤務種類コードListを取得する(契約コード、就業情報端末コード): 勤務種類コード<List>
		List<WorkTypeCode> workTypeCodeList = this.timeRecordReqSettingRepository.
													getWorkTypeCodeList(contractCode, new EmpInfoTerminalCode(empInforTerCode));
																 
		GetWorkTypeDto dto = new GetWorkTypeDto();
		dto.setPosibleWorkTypes(posibleWorkTypes
							   .stream().map(e -> e.getWorkTypeCode().v()).collect(Collectors.toList()));
		dto.setWorkTypeCodes(workTypeCodeList
							   .stream().map(e -> e.v()).collect(Collectors.toList()));
		return dto;
	}
}
