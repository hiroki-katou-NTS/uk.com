package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.send;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;

/**
 * @author ThanhNX
 *
 *         勤務種類送信
 */
@Value
public class SendWorkTypeName implements DomainValue {

	/**
	 * 勤務番号
	 */
	private final String workTypeNumber;

	/**
	 * 1日の勤務区分番号
	 */
	private final String daiClassifiNum;

	/**
	 * 午前の勤務区分番号
	 */
	private final String morningClassifiNum;

	/**
	 * 午後の勤務区分番号
	 */
	private final String afternoonClassifiNum;

	/**
	 * 勤務名
	 */
	private final String workName;

	public SendWorkTypeName(String workTypeNumber, String daiClassifiNum, String morningClassifiNum,
			String afternoonClassifiNum, String workName) {
		super();
		this.workTypeNumber = workTypeNumber;
		this.daiClassifiNum = daiClassifiNum;
		this.morningClassifiNum = morningClassifiNum;
		this.afternoonClassifiNum = afternoonClassifiNum;
		this.workName = workName;
	}

}
