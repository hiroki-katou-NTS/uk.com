//package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import org.assertj.core.groups.Tuple;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import mockit.Expectations;
//import mockit.Injectable;
//import mockit.integration.junit4.JMockit;
//import nts.arc.time.GeneralDate;
//import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
//import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
//import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting.ReqSettingBuilder;
//import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.send.SendReservationMenu;
//import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
//import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoAmount;
//import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoName;
//import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoReservationUnitName;
//import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
//import nts.uk.ctx.at.shared.dom.common.CompanyId;
//
///**
// * @author ThanhNX
// *
// *         予約をNRに 送信するデータに変換するTest
// */
//@RunWith(JMockit.class)
//public class SendReservationMenuServiceTest {
//
//	@Injectable
//	private SendReservationMenuService.Require require;
//
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//	}
//
//	@Before
//	public void setUp() throws Exception {
//	}
//
//	@Test
//	public void testSendEmpty() {
//
//		List<SendReservationMenu> actual = SendReservationMenuService.send(require, new EmpInfoTerminalCode(1),
//				new ContractCode("1"));
//		assertThat(actual).isEqualTo(Collections.emptyList());
//	}
//
//	@Test
//	public void testSendEmptyReservType() {
//
//		Optional<TimeRecordReqSetting> timeRecordReqSetting = Optional
//				.of(new ReqSettingBuilder(new EmpInfoTerminalCode(1), new ContractCode("1"), new CompanyId("1"), "1",
//						Collections.emptyList(), Collections.emptyList(), Collections.emptyList()).overTimeHoliday(true)
//								.workTime(Collections.emptyList()).reservationReceive(false).build());
//		new Expectations() {
//			{
//
//				require.getTimeRecordReqSetting((EmpInfoTerminalCode) any, (ContractCode) any);
//				result = timeRecordReqSetting;
//			}
//		};
//		List<SendReservationMenu> actual = SendReservationMenuService.send(require, new EmpInfoTerminalCode(1),
//				new ContractCode("1"));
//		assertThat(actual).isEqualTo(Collections.emptyList());
//	}
//
//	@SuppressWarnings("unchecked")
//	@Test
//	public void testDone() {
//		Optional<TimeRecordReqSetting> timeRecordReqSetting = Optional
//				.of(new ReqSettingBuilder(new EmpInfoTerminalCode(1), new ContractCode("1"), new CompanyId("1"), "1",
//						Collections.emptyList(), Arrays.asList(1, 2), Collections.emptyList()).overTimeHoliday(true)
//								.workTime(Collections.emptyList()).reservationReceive(false).build());
//		new Expectations() {
//			{
//
//				require.getTimeRecordReqSetting((EmpInfoTerminalCode) any, (ContractCode) any);
//				result = timeRecordReqSetting;
//
//				require.getBento(anyString, (GeneralDate) any, (List<Integer>) any);
//				result = Arrays.asList(
//						new Bento(1, new BentoName("A"), new BentoAmount(100), new BentoAmount(200),
//								new BentoReservationUnitName("1"), true, true),
//						new Bento(2, new BentoName("B"), new BentoAmount(100), new BentoAmount(200),
//								new BentoReservationUnitName("1"), true, true));
//			}
//		};
//		List<SendReservationMenu> actual = SendReservationMenuService.send(require, new EmpInfoTerminalCode(1),
//				new ContractCode("1"));
//		assertThat(actual).extracting(d -> d.getBentoMenu(), d -> d.getUnit(), d -> d.getFrameNumber())
//				.containsExactly(Tuple.tuple("A", "1", 1), Tuple.tuple("B", "1", 2));
//	}
//
//}
