package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.send.SendReservationMenu;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;

/**
 * @author ThanhNX
 *
 *         予約をNRに 送信するデータに変換する
 */
public class SendReservationMenuService {

	private SendReservationMenuService() {
	};

	// [1] 各種名称送信に変換
	public static List<SendReservationMenu> send(Require require, EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode) {

		Optional<TimeRecordReqSetting> requestSetting = require.getTimeRecordReqSetting(empInfoTerCode, contractCode);

		if (!requestSetting.isPresent() || requestSetting.get().getBentoMenuFrameNumbers().isEmpty())
			return Collections.emptyList();

		List<Bento> lstBento = require.getBento(requestSetting.get().getCompanyId().v(), GeneralDate.today(),
				requestSetting.get().getBentoMenuFrameNumbers());
		return convert(lstBento);
	}

	// [pvt-1] 予約メニュー名称送信に変換
	private static List<SendReservationMenu> convert(List<Bento> lstBento) {
		List<SendReservationMenu> lstResult = new ArrayList<>();
		IntStream.range(1, 41).boxed().forEach(indx -> {
			lstResult.add(lstBento.stream().filter(x -> x.getFrameNo() == indx).findFirst()
					.map(x -> new SendReservationMenu(x.getName().v(), x.getUnit().v(), x.getFrameNo()))
					.orElse(new SendReservationMenu("", "", indx)));
		});
		return lstResult;
	}

	public static interface Require {

		// [R-1] 弁当を取得する
		public List<Bento> getBento(String companyID, GeneralDate date, List<Integer> frameNos);

		// [R-2]タイムレコードのﾘｸｴｽﾄ設定を取得する
		public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode);
	}

}
