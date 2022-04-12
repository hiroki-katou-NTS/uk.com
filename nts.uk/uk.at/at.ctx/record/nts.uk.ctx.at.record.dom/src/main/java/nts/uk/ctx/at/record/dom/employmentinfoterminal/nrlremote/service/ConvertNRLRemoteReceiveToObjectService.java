package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.service;

import java.util.Optional;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.dto.TimeRecordSettingInfoDto;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;

/**
 * @author ThanhNX
 *
 *         NRLリモートの受信をオブジェクトに変換する
 */
public class ConvertNRLRemoteReceiveToObjectService {

	private ConvertNRLRemoteReceiveToObjectService() {
	};

	// 変換する
	public static Optional<AtomTask> convertData(Require require, ContractCode contractCode,
			EmpInfoTerminalCode empInfoTerCode, String macAddr, String payload) {

		// $タイムレコーダーの設定情報 = #タイムレコードの設定情報.ペイロード分析($フレームxml)
		TimeRecordSettingInfoDto settingInfo = TimeRecordSettingInfoDto.payloadAnalysis(macAddr, payload);

		// RequireImpl impl = new RequireImpl(empInfoTerminalRepository,
		// timeRecordSetFormatListRepository);
		AtomTask atomTask = ReceiveNRRemoteSettingService.processInfo(require, contractCode, empInfoTerCode,
				settingInfo);
		return Optional.of(atomTask);

	}

	public static interface Require extends ReceiveNRRemoteSettingService.Require {

	}
}
