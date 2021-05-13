package nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave;

import lombok.Getter;
import lombok.Setter;

/**
 * 看護対象か
 * @author yuri_tamakoshi
 */
@Getter
@Setter
public class ChildCareAtr {
	/** 次回対象か */
	private boolean nextAtr;
	/** 今回対象か */
	private boolean thisAtr;

	/**
	 * コンストラクタ
	 */
	public ChildCareAtr() {
		this.nextAtr = false;
		this.thisAtr =  false;
	}
	/**
	 * ファクトリー
	 * @param nextAtr 次回対象か
	 * @param thisAtr 今回対象か
	 * @return 看護対象か
	 */
	public static ChildCareAtr of(
			boolean nextAtr,
			boolean thisAtr){

		ChildCareAtr domain = new ChildCareAtr();
		domain.nextAtr = nextAtr;
		domain.thisAtr = thisAtr;
		return domain;
	}
}
