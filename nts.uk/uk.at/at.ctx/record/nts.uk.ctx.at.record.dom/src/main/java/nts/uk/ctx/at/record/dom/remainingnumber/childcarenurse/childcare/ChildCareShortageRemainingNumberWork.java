package nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.ChildCareNurseUsedNumber;

/**
 *　子の看護介護残数不足数
  * @author yuri_tamakoshi
 */
@Getter
@Setter
public class ChildCareShortageRemainingNumberWork {

	/** 残数不足数 */
	private ChildCareNurseRemainingNumber shortageRemNum;
	/** 使用可能数 */
	private ChildCareNurseUsedNumber available;

	/**
	 * コンストラクタ
	 */
	public ChildCareShortageRemainingNumberWork(){
		this.shortageRemNum = new ChildCareNurseRemainingNumber();
		this.available = new  ChildCareNurseUsedNumber();
	}
	/**
	 * ファクトリー
	 * @param shortageRemNum 残数不足数
	 * @param available 使用可能数
	 * @return 子の看護介護残数不足数
	 */
	public static ChildCareShortageRemainingNumberWork of (
			ChildCareNurseRemainingNumber shortageRemNum,
			 ChildCareNurseUsedNumber available) {

		ChildCareShortageRemainingNumberWork domain = new ChildCareShortageRemainingNumberWork();
		domain.shortageRemNum = shortageRemNum;
		domain.available = available;
		return domain;
	}
}