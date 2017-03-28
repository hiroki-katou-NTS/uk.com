package nts.uk.ctx.basic.dom.organization.position;

import lombok.AllArgsConstructor;
@AllArgsConstructor
public enum InChargeAtr {
	/**0: 担当者権限ではない*/
	NotPersonsResponsibleAuthority(0),
	/**1: 担当者権限である */
	PersonsResponsibleAuthority(1);
	
	public final int value;
}