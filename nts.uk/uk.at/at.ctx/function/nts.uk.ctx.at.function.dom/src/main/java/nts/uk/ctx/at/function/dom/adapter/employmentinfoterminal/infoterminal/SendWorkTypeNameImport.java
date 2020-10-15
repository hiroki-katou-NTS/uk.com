package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal;

import lombok.Value;

/**
 * @author ThanhNX
 *
 *         勤務種類送Import
 */
@Value
public class SendWorkTypeNameImport {

	/**
	 * 勤務番号
	 */
	private final String workTypeNumber;

	/**
	 * 1日の勤務区分番号
	 */
	private final String daiClassifiNum;

	/**
	 * 勤務名
	 */
	private final String workName;

	public SendWorkTypeNameImport(String workTypeNumber, String daiClassifiNum, String workName) {
		super();
		this.workTypeNumber = workTypeNumber;
		this.daiClassifiNum = daiClassifiNum;
		this.workName = workName;
	}
}
