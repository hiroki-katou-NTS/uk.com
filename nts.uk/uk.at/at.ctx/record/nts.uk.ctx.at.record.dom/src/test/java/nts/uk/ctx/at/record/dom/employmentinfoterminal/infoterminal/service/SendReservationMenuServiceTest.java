package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.assertj.core.groups.Tuple;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting.ReqSettingBuilder;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.send.SendReservationMenu;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoAmount;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoName;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoReservationUnitName;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * @author ThanhNX
 *
 *         予約をNRに 送信するデータに変換するTest
 */
@RunWith(JMockit.class)
public class SendReservationMenuServiceTest {

	@Injectable
	private SendReservationMenuService.Require require;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSendEmpty() {

		List<SendReservationMenu> actual = SendReservationMenuService.send(require, new EmpInfoTerminalCode("1"),
				new ContractCode("1"));
		assertThat(actual).isEqualTo(Collections.emptyList());
	}

	@Test
	public void testSendEmptyReservType() {

		Optional<TimeRecordReqSetting> timeRecordReqSetting = Optional
				.of(new ReqSettingBuilder(new EmpInfoTerminalCode("1"), new ContractCode("1"), new CompanyId("1"), "1",
						Collections.emptyList(), Collections.emptyList(), Collections.emptyList()).overTimeHoliday(true)
								.workTime(Collections.emptyList()).reservationReceive(false).build());
		new Expectations() {
			{

				require.getTimeRecordReqSetting((EmpInfoTerminalCode) any, (ContractCode) any);
				result = timeRecordReqSetting;
			}
		};
		List<SendReservationMenu> actual = SendReservationMenuService.send(require, new EmpInfoTerminalCode("1"),
				new ContractCode("1"));
		assertThat(actual).isEqualTo(Collections.emptyList());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDone() {
		Optional<TimeRecordReqSetting> timeRecordReqSetting = Optional
				.of(new ReqSettingBuilder(new EmpInfoTerminalCode("1"), new ContractCode("1"), new CompanyId("1"), "1",
						Collections.emptyList(), Arrays.asList(1, 2), Collections.emptyList()).overTimeHoliday(true)
								.workTime(Collections.emptyList()).reservationReceive(false).build());
		new Expectations() {
			{

				require.getTimeRecordReqSetting((EmpInfoTerminalCode) any, (ContractCode) any);
				result = timeRecordReqSetting;

				require.getBento(anyString, (GeneralDate) any, (List<Integer>) any);
				result = Arrays.asList(
						new Bento(1, new BentoName("A"), new BentoAmount(100), new BentoAmount(200),
								new BentoReservationUnitName("1"), ReservationClosingTimeFrame.FRAME1, Optional.empty()),
						
						new Bento(2, new BentoName("B"), new BentoAmount(100), new BentoAmount(200),
								new BentoReservationUnitName("1"), ReservationClosingTimeFrame.FRAME2, Optional.empty())
						);
			}
		};
		List<SendReservationMenu> actual = SendReservationMenuService.send(require, new EmpInfoTerminalCode("1"),
				new ContractCode("1"));
		assertThat(actual).extracting(d -> d.getBentoMenu(), d -> d.getUnit(), d -> d.getFrameNumber())
				.contains(Tuple.tuple("A", "1", 1), Tuple.tuple("B", "1", 2),
						Tuple.tuple("", "", 3),
						Tuple.tuple("", "", 4),
						Tuple.tuple("", "", 5),
						Tuple.tuple("", "", 6),
						Tuple.tuple("", "", 7),
						Tuple.tuple("", "", 8),
						Tuple.tuple("", "", 9),
						Tuple.tuple("", "", 10),
						Tuple.tuple("", "", 11),
						Tuple.tuple("", "", 12),
						Tuple.tuple("", "", 13),
						Tuple.tuple("", "", 14),
						Tuple.tuple("", "", 15),
						Tuple.tuple("", "", 16),
						Tuple.tuple("", "", 17),
						Tuple.tuple("", "", 18),
						Tuple.tuple("", "", 19),
						Tuple.tuple("", "", 20),
						Tuple.tuple("", "", 21),
						Tuple.tuple("", "", 22),
						Tuple.tuple("", "", 23),
						Tuple.tuple("", "", 24),
						Tuple.tuple("", "", 25),
						Tuple.tuple("", "", 26),
						Tuple.tuple("", "", 27),
						Tuple.tuple("", "", 28),
						Tuple.tuple("", "", 29),
						Tuple.tuple("", "", 30),
						Tuple.tuple("", "", 31),
						Tuple.tuple("", "", 32),
						Tuple.tuple("", "", 33),
						Tuple.tuple("", "", 34),
						Tuple.tuple("", "", 35),
						Tuple.tuple("", "", 36),
						Tuple.tuple("", "", 37),
						Tuple.tuple("", "", 38),
						Tuple.tuple("", "", 39),
						Tuple.tuple("", "", 40)
						);
	}

}
