package nts.uk.screen.at.app.query.knr.knr002.g;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.command.knr.knr002.g.EmpTerminalRegisterInRequestCommand;
import nts.uk.ctx.at.record.app.command.knr.knr002.g.EmpTerminalRegisterInRequestCommandHandler;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;

/**
 * 
 * UKDesign.UniversalK.就業.KNR_就業情報端末.KNR002_就業情報端末の監視.Ｇ：送受信内容の設定ダイアログ.メニュー別OCD.Ｇ：送受信内容の決定
 * @author xuannt
 *
 */
@Stateless
public class DeterminingTransferContents {
	//	就業情報端末のリクエスト一覧Repository.[1] 就業情報端末のリクエスト一覧を取得する
	@Inject
	ConfirmTransmissionMaster confirmTransmissionMaster;
	
	//	就業情報端末のリクエスト一覧に登録するCommandHandler.handle
	@Inject
	EmpTerminalRegisterInRequestCommandHandler empTerminalRegisterInRequestCommandHandler;

	public ConfirmTransmissionMasterDto determine(String empInfoTerCode,
													 boolean sendEmployeeId, 
													 boolean sendWorkType, 
													 boolean sendWorkTime, 
													 boolean overTimeHoliday, 
													 boolean applicationReason, 
													 boolean sendBentoMenu, 
													 boolean timeSetting, 
													 boolean reboot, 
													 boolean stampReceive, 
													 boolean applicationReceive,
													 boolean reservationReceive){
		
		//	1.	送信するマスタにデータが選択されているか確認 ??? Old Data ==> is necessary???
		ConfirmTransmissionMasterDto comfirmination = this.confirmTransmissionMaster
														  .getTimeRecordReqSetting(empInfoTerCode);
		
		//	2. 就業情報端末のリクエスト一覧に登録する(契約コード、就業情報端末コード、就業情報端末のリクエスト一覧)
		this.empTerminalRegisterInRequestCommandHandler
			.handle(new EmpTerminalRegisterInRequestCommand(
					new EmpInfoTerminalCode(empInfoTerCode),
					sendEmployeeId,
					sendWorkType,
					sendWorkTime,
					overTimeHoliday,
					applicationReason,
					sendBentoMenu,
					timeSetting,
					reboot,
					stampReceive,
					applicationReceive,
					reservationReceive));
		
		return comfirmination;
	}
}
