package nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * 家族の生年月日
 * @author yuri_tamakoshi
*/
@Getter
@Setter
public class FamilyBirthday {

	/** 生年月日 */
	private GeneralDate birthday;

	/**
	 * コンストラクタ
	 */
	public FamilyBirthday(){
		this.birthday = GeneralDate.today();
	}

	/**
	 * ファクトリー
	 * @param birthday 生年月日
	 * @return 家族の生年月日
	 */
	public static FamilyBirthday of(
			GeneralDate birthday){

		FamilyBirthday domain = new FamilyBirthday();
		domain.birthday = birthday;
		return domain;
	}

	/**
	 * 子の看護対象判定
	 * @param thisYearStartMonthDay 本年起算日
	 * @param nextStartMonthDay 次回起算日
	 * @return 看護対象か ChildCareAtr
	 */
	public ChildCareAtr childCareAtr(GeneralDate thisYearStartMonthDay, GeneralDate nextStartMonthDay) {

		// 看護対象か
		ChildCareAtr childCareAtr = new ChildCareAtr();

		// 本年起算日時点の年齢が6歳以下か確認する
		// ===本年起算日　ー　生年月日 　<　6年
		// ===true：6歳未満
		if(thisYearStartMonthDay.before(birthday.addYears(6))) {
			// 6歳未満の場合
			// 本年対象をtrueに変更
			childCareAtr.setThisAtr(true);
		}

		 // 次回起算日時点の年齢が6歳以下か確認する
		// ===次回起算日　ー　生年月日 　<　6年
		// ===true：6歳未満
		if(nextStartMonthDay.before(birthday.addYears(6))) {
			// 6歳未満の場合
			// 次回対象をtrueに変更
			childCareAtr.setNextAtr(true);
		}
		return childCareAtr;
	}
}
