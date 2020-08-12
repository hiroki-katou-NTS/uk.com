package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.service;

import java.io.InputStream;
import java.util.Optional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.dto.TimeRecordSettingInfoDto;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.xml.NRLRemoteDataXml;

/**
 * @author ThanhNX
 *
 *         NRLリモートの受信をオブジェクトに変換する
 */
public class ConvertNRLRemoteReceiveToObjectService {

	private ConvertNRLRemoteReceiveToObjectService() {
	};

	// 変換する
	public static Optional<AtomTask> convertData(Require require, InputStream in) {

		JAXBContext context;
		try {
			context = JAXBContext.newInstance(NRLRemoteDataXml.class);
			Unmarshaller um = context.createUnmarshaller();
			NRLRemoteDataXml frame = (NRLRemoteDataXml) um.unmarshal(in);

			// $タイムレコーダーの設定情報 = #タイムレコードの設定情報.ペイロード分析($フレームxml)
			TimeRecordSettingInfoDto settingInfo = TimeRecordSettingInfoDto.payloadAnalysis(frame.getMac(),
					frame.getPayload());

			// RequireImpl impl = new RequireImpl(empInfoTerminalRepository,
			// timeRecordSetFormatListRepository);
			AtomTask atomTask = ReceiveNRRemoteSettingService.processInfo(require, settingInfo);
			return Optional.of(atomTask);
		} catch (JAXBException e) {
			// Error file xml
			e.printStackTrace();
		}

		return Optional.empty();
	}

	public static interface Require extends ReceiveNRRemoteSettingService.Require {

	}
}
