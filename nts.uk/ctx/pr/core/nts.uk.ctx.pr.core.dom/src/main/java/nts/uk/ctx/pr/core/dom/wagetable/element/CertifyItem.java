/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.element;

import lombok.Getter;

/**
 * The Class CertifyItem.
 */
@Getter
public class CertifyItem extends CodeItem {

	/**
	 * Instantiates a new certify item.
	 *
	 * @param certifyCode the certify code
	 * @param uuid the uuid
	 */
	public CertifyItem(String certifyCode, String uuid) {
		super(certifyCode, uuid);
	}

}
