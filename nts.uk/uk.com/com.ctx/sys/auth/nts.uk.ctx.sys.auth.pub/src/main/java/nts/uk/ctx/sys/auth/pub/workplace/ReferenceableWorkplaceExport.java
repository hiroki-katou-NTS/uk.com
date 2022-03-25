package nts.uk.ctx.sys.auth.pub.workplace;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.sys.auth.pub.wkpmanager.ReferenceableWorkplace;

@AllArgsConstructor
@Data
/**
 * 
 * @author sonnlb
 *
 *         参照可能職場
 */
public class ReferenceableWorkplaceExport {
	// 職場リスト
	private List<String> workplaceList;
	// 所属情報

	private Map<String, String> affiliationInformation;

	public static ReferenceableWorkplaceExport fromDomain(ReferenceableWorkplace wkp) {

		return new ReferenceableWorkplaceExport(wkp.getWorkplaceList(), wkp.getAffiliationInformation());
	}
}
