package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.service;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.dto.TimeRecordSettingInfoDto;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;

/**
 * @author ThanhNX
 *
 *         NRリモート設定を受信する
 */
public class ReceiveNRRemoteSettingService {

	private ReceiveNRRemoteSettingService() {

	}

	// 情報処理
	public static AtomTask processInfo(Require require, ContractCode contractCode, EmpInfoTerminalCode empInfoTerCode,
			TimeRecordSettingInfoDto input) {

		return AtomTask.of(() -> {

			require.removeTRSetFormatList(empInfoTerCode, contractCode);

			// $タイムレコード設定 = #タイムレコード設定フォーマットリスト.変換する(タイムレコーダー)
			// require.タイムレコード設定フォーマットリストRepository.insert($タイムレコード設定)
			require.insert(contractCode, TimeRecordSetFormatList.convert(empInfoTerCode, input));

		});

	}

	public static interface Require {

		// [R-2]タイムレコード設定フォーマットリストを削除する
		// TimeRecordSetFormatList
		public void removeTRSetFormatList(EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode);

		// [R-3] タイムレコード設定フォーマットリストをインサートする
		public void insert(ContractCode code, TimeRecordSetFormatList trSetFormat);
	}
}
