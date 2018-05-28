package nts.uk.ctx.sys.auth.infra.entity.password.changelog;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDateTime;

/**
 * The Class SacdtPasswordChangeLogPK_.
 */
@StaticMetamodel(SacdtPasswordChangeLogPK.class)
public class SacdtPasswordChangeLogPK_ {

	/** The user id. */
	public static volatile SingularAttribute<SacdtPasswordChangeLogPK, String> userId;
	
	/** The login id. */
	public static volatile SingularAttribute<SacdtPasswordChangeLogPK, String> loginId;
	
	/** The modified datetime. */
	public static volatile SingularAttribute<SacdtPasswordChangeLogPK, GeneralDateTime> modifiedDatetime;
}
