package nts.uk.ctx.at.record.dom.adapter.workplace;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
/**
 * 
 * @author sonnlb
 *
 *         参照可能職場
 */
public class ReferenceableWorkplaceImport {

	// 職場リスト
	private List<String> workplaceList;
	// 所属情報

	private Map<String, String> affiliationInformation;

}
