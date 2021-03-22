package nts.uk.ctx.at.record.pubimp.employmentinfoterminal.infoterminal;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service.SendReservationMenuService;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.SendNRDataPub;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.SendReservationMenuExport;

@Stateless
public class SendReservationMenuPubImpl implements SendNRDataPub<List<SendReservationMenuExport>> {

	@Inject
	private TimeRecordReqSettingRepository timeRecordReqSettingRepository;

	@Inject
	private BentoMenuRepository bentoMenuRepository;

	@Override
	public List<SendReservationMenuExport> send(String empInfoTerCode, String contractCode) {

		RequireImpl requireImpl = new RequireImpl(timeRecordReqSettingRepository, bentoMenuRepository);

		return SendReservationMenuService.send(requireImpl, new EmpInfoTerminalCode(empInfoTerCode), new ContractCode(contractCode)).stream()
				.map(x -> new SendReservationMenuExport(x.getBentoMenu(), x.getUnit(), x.getFrameNumber()))
				.collect(Collectors.toList());

	}

	@AllArgsConstructor
	public static class RequireImpl implements SendReservationMenuService.Require {

		private final TimeRecordReqSettingRepository timeRecordReqSettingRepository;

		private final BentoMenuRepository bentoMenuRepository;

		@Override
		public List<Bento> getBento(String companyID, GeneralDate date, List<Integer> frameNos) {
			BentoMenu bentoMenu = bentoMenuRepository.getBentoMenu(companyID, date);
			if (bentoMenu == null)
				return Collections.emptyList();

			return bentoMenu.getMenu().stream().filter(x -> frameNos.contains(x.getFrameNo()))
					.collect(Collectors.toList());
		}

		@Override
		public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode) {
			return timeRecordReqSettingRepository.getTimeRecordReqSetting(empInfoTerCode, contractCode);
		}

	}
}
