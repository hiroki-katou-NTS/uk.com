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

	/*
	 * 　テストしたい内容
	 * 　　「逐次休暇の紐付け情報」データを作成するかどうかを確認する
	 * 
	 * 　準備するデータ
	 * 　　　代休
	 * 　　　時間管理者がある
	 * */
	@Test
	public void testDaikyuManagerTime() {
		TimeLapseVacationSetting setting = create(true, TypeOffsetJudgment.REAMAIN);//時間管理区分,代休
		Optional<SeqVacationAssociationInfo> actualResult = TypeRegistrationProcess.process(setting,
				GeneralDate.ymd(2019, 4, 2), GeneralDate.ymd(2019, 5, 30), new ManagementDataRemainUnit(1.0),
				TypeOffsetJudgment.REAMAIN);
		assertThat(actualResult).isEqualTo(Optional.empty());

	}

	/*
	 * 　テストしたい内容
	 * 　　「逐次休暇の紐付け情報」データを作成するかどうかを確認する
	 * 
	 * 　準備するデータ
	 * 　　　代休
	 * 　　　時間管理者がない
	 * */
	@Test
	public void testDaikyuNoManagerTime() {
		TimeLapseVacationSetting setting = create(false, TypeOffsetJudgment.REAMAIN);//時間管理区分,代休
		Optional<SeqVacationAssociationInfo> actualResult = TypeRegistrationProcess.process(setting,
				GeneralDate.ymd(2019, 4, 2), GeneralDate.ymd(2019, 5, 30), new ManagementDataRemainUnit(1.0),
				TypeOffsetJudgment.REAMAIN);
		assertThat(actualResult.get().getOutbreakDay()).isEqualTo(GeneralDate.ymd(2019, 4, 2));// 発生日
		assertThat(actualResult.get().getDateOfUse()).isEqualTo(GeneralDate.ymd(2019, 5, 30));// 使用日
		assertThat(actualResult.get().getDayNumberUsed()).isEqualTo(new ReserveLeaveRemainingDayNumber(1.0));// 使用日数
		assertThat(actualResult.get().getTargetSelectionAtr()).isEqualTo(TargetSelectionAtr.AUTOMATIC);// 対象選択区分

	}

	/*
	 * 　テストしたい内容
	 * 　　「逐次休暇の紐付け情報」データを作成するかどうかを確認する
	 * 
	 * 　準備するデータ
	 * 　　　振休
	 * */
	@Test
	public void testFurikyu() {
		TimeLapseVacationSetting setting = create(false, TypeOffsetJudgment.REAMAIN);//時間管理区分,振休
		Optional<SeqVacationAssociationInfo> actualResult = TypeRegistrationProcess.process(setting,
				GeneralDate.ymd(2019, 4, 2), GeneralDate.ymd(2019, 5, 30), new ManagementDataRemainUnit(1.0),
				TypeOffsetJudgment.ABSENCE);
		assertThat(actualResult.get().getOutbreakDay()).isEqualTo(GeneralDate.ymd(2019, 4, 2));// 発生日
		assertThat(actualResult.get().getDateOfUse()).isEqualTo(GeneralDate.ymd(2019, 5, 30));// 使用日
		assertThat(actualResult.get().getDayNumberUsed()).isEqualTo(new ReserveLeaveRemainingDayNumber(1.0));// 使用日数
		assertThat(actualResult.get().getTargetSelectionAtr()).isEqualTo(TargetSelectionAtr.AUTOMATIC);// 対象選択区分
	}

	private  TimeLapseVacationSetting create(boolean magTime, TypeOffsetJudgment type) {
		return  new TimeLapseVacationSetting(
					new DatePeriod(GeneralDate.ymd(2019, 4, 11), GeneralDate.ymd(2019, 4, 20)), true, 2, true,
					Optional.of(magTime), Optional.of(5));
	}
}
