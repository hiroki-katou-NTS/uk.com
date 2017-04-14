/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.address;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class XxxxxAddress_.
 */
@StaticMetamodel(XxxxxAddress.class)
public class XxxxxAddress_ {

	/** The id. */
	public static volatile SingularAttribute<XxxxxAddress, Integer> id;

	/** The zip code. */
	public static volatile SingularAttribute<XxxxxAddress, String> zipCode;

	/** The prefecture. */
	public static volatile SingularAttribute<XxxxxAddress, String> prefecture;

	/** The city. */
	public static volatile SingularAttribute<XxxxxAddress, String> city;

	/** The town. */
	public static volatile SingularAttribute<XxxxxAddress, String> town;

}