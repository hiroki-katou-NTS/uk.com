package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.service;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MacAddress;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.dto.TimeRecordSettingInfoDto;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.xml.NRLRemoteDataXml;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;

/**
 * @author ThanhNX
 *
 *         タイムレコード更新をXmlに変換する
 */
public class ConvertTimeRecordUpdateToXmlService {
	
	private ConvertTimeRecordUpdateToXmlService() {};

	// [1] 変換する
	public static NRLRemoteDataXml convertToXml(Require require, String macAdd) {

		Optional<TimeRecordSettingInfoDto> settingUpdateOpt = SendTimeRecordSetInfoService.send(require,
				new MacAddress(macAdd), new ContractCode("000000000000"));

		if (!settingUpdateOpt.isPresent())
			return null;

		return new NRLRemoteDataXml("", TimeRecordSettingInfoDto.createPayLoad(settingUpdateOpt.get()));

	}

	public static interface Require extends SendTimeRecordSetInfoService.Require {

	}
}
