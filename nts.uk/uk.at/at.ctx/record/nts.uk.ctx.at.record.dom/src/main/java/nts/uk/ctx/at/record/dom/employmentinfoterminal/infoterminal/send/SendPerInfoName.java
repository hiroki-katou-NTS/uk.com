package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.send;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;

/**
 * @author ThanhNX
 *
 *         個人情報名称送信
 */
@Value
public class SendPerInfoName implements DomainValue {

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

	public SendPerInfoName(String idNumber, String perName, String departmentCode, String companyCode,
			String reservation, String perCode) {
		super();
		this.idNumber = idNumber;
		this.perName = perName;
		this.departmentCode = departmentCode;
		this.companyCode = companyCode;
		this.reservation = reservation;
		this.perCode = perCode;
	}

}
