package nts.uk.ctx.pr.core.pub.wageprovision.processdatecls.algorithm;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
/**
 * 締め日リス Export
 * 
 * @author sonnlb
 *
 */
public class ClosureDateExport {

	/**
	 * 雇用コード
	 */
	private List<String> employmentCodes;
	
	/**
	 * 参照日
	 */
	
	private List<Integer> referenceDates;
}
