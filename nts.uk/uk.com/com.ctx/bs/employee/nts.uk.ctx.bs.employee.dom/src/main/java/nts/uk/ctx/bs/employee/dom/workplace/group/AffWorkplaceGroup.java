package nts.uk.ctx.bs.employee.dom.workplace.group;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.基幹.社員.職場.職場グループ.職場グループ所属情報
 * @author phongtq
 *
 */
@Getter
public class AffWorkplaceGroup implements DomainAggregate {

	/** 職場グループID */
	private final String WKPGRPID;
	
	/** 職場ID */
	private String WKPID;
	
	public AffWorkplaceGroup(String wKPGRPID, String wKPID) {
		super();
		WKPGRPID = wKPGRPID;
		WKPID = wKPID;
	}
	
	public AffWorkplaceGroup(String wKPGRPID, String wKPID, String CID) {
		super();
		WKPGRPID = wKPGRPID;
		WKPID = wKPID;
	}
}
