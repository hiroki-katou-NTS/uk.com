/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.executionlog;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtSchCreator_.
 */
@StaticMetamodel(KscmtSchCreator.class)
public class KscmtSchCreator_ {
	
	/** The kscmt sch creator PK. */
    public static volatile SingularAttribute<KscmtSchCreator, KscmtSchCreatorPK> kscmtSchCreatorPK;
    
    /** The exe status. */
    public static volatile SingularAttribute<KscmtSchCreator, Integer> exeStatus;
    
}
