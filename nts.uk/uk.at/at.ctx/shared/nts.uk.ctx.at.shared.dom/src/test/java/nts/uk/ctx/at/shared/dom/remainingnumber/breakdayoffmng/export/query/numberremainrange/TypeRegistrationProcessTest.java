package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TargetSelectionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SeqVacationAssociationInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.vacation.algorithm.TimeLapseVacationSetting;

public class TypeRegistrationProcessTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testDaikyuManagerTime() {
		TimeLapseVacationSetting setting = new TimeLapseVacationSetting(
				new DatePeriod(GeneralDate.ymd(2019, 4, 1), GeneralDate.ymd(2019, 4, 30)), true, 2, true,
				Optional.of(true), Optional.of(5));
		Optional<SeqVacationAssociationInfo> actualResult = TypeRegistrationProcess.process(setting,
				GeneralDate.ymd(2019, 4, 2), GeneralDate.ymd(2019, 5, 30), new ManagementDataRemainUnit(1.0),
				TypeOffsetJudgment.REAMAIN);
		assertThat(actualResult).isEqualTo(Optional.empty());

	}

	@Test
	public void testDaikyuNoManagerTime() {
		TimeLapseVacationSetting setting = new TimeLapseVacationSetting(
				new DatePeriod(GeneralDate.ymd(2019, 4, 1), GeneralDate.ymd(2019, 4, 30)), true, 2, true,
				Optional.of(false), Optional.of(5));
		Optional<SeqVacationAssociationInfo> actualResult = TypeRegistrationProcess.process(setting,
				GeneralDate.ymd(2019, 4, 2), GeneralDate.ymd(2019, 5, 30), new ManagementDataRemainUnit(1.0),
				TypeOffsetJudgment.REAMAIN);
		assertThat(actualResult.get().getOutbreakDay()).isEqualTo(GeneralDate.ymd(2019, 4, 2));
		assertThat(actualResult.get().getDateOfUse()).isEqualTo(GeneralDate.ymd(2019, 5, 30));
		assertThat(actualResult.get().getDayNumberUsed()).isEqualTo(new ReserveLeaveRemainingDayNumber(1.0));
		assertThat(actualResult.get().getTargetSelectionAtr()).isEqualTo(TargetSelectionAtr.AUTOMATIC);

	}

	@Test
	public void testFurikyu() {
		TimeLapseVacationSetting setting = new TimeLapseVacationSetting(
				new DatePeriod(GeneralDate.ymd(2019, 4, 1), GeneralDate.ymd(2019, 4, 30)), true, 2, true,
				Optional.of(false), Optional.of(5));
		Optional<SeqVacationAssociationInfo> actualResult = TypeRegistrationProcess.process(setting,
				GeneralDate.ymd(2019, 4, 2), GeneralDate.ymd(2019, 5, 30), new ManagementDataRemainUnit(1.0),
				TypeOffsetJudgment.ABSENCE);
		assertThat(actualResult.get().getOutbreakDay()).isEqualTo(GeneralDate.ymd(2019, 4, 2));
		assertThat(actualResult.get().getDateOfUse()).isEqualTo(GeneralDate.ymd(2019, 5, 30));
		assertThat(actualResult.get().getDayNumberUsed()).isEqualTo(new ReserveLeaveRemainingDayNumber(1.0));
		assertThat(actualResult.get().getTargetSelectionAtr()).isEqualTo(TargetSelectionAtr.AUTOMATIC);
	}

}
