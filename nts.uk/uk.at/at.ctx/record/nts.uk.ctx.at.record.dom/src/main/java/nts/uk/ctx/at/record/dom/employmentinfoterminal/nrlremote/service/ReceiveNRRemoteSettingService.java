package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.service;

import java.util.Optional;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MacAddress;
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
	public static AtomTask processInfo(Require require, TimeRecordSettingInfoDto input) {

		MacAddress macAdd = new MacAddress(input.getMacAdd());
		ContractCode contractCode = new ContractCode("000000000000");

		// $就業情報端末 = require.就業情報端末Repository.get(タイムレコーダー.MACアドレス, "000000000000")
		Optional<EmpInfoTerminal> empInfoTerOpt = require.getEmpInfoTerWithMac(macAdd, contractCode);

		if (!empInfoTerOpt.isPresent()) {
			return AtomTask.of(() -> {
			});
		}

		return AtomTask.of(() -> {

			require.removeTRSetFormatList(empInfoTerOpt.get().getEmpInfoTerCode(), contractCode);

			// $タイムレコード設定 = #タイムレコード設定フォーマットリスト.変換する(タイムレコーダー)
			// require.タイムレコード設定フォーマットリストRepository.insert($タイムレコード設定)
			require.insert(contractCode, TimeRecordSetFormatList.convert(empInfoTerOpt.get().getEmpInfoTerCode(), input));

		});

	}

	public static interface Require {

		// [R-1]就業情報端末を取得する
		public Optional<EmpInfoTerminal> getEmpInfoTerWithMac(MacAddress macAdd, ContractCode contractCode);

		// [R-2]タイムレコード設定フォーマットリストを削除する
		// TimeRecordSetFormatList
		public void removeTRSetFormatList(EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode);

		// [R-3] タイムレコード設定フォーマットリストをインサートする
		public void insert(ContractCode code, TimeRecordSetFormatList trSetFormat);
	}
}
