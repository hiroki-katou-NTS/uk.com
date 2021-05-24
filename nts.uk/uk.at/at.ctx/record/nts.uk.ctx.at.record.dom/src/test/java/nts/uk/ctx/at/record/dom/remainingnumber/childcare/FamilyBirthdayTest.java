package nts.uk.ctx.at.record.dom.remainingnumber.childcare;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import static nts.arc.time.GeneralDate.*;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.FamilyBirthday;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;

@RunWith(JMockit.class)
public class FamilyBirthdayTest {

	@Injectable
	private NursingLeaveSetting.RequireM7 require;

	/**
	 * 子の看護対象判定
	 */
	@Test
	// パターン１　※6歳未満＝6歳の誕生日前日まで
	// 本年起算日時点の年齢が6歳未満
	// 次回起算日時点の年齢が6歳未満
	// 生年月日 2014.4.2
	// 本年起算日 2020.4.1、次回起算日 2020.4.1
	public void childCareAtr1() {
		val birthday = ymd(2014, 4, 2);//生年月日
		val childCare = familyBirthday(birthday);
		val childCareAtr = childCare.childCareAtr(birthday.addYears(6).addDays(-1), birthday.addYears(6).addDays(-1));//本年起算日、次回起算日

		assertThat(childCareAtr.isThisAtr()).isTrue();
		assertThat(childCareAtr.isNextAtr()).isTrue();
	}

	@Test
	// パターン２　※6歳未満＝6歳の誕生日前日まで
	// 本年起算日時点の年齢が6歳未満
	// 次回起算日時点の年齢が6歳未満ではない
	// 生年月日 2014.4.1
	// 本年起算日 2020.3.31、次回起算日 2020.4.1
	public void childCareAtr2() {
		val birthday = ymd(2014, 4, 1);
		val childCare = familyBirthday(birthday);
		val childCareAtr = childCare.childCareAtr(birthday.addYears(6).addDays(-1), birthday.addYears(6));//本年起算日、次回起算日

		assertThat(childCareAtr.isThisAtr()).isTrue();
		assertThat(childCareAtr.isNextAtr()).isFalse();
	}

	@Test
	// パターン３　※6歳未満＝6歳の誕生日前日まで
	// 次回起算日時点の年齢が6歳未満ではない
	// 本年起算日時点の年齢が6歳未満ではない
	// 生年月日 2014.4.1
	// 本年起算日 2020.4.1、次回起算日 2020.4.1
	public void childCareAtr3() {
		val birthday = ymd(2014, 4, 1);
		val childCare = familyBirthday(birthday);
		val childCareAtr = childCare.childCareAtr(birthday.addYears(6), birthday.addYears(6));//本年起算日、次回起算日

		assertThat(childCareAtr.isThisAtr()).isFalse();
		assertThat(childCareAtr.isNextAtr()).isFalse();
	}

	// 家族の生年月日
	private FamilyBirthday familyBirthday(GeneralDate birthday) {
		return FamilyBirthday.of(birthday);
	}
}
