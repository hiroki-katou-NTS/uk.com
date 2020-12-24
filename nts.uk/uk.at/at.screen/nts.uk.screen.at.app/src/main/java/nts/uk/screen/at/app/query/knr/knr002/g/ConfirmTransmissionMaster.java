package nts.uk.screen.at.app.query.knr.knr002.g;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.shr.com.context.AppContexts;

/**
 *
 * UKDesign.UniversalK.就業.KNR_就業情報端末.KNR002_就業情報端末の監視.Ｇ：送受信内容の設定ダイアログ.メニュー別OCD.Ｇ：送信マスタの確認l
 * 
 * @author xuannt
 *
 */
@Stateless
public class ConfirmTransmissionMaster {

	// 就業情報端末のリクエスト一覧Repository.[1] 就業情報端末のリクエスト一覧を取得する
	@Inject
	TimeRecordReqSettingRepository timeRecordReqSettingRepository;

	// 1. get*(契約コード、就業情報端末コード): 就業情報端末のリクエスト一覧
	public ConfirmTransmissionMasterDto getTimeRecordReqSetting(EmpInfoTerminalCode empInforTerCode) {
		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		Optional<TimeRecordReqSetting> timeRecordReqSetting = this.timeRecordReqSettingRepository
																  .getTimeRecordReqSetting(empInforTerCode, contractCode);
		ConfirmTransmissionMasterDto dto = new ConfirmTransmissionMasterDto();
		if (!timeRecordReqSetting.isPresent())
			return dto;
		dto.setTimeRecordReqSetting(timeRecordReqSetting.get());
		return dto;
	}
}
