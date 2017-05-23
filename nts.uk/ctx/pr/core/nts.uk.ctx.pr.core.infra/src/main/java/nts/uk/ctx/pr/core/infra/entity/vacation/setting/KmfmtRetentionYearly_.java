package nts.uk.ctx.pr.core.infra.entity.vacation.setting;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(KmfmtRetentionYearly.class)
public class KmfmtRetentionYearly_ {

	public static volatile SingularAttribute<KmfmtRetentionYearly, Short> yearAmount;
	public static volatile SingularAttribute<KmfmtRetentionYearly, Short> maxDaysRetention;
	public static volatile SingularAttribute<KmfmtRetentionYearly, String> cid;

}