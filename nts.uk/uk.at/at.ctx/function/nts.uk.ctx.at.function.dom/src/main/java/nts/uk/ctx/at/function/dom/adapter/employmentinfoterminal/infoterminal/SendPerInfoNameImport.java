package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal;

import lombok.Value;

/**
 * @author ThanhNX
 *
 *         個人情報名称送信Import
 */
@Value
public class SendPerInfoNameImport {

	/**
	 * ID番号
	 */
	private final String idNumber;

	/**
	 * 個人名称
	 */
	private final String perName;

	/**
	 * 部門
	 */
	private final String departmentCode;

	/**
	 * 会社コード
	 */
	private final String companyCode;

	/**
	 * 予約
	 */
	private final String reservation;

	/**
	 * 個人コード
	 */
	private final String perCode;

	public SendPerInfoNameImport(String idNumber, String perName, String departmentCode, String companyCode,
			String reservation, String perCode) {
		this.idNumber = idNumber;
		this.perName = perName;
		this.departmentCode = departmentCode;
		this.companyCode = companyCode;
		this.reservation = reservation;
		this.perCode = perCode;
	}
}
