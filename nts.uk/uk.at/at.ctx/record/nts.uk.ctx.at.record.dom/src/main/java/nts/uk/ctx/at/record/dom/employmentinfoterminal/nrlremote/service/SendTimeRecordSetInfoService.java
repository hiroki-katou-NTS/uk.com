package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.service;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MacAddress;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetUpdateList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.dto.TimeRecordSettingInfoDto;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;

/**
 * @author ThanhNX
 *
 *         タイムレコードの設定情報を送信する
 */
public class SendTimeRecordSetInfoService {

	private SendTimeRecordSetInfoService() {
	};

	// 送信する
	public static Optional<TimeRecordSettingInfoDto> send(Require require, MacAddress macAdd,
			ContractCode contractCode) {

		// $就業情報端末 = require.就業情報端末Repository.get(MACアドレス, 契約コード)
		Optional<EmpInfoTerminal> empTerOpt = require.getEmpInfoTerWithMac(macAdd, contractCode);
		if (!empTerOpt.isPresent())
			return Optional.empty();

		// $タイムレコード設定 = require.タイムレコード設定更新リストRepository.find($就業情報端末.コード)
		Optional<TimeRecordSetUpdateList> setUpdateOpt = require.findSettingUpdate(empTerOpt.get().getEmpInfoTerCode(),
				contractCode);
		// $設定フォーマット = require.タイムレコード設定フォーマットリストを取得する($就業情報端末.コード);
		Optional<TimeRecordSetFormatList> setFormatOpt = require.findSetFormat(empTerOpt.get().getEmpInfoTerCode(),
				contractCode);
		if (!setUpdateOpt.isPresent() || !setFormatOpt.isPresent())
			return Optional.empty();

		// return Optional.of(#タイムレコードの設定情報.タイムレコードの設定情報を作る($設定フォーマット, $タイムレコード設定))
		return Optional.of(TimeRecordSettingInfoDto.create(setFormatOpt.get(), setUpdateOpt.get()));
	}

	public static interface Require {

		// [R-1]就業情報端末を取得する
		public Optional<EmpInfoTerminal> getEmpInfoTerWithMac(MacAddress maccAdd, ContractCode contractCode);

		// [R-2] タイムレコード設定更新リストを取得する
		Optional<TimeRecordSetUpdateList> findSettingUpdate(EmpInfoTerminalCode empInfoTerCode,
				ContractCode contractCode);

		// [R-3] タイムレコード設定フォーマットリストを取得する
		Optional<TimeRecordSetFormatList> findSetFormat(EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode);

	}
}
