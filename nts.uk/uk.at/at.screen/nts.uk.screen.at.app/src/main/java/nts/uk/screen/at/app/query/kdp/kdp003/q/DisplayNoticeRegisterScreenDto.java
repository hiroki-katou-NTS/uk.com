package nts.uk.screen.at.app.query.kdp.kdp003.q;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.sys.portal.dom.notice.adapter.WorkplaceInfoImport;

/**
 * 打刻入力の作成するお知らせ登録の画面を表示する
 * 
 * @author tutt
 *
 */
@Data
public class DisplayNoticeRegisterScreenDto {

	/**
	 * 作成者の職場（職場ID、職場コード、職場表示名）
	 */
	private WorkplaceInfoImport workplaceInfo;

	/**
	 * ＜List＞対象職場（職場ID、職場コード、職場表示名）（Optional）
	 */
	private List<WorkplaceInfoImport> targetWkps;
}
