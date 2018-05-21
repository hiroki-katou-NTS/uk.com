/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.dom.mailnoticeset.dto;

import lombok.Getter;

/**
 * The Class PersonContactImport.
 */
// 個人連絡先
@Getter
public class PersonContactImport {

	// 個人ID
	/** The person id. */
	private String personId;

	// メールアドレス
	/** The mail address. */
	private String mailAddress;

	// 携帯メールアドレス
	/** The mobile mail address. */
	private String mobileMailAddress;

	// 携帯電話番号
	/** The cell phone no. */
	private String cellPhoneNo;

	/**
	 * Instantiates a new person contact import.
	 *
	 * @param personId
	 *            the person id
	 * @param mailAddress
	 *            the mail address
	 * @param mobileMailAddress
	 *            the mobile mail address
	 * @param cellPhoneNo
	 *            the cell phone no
	 */
	public PersonContactImport(String personId, String mailAddress, String mobileMailAddress, String cellPhoneNo) {
		this.personId = personId;
		this.mailAddress = mailAddress;
		this.mobileMailAddress = mobileMailAddress;
		this.cellPhoneNo = cellPhoneNo;
	}

}
