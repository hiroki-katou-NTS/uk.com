package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.referstoinformation;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * AR: 	社員の静脈情報
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.指情報.社員の静脈情報
 * @author chungnt
 *
 */

@Getter
@AllArgsConstructor
public class EmployeeVeinInformation implements DomainAggregate {

	/**
	 * 会社ID
	 */
	private final String companyId;

	/** 
	 * 社員ID
	 */
	private final String sid;
	
	/**
	 * 	指情報一覧
	 */
	private List<FingerVeininformation> veininformations;
	
}
