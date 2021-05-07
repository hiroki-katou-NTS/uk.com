package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster.Require;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

@RunWith(JMockit.class)
public class ShiftMasterTest {

	@Injectable
	Require require;

	@Test
	public void test_getters() {
		val instance = ShiftMasterHelper.createDummy();
		NtsAssert.invokeGetters(instance);
	}



	/**
	 * Target	:
	 * 		- create(constructor)
	 * 		- change
	 * Expect	:
	 * 		・指定した情報で正しくインスタンスが生成されること
	 * 		・指定した情報で正しくインスタンスの内容が変更されること
	 */
	@Test
	public void test_create_change() {

		//勤務情報
		val workInfoBefore = new WorkInformation("WorkType01", "WorkTime01");
		val workInfoAfter = new WorkInformation("WorkType02", "WorkTime02");

		//表示情報
		val displayInfoBefore = ShiftMasterHelper.DispInfo.create("Name_01", "fff", "ccc", Optional.of("Note_01"));
		val displayInfoAfter = ShiftMasterHelper.DispInfo.create("Name_02", "000", "999", Optional.of("Note_02"));

		//取込コード
		val impCdBefore = new ShiftMasterImportCode("ImportBefore");
		val impCdAfter = new ShiftMasterImportCode("ImportAfter");

		/** 作成  */
		val shiftMaster = new ShiftMaster("cid"
								,	new ShiftMasterCode("shiftMaster01")
								,	displayInfoBefore
								,	workInfoBefore.getWorkTypeCode().v()
								,	workInfoBefore.getWorkTimeCode().v()
								,	impCdBefore
							);
		//表示情報
		assertThat(shiftMaster.getDisplayInfor().getName()).isEqualTo(displayInfoBefore.getName());
		assertThat(shiftMaster.getDisplayInfor().getColor()).isEqualTo(displayInfoBefore.getColor());
		assertThat(shiftMaster.getDisplayInfor().getColorSmartPhone()).isEqualTo(displayInfoBefore.getColorSmartPhone());
		assertThat(shiftMaster.getDisplayInfor().getRemarks()).isEqualTo(displayInfoBefore.getRemarks());

		//勤務情報
		assertThat(shiftMaster.getWorkTypeCode()).isEqualTo(workInfoBefore.getWorkTypeCode());
		assertThat(shiftMaster.getWorkTimeCode()).isEqualTo(workInfoBefore.getWorkTimeCode());

		//取込コード
		assertThat(shiftMaster.getImportCode()).isEqualTo(impCdBefore);


		/** 変更  */
		shiftMaster.change(displayInfoAfter, impCdAfter, workInfoAfter);

		//表示情報
		assertThat(shiftMaster.getDisplayInfor().getName()).isEqualTo(displayInfoAfter.getName());
		assertThat(shiftMaster.getDisplayInfor().getColor()).isEqualTo(displayInfoAfter.getColor());
		assertThat(shiftMaster.getDisplayInfor().getColorSmartPhone()).isEqualTo(displayInfoAfter.getColorSmartPhone());
		assertThat(shiftMaster.getDisplayInfor().getRemarks()).isEqualTo(displayInfoAfter.getRemarks());

		//勤務情報
		assertThat(shiftMaster.getWorkTypeCode()).isEqualTo(workInfoAfter.getWorkTypeCode());
		assertThat(shiftMaster.getWorkTimeCode()).isEqualTo(workInfoAfter.getWorkTimeCode());

		//取込コード
		assertThat(shiftMaster.getImportCode()).isEqualTo(impCdAfter);

	}



	/**
	 * Target	: checkError
	 * Pattern	: エラー状態＝勤務種類が削除された
	 * 				・勤務種類が取得できない
	 * Expect	: throw Msg_1608
	 */
	@Test
	public void test_checkError_throw_Msg_1608() {

		val shiftMaster = ShiftMasterHelper.create("code", "name", "workTypeCode", Optional.of("workTimeCode"), "importCode");

		new Expectations() {{
			// 勤務種類を取得する
			require.getWorkType(anyString);
		}};

		NtsAssert.businessException("Msg_1608", () -> shiftMaster.checkError(require));

	}

	/**
	 * Target	: checkError
	 * Pattern	: エラー状態＝就業時間帯が削除された
	 * 				・就業時間帯が任意
	 * 				・就業時間帯コードが設定されている
	 * 				・就業時間帯が取得できない
	 * Expect	: throw Msg_1609
	 */
	@Test
	public void test_checkError_throw_Msg_1609(@Injectable WorkType workType) {

		val shiftMaster = ShiftMasterHelper.create("code", "name", "workTypeCode", Optional.of("workTimeCode"), "importCode");

		new Expectations() {{
			// 勤務種類を取得する
			require.getWorkType(anyString);
			result = Optional.of(workType);
			// 勤務種類：就業時間帯が必須か＝任意
			require.checkNeededOfWorkTimeSetting(anyString);
			result = SetupType.OPTIONAL;
			// 就業時間帯を取得する
			require.getWorkTime(anyString);
		}};

		NtsAssert.businessException("Msg_1609", () -> shiftMaster.checkError(require));

	}

	/**
	 * Target	: checkError
	 * Pattern	: エラー状態＝就業時間帯が必須なのに設定されていない
	 * 				・就業時間帯が必須
	 * 				・就業時間帯コードが設定されていない
	 * Expect	: throw Msg_435
	 */
	@Test
	public void test_checkError_throw_Msg_435(@Injectable WorkType workType) {

		val shiftMaster = ShiftMasterHelper.create("code", "name", "workTypeCode", Optional.empty(), "importCode");

		new Expectations() {{
			// 勤務種類を取得する
			require.getWorkType(anyString);
			result = Optional.of(workType);
			// 勤務種類：就業時間帯が必須か＝必須
			require.checkNeededOfWorkTimeSetting(anyString);
			result = SetupType.REQUIRED;
		}};

		NtsAssert.businessException("Msg_435", () -> shiftMaster.checkError(require));

	}

	/**
	 * Target	: checkError
	 * Pattern	: エラー状態＝就業時間帯が不要なのに設定されている
	 * 				・就業時間帯が不要
	 * 				・就業時間帯コードが設定されている
	 * Expect	: throw Msg_434
	 */
	@Test
	public void test_checkError_throw_Msg_434(@Injectable WorkType workType) {

		val shiftMaster = ShiftMasterHelper.create("code", "name", "workTypeCode", Optional.of("workTimeCode"), "importCode");

		new Expectations() {{
			// 勤務種類を取得する
			require.getWorkType(anyString);
			result = Optional.of(workType);
			// 勤務種類：就業時間帯が必須か＝不要
			require.checkNeededOfWorkTimeSetting(anyString);
			result = SetupType.NOT_REQUIRED;
		}};

		NtsAssert.businessException("Msg_434", () -> shiftMaster.checkError(require));

	}

}
