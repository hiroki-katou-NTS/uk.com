package nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * 看護介護対象人数変更日
 * @author yuri_tamakoshi
 */
@Getter
@Setter
public class ChildCareTargetChanged {

	/** 人数 */
	private NumberOfCaregivers numPerson;
	/** 変更日 */
	private GeneralDate ymd;

	/**
	 * コンストラクタ
	 */
	public ChildCareTargetChanged() {
		this.numPerson = new NumberOfCaregivers(0);
		this.ymd = GeneralDate.today();
	}
	/**
	 * ファクトリー
	 * @param numPerson人数
	 * @param ymd 変更日
	 * @return 看護介護対象人数変更日
	 */
	public static ChildCareTargetChanged of(
			NumberOfCaregivers numPerson,
			GeneralDate ymd){

		ChildCareTargetChanged domain = new ChildCareTargetChanged();
		domain.numPerson = numPerson;
		domain.ymd = ymd;
		return domain;
	}

	/** 人数を１人追加する */
	public void addOnePerson() {
		this.numPerson = new NumberOfCaregivers(this.numPerson.v() + 1);
	}
}
