package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
/**
 * 職位別のサーチ設定
 * @author dudt
 *
 */
@Getter
@AllArgsConstructor
public class JobtitleSearchSet extends AggregateRoot{
	/**会社ID*/
	private String companyId;
	/**職位ID	 */
	private String jobId;
	
	private SearchSetFlg searchSetFlg;
	
	public static JobtitleSearchSet createSimpleFromJavaType(String companyId,
			String jobId,
			int searchSetFlg) {
		return new JobtitleSearchSet(companyId,
				jobId,
				EnumAdaptor.valueOf(searchSetFlg, SearchSetFlg.class));
	}
	
	public boolean needsSearch() {
		return this.searchSetFlg == SearchSetFlg.TODO;
	}
}
