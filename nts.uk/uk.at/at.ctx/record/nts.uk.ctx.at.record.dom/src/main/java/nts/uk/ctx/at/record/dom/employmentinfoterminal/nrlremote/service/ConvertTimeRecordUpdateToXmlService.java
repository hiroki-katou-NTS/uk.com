package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.service;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.dto.TimeRecordSettingInfoDto;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;

/**
 * @author ThanhNX
 *
 *         タイムレコーダ更新をXmlに変換する
 */
public class ConvertTimeRecordUpdateToXmlService {
	
	private ConvertTimeRecordUpdateToXmlService() {};

	// [1] 変換する
	public static Optional<String> convertToXml(Require require, ContractCode contractCode, EmpInfoTerminalCode empInfoTerCode) {

		Optional<TimeRecordSettingInfoDto> settingUpdateOpt = SendTimeRecordSetInfoService.send(require,
				contractCode, empInfoTerCode);

		if (!settingUpdateOpt.isPresent())
			return Optional.empty();

		return  Optional.of(TimeRecordSettingInfoDto.createPayLoad(settingUpdateOpt.get()));

	}

	public static interface Require extends SendTimeRecordSetInfoService.Require {

	}
}
