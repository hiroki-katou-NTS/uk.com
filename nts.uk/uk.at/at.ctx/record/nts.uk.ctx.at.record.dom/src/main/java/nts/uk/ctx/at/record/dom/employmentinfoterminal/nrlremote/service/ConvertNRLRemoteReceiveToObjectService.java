package nts.uk.ctx.at.function.app.nrlremote;

import java.io.InputStream;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import lombok.AllArgsConstructor;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.function.app.nrlremote.xml.NRLRemoteDataXml;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MacAddress;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerminalRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.dto.TimeRecordSettingInfoDto;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.repo.TimeRecordSetFormatListRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.service.ReceiveNRRemoteSettingService;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;

/**
 * @author ThanhNX
 *
 *         NRLリモートの受信をオブジェクトに変換する
 */
@Stateless
public class ConvertNRLRemoteReceiveToObject {

	@Inject
	private EmpInfoTerminalRepository empInfoTerminalRepository;

	@Inject
	private TimeRecordSetFormatListRepository timeRecordSetFormatListRepository;

	// 変換する
	public void convertData(InputStream in) {

		JAXBContext context;
		try {
			context = JAXBContext.newInstance(NRLRemoteDataXml.class);
			Unmarshaller um = context.createUnmarshaller();
			NRLRemoteDataXml frame = (NRLRemoteDataXml) um.unmarshal(in);

			// $タイムレコーダーの設定情報 = #タイムレコードの設定情報.ペイロード分析($フレームxml)
			TimeRecordSettingInfoDto settingInfo = TimeRecordSettingInfoDto.payloadAnalysis(frame.getType(),
					frame.getPayload());

			RequireImpl impl = new RequireImpl(empInfoTerminalRepository, timeRecordSetFormatListRepository);
			AtomTask atomTask = ReceiveNRRemoteSettingService.processInfo(impl, settingInfo);
			atomTask.run();
			return;
		} catch (JAXBException e) {
			// Error file xml
			e.printStackTrace();
		}

	}

	@AllArgsConstructor
	public class RequireImpl implements ReceiveNRRemoteSettingService.Require {

		private final EmpInfoTerminalRepository empInfoTerminalRepository;

		private final TimeRecordSetFormatListRepository timeRecordSetFormatListRepository;

		@Override
		public Optional<EmpInfoTerminal> getEmpInfoTerWithMac(MacAddress macAdd,
				ContractCode contractCode) {
			return empInfoTerminalRepository.getEmpInfoTerWithMac(macAdd, contractCode);
		}
		
		@Override
		public void removeTRSetFormatList(EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode) {
			timeRecordSetFormatListRepository.removeTRSetFormatList(empInfoTerCode, contractCode);
		}

		@Override
		public void insert(ContractCode code, TimeRecordSetFormatList trSetFormat) {
			timeRecordSetFormatListRepository.insert(code, trSetFormat);
		}

	}

}
