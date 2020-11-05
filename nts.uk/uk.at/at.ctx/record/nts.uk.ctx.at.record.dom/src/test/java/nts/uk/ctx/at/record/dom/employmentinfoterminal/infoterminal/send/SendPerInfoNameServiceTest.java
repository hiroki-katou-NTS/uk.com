package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.send;

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
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeDto;
import nts.uk.ctx.at.record.dom.adapter.workplace.SWkpHistRcImported;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting.ReqSettingBuilder;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service.SendPerInfoNameService;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

/**
 * @author ThanhNX
 *
 *         個人情報をNRに 送信するデータに変換するTest
 */
@RunWith(JMockit.class)
public class SendPerInfoNameServiceTest {

	@Injectable
	private SendPerInfoNameService.Require require;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSendEmpty() {

		List<SendPerInfoName> actual = SendPerInfoNameService.send(require, new EmpInfoTerminalCode(1),
				new ContractCode("1"));
		assertThat(actual).isEqualTo(Collections.emptyList());
	}

	@Test
	public void testSendEmptySid() {

		Optional<TimeRecordReqSetting> timeRecordReqSetting = Optional
				.of(new ReqSettingBuilder(new EmpInfoTerminalCode(1), new ContractCode("1"), new CompanyId("1"), "1",
						Collections.emptyList(), null, null).overTimeHoliday(true).build());
		new Expectations() {
			{

				require.getTimeRecordReqSetting((EmpInfoTerminalCode) any, (ContractCode) any);
				result = timeRecordReqSetting;
			}
		};
		List<SendPerInfoName> actual = SendPerInfoNameService.send(require, new EmpInfoTerminalCode(1),
				new ContractCode("1"));
		assertThat(actual).isEqualTo(Collections.emptyList());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test() {

		Optional<TimeRecordReqSetting> timeRecordReqSetting = Optional
				.of(new ReqSettingBuilder(new EmpInfoTerminalCode(1), new ContractCode("1"), new CompanyId("1"), "1",
						Arrays.asList(new EmployeeId("1"), new EmployeeId("2")), null, null).overTimeHoliday(true)
								.build());

		new Expectations() {
			{

				require.getTimeRecordReqSetting((EmpInfoTerminalCode) any, (ContractCode) any);
				result = timeRecordReqSetting;

				require.findBySid((List<String>) any, (GeneralDate) any);
				result = Arrays.asList(new SWkpHistRcImported(null, "1", "11", "A", "A", "A"),
						new SWkpHistRcImported(null, "2", "22", "B", "B", "B"));

				require.getByCardNoAndContractCode((List<String>) any);
				result = Arrays.asList(
						new StampCard(new ContractCode("1"), new StampNumber("1"), "1",  GeneralDate.today(), "1"),
						new StampCard(new ContractCode("1"), new StampNumber("2"), "2",  GeneralDate.today(), "2")
				);

				require.getByListSID((List<String>) any);
				result = Arrays.asList(new EmployeeDto("1", "AAAA", "AAAAAAAAAA01234567892C"),
						new EmployeeDto("2", "BBBB", "BBBB"), new EmployeeDto("2", "BBBB", "BBBB"));

			}
		};
		List<SendPerInfoName> actual = SendPerInfoNameService.send(require, new EmpInfoTerminalCode(1),
				new ContractCode("1"));
		assertThat(actual)
				.extracting(d -> d.getIdNumber(), d -> d.getPerName(), d -> d.getDepartmentCode(),
						d -> d.getCompanyCode(), d -> d.getReservation(), d -> d.getPerCode())
				.containsExactly(Tuple.tuple("1", "AAAAAAAAAA0123456789", "A", "00", "0000", "AAAA"),
						Tuple.tuple("2", "BBBB", "B", "00", "0000", "BBBB"));

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testEmpNull() {

		Optional<TimeRecordReqSetting> timeRecordReqSetting = Optional
				.of(new ReqSettingBuilder(new EmpInfoTerminalCode(1), new ContractCode("1"), new CompanyId("1"), "1",
						Arrays.asList(new EmployeeId("1"), new EmployeeId("2")), null, null).overTimeHoliday(true)
								.build());

		new Expectations() {
			{

				require.getTimeRecordReqSetting((EmpInfoTerminalCode) any, (ContractCode) any);
				result = timeRecordReqSetting;

				require.findBySid((List<String>) any, (GeneralDate) any);
				result = Arrays.asList(new SWkpHistRcImported(null, "1", "11", "A", "A", "A"),
						new SWkpHistRcImported(null, "2", "22", "B", "B", "B"),
						new SWkpHistRcImported(null, "2", "22", "B", "B", "B"));

				require.getByCardNoAndContractCode((List<String>) any);
				result = Arrays.asList(
						new StampCard(new ContractCode("1"), new StampNumber("1"), "1",  GeneralDate.today(), "1"),
						new StampCard(new ContractCode("1"), new StampNumber("2"), "2",  GeneralDate.today(), "2"));

				require.getByListSID((List<String>) any);
				result = Arrays.asList(new EmployeeDto("1", "AAAA", "AAAAAAAAAA01234567892C"),
						new EmployeeDto("3", "BBBB", "BBBB"));

			}
		};
		List<SendPerInfoName> actual = SendPerInfoNameService.send(require, new EmpInfoTerminalCode(1),
				new ContractCode("1"));
		assertThat(actual)
				.extracting(d -> d.getIdNumber(), d -> d.getPerName(), d -> d.getDepartmentCode(),
						d -> d.getCompanyCode(), d -> d.getReservation(), d -> d.getPerCode())
				.containsExactly(Tuple.tuple("2", "", "B", "00", "0000", ""),
						Tuple.tuple("1", "AAAAAAAAAA0123456789", "A", "00", "0000", "AAAA"));

	}

}
