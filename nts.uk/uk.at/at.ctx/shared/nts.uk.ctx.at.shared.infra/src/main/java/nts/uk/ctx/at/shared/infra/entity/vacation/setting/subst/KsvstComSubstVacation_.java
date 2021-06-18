/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.subst;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KsvstComSubstVacation_.
 */
@Generated(value = "EclipseLink-2.5.2.v20140319-rNA", date = "2017-05-24T11:41:51")
@StaticMetamodel(KsvstComSubstVacation.class)
public class KsvstComSubstVacation_ {
	
	public static volatile SingularAttribute<KsvstComSubstVacation, Short> linkMngAtr;
	
	public static volatile SingularAttribute<KsvstComSubstVacation, Short> expDateMngMethod;
	
	/** The allow prepaid leave. */
	public static volatile SingularAttribute<KsvstComSubstVacation, Short> allowPrepaidLeave;

	/** The ins date. */
	public static volatile SingularAttribute<KsvstComSubstVacation, Date> insDate;

	/** The upd ccd. */
	public static volatile SingularAttribute<KsvstComSubstVacation, String> updCcd;

	/** The upd pg. */
	public static volatile SingularAttribute<KsvstComSubstVacation, String> updPg;

	/** The ins ccd. */
	public static volatile SingularAttribute<KsvstComSubstVacation, String> insCcd;

	/** The upd scd. */
	public static volatile SingularAttribute<KsvstComSubstVacation, String> updScd;

	/** The upd date. */
	public static volatile SingularAttribute<KsvstComSubstVacation, Date> updDate;

	/** The exclus ver. */
	public static volatile SingularAttribute<KsvstComSubstVacation, Integer> exclusVer;

	/** The expiration date set. */
	public static volatile SingularAttribute<KsvstComSubstVacation, Short> expirationDateSet;

	/** The ins scd. */
	public static volatile SingularAttribute<KsvstComSubstVacation, String> insScd;

	/** The is manage. */
	public static volatile SingularAttribute<KsvstComSubstVacation, Short> isManage;

	/** The ins pg. */
	public static volatile SingularAttribute<KsvstComSubstVacation, String> insPg;

	/** The cid. */
	public static volatile SingularAttribute<KsvstComSubstVacation, String> cid;

}