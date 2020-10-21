package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ConvertEmbossCategory;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.CreateStampInfo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerSerialNo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal.EmpInfoTerminalBuilder;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalName;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.IPAddress;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MacAddress;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ModelEmpInfoTer;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MonitorIntervalTime;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.OutPlaceConvert;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting.ReqSettingBuilder;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.receive.StampReceptionData;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.receive.StampReceptionData.StampDataBuilder;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampDataReflectResult;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author ThanhNX
 *
 *         データタイムレコードを打刻に変換するTest
 */
@RunWith(JMockit.class)
public class ConvertTimeRecordStampServiceTest {
	private static EmpInfoTerminalCode empInfoTerCode;

	private static ContractCode contractCode;

	private static Optional<EmpInfoTerminal> empInfoTer;

	@Injectable
	private ConvertTimeRecordStampService.Require require;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		empInfoTerCode = new EmpInfoTerminalCode(1);
		contractCode = new ContractCode("1");
	}

	@Before
	public void setUp() throws Exception {
		empInfoTer = Optional.of(new EmpInfoTerminalBuilder(Optional.of(new IPAddress("192.168.1.1")),
				new MacAddress("AABBCCDD"), new EmpInfoTerminalCode(1), Optional.of(new EmpInfoTerSerialNo("1")),
				new EmpInfoTerminalName(""), new ContractCode("1"))
						.createStampInfo(new CreateStampInfo(new OutPlaceConvert(NotUseAtr.NOT_USE, Optional.empty()),
								new ConvertEmbossCategory(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE), Optional.empty()))
						.modelEmpInfoTer(ModelEmpInfoTer.NRL_1).intervalTime((new MonitorIntervalTime(1))).build());
	}

	@Test
	public void testEmpInfoTerNoPresent() {
		StampReceptionData dataNR = new StampDataBuilder("1", "A", "1", "A", "200303", "01").time("0101")
				.overTimeHours("1101").midnightTime("1201").build();
		Pair<Optional<AtomTask>, Optional<StampDataReflectResult>> resultActual = ConvertTimeRecordStampService
				.convertData(require, empInfoTerCode, contractCode, dataNR);
		assertThat(resultActual.getLeft()).isEqualTo(Optional.empty());
		assertThat(resultActual.getRight()).isEqualTo(Optional.empty());

	}

	@Test
	public void testEmpInfoTerNoPresentSetting() {
		StampReceptionData dataNR = new StampDataBuilder("1", "A", "1", "A", "200303", "01").time("0101")
				.overTimeHours("1101").midnightTime("1201").build();

		Optional<EmpInfoTerminal> empInfoTer = Optional
				.of(new EmpInfoTerminalBuilder(Optional.of(new IPAddress("192.168.1.1")), new MacAddress("AABBCCDD"),
						new EmpInfoTerminalCode(1), Optional.of(new EmpInfoTerSerialNo("1")),
						new EmpInfoTerminalName(""), new ContractCode("1")).createStampInfo(null)
								.modelEmpInfoTer(ModelEmpInfoTer.NRL_1).intervalTime((new MonitorIntervalTime(1)))
								.build());
		new Expectations() {
			{
				require.getEmpInfoTerminal((EmpInfoTerminalCode) any, (ContractCode) any);
				result = empInfoTer;

			}
		};

		Pair<Optional<AtomTask>, Optional<StampDataReflectResult>> resultActual = ConvertTimeRecordStampService
				.convertData(require, empInfoTerCode, contractCode, dataNR);
		assertThat(resultActual.getLeft()).isEqualTo(Optional.empty());
		assertThat(resultActual.getRight()).isEqualTo(Optional.empty());

	}

	@Test
	public void testExistHistory() {
		StampReceptionData dataNR = new StampDataBuilder("1", "A", "1", "A", "200303", "01").time("0101")
				.overTimeHours("1101").midnightTime("1201").build();

		Optional<TimeRecordReqSetting> timeRecordReqSetting = Optional
				.of(new ReqSettingBuilder(empInfoTerCode, contractCode, null, null, null, null, null).build());

		Optional<StampRecord> stampRecord = Optional.empty();
//				Optional.of(new StampRecord(new StampNumber("1"), GeneralDateTime.now(),
//				true, ReservationArt.NONE, Optional.empty()));

		new Expectations() {
			{
				require.getEmpInfoTerminal((EmpInfoTerminalCode) any, (ContractCode) any);
				result = empInfoTer;

				require.getTimeRecordReqSetting((EmpInfoTerminalCode) any, (ContractCode) any);
				result = timeRecordReqSetting;

				require.getStampRecord(contractCode, (StampNumber) any, (GeneralDateTime) any);
				result = stampRecord;

			}
		};

		Pair<Optional<AtomTask>, Optional<StampDataReflectResult>> resultActual = ConvertTimeRecordStampService
				.convertData(require, empInfoTerCode, contractCode, dataNR);
		assertThat(resultActual.getLeft()).isEqualTo(Optional.empty());
		assertThat(resultActual.getRight()).isEqualTo(Optional.empty());

	}

	@Test
	public void testNotExistStampCard() {
		StampReceptionData dataNR = new StampDataBuilder("1", "A", "1", "A", "200303", "01").time("0101")
				.overTimeHours("1101").midnightTime("1201").build();

		Optional<TimeRecordReqSetting> timeRecordReqSetting = Optional
				.of(new ReqSettingBuilder(empInfoTerCode, contractCode, null, null, null, null, null).build());

		new Expectations() {
			{
				require.getEmpInfoTerminal((EmpInfoTerminalCode) any, (ContractCode) any);
				result = empInfoTer;

				require.getTimeRecordReqSetting((EmpInfoTerminalCode) any, (ContractCode) any);
				result = timeRecordReqSetting;

			}
		};

		Pair<Optional<AtomTask>, Optional<StampDataReflectResult>> resultActual = ConvertTimeRecordStampService
				.convertData(require, empInfoTerCode, contractCode, dataNR);
		assertThat(resultActual.getLeft()).isEqualTo(Optional.empty());
		assertThat(resultActual.getRight()).isEqualTo(Optional.empty());

	}

	@Test
	public void testRegistDoneData() {
		StampReceptionData dataNR = new StampDataBuilder("1", "A", "1", "A", "200303", "01").time("0101")
				.overTimeHours("1101").midnightTime("1201").build();

		Optional<TimeRecordReqSetting> timeRecordReqSetting = Optional
				.of(new ReqSettingBuilder(empInfoTerCode, contractCode, null, null, null, null, null).build());

		new Expectations() {
			{
				require.getEmpInfoTerminal((EmpInfoTerminalCode) any, (ContractCode) any);
				result = empInfoTer;

				require.getTimeRecordReqSetting((EmpInfoTerminalCode) any, (ContractCode) any);
				result = timeRecordReqSetting;

				require.getStampRecord(contractCode, (StampNumber) any, (GeneralDateTime) any);
				result = Optional.empty();

				require.getByCardNoAndContractCode(contractCode, (StampNumber) any);
				result = Optional.empty();
//						Optional.of(new StampCard("1", "2", new StampNumber("1"), GeneralDate.today(), contractCode));

			}
		};

		Pair<Optional<AtomTask>, Optional<StampDataReflectResult>> resultActual = ConvertTimeRecordStampService
				.convertData(require, empInfoTerCode, contractCode, dataNR);
		assertThat(resultActual.getLeft()).isEqualTo(Optional.empty());
		NtsAssert.atomTask(() -> resultActual.getRight().get().getAtomTask(),
				any -> require.insert((StampRecord) (any.get())), any -> require.insert((Stamp) (any.get())));
	}
}
