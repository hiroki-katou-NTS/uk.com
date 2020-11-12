package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.application;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @author ThanhNX
 *
 *         申請受信データPub
 */
@EqualsAndHashCode
public class ApplicationReceptionDataImport {

	/**
	 * ID番号
	 */
	@Getter
	private String idNumber;

	/**
	 * 申請区分
	 */
	@Getter
	private String applicationCategory;

	/**
	 * 年月日
	 */
	@Getter
	private String ymd;

	/**
	 * 時分秒
	 */
	@Getter
	private String time;

	public ApplicationReceptionDataImport(String idNumber, String applicationCategory, String ymd, String time) {
		this.idNumber = idNumber;
		this.applicationCategory = applicationCategory;
		this.ymd = ymd;
		this.time = time;
	}

}
