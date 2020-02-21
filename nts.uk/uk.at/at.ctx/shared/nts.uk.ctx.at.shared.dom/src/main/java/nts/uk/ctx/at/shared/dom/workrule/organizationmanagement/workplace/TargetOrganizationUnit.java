/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace;

/**
 * 対象組織の単位
 * 
 * @author tutk
 *
 */
public enum TargetOrganizationUnit {

	/**
	 * 職場
	 */
	WORKPLACE(0),

	/**
	 * 職場グループ
	 */
	WORKPLACE_GROUP(1);

	/** The value. */
	public int value;

	/** The Constant values. */
	private final static TargetOrganizationUnit[] values = TargetOrganizationUnit.values();

	/**
	 * Instantiates a new closure id.
	 *
	 * @param value
	 *            the value
	 * @param description
	 *            the description
	 */
	private TargetOrganizationUnit(int value) {
		this.value = value;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the use division
	 */
	public static TargetOrganizationUnit valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (TargetOrganizationUnit val : TargetOrganizationUnit.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}