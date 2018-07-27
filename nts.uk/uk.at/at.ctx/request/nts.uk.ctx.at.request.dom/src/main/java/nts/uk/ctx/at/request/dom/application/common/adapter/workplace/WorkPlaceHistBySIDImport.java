package nts.uk.ctx.at.request.dom.application.common.adapter.workplace;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 社員指定期間所属職場履歴
 * @author hoatt
 *
 */
@Getter
@AllArgsConstructor
public class WorkPlaceHistBySIDImport {
	/**社員ID*/
	private String sID;
	/**職場リスト*/
	private List<WkpInfo> lstWkpInfo;
}
