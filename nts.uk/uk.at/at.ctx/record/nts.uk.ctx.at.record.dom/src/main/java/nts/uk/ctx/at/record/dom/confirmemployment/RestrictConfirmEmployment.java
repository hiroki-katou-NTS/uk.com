package nts.uk.ctx.at.record.dom.confirmemployment;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * refactor 5
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.就業確定.就業確定の機能制限
 * @author Doan Duy Hung
 *
 */
@Getter
public class RestrictConfirmEmployment implements DomainAggregate {
	
	/**
	 * 会社ID
	 */
	private String companyID;
	
	/**
	 * 就業確定を行う
	 */
	private boolean confirmEmployment;
	
	public RestrictConfirmEmployment(String companyID, boolean confirmEmployment) {
		this.companyID = companyID;
		this.confirmEmployment = confirmEmployment;
	}
}
