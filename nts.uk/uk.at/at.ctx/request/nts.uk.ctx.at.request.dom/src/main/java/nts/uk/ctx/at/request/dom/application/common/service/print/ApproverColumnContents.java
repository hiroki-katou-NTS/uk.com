package nts.uk.ctx.at.request.dom.application.common.service.print;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.印刷内容を取得する.印字する承認者を取得する.承認者欄の内容
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApproverColumnContents {
	
	/**
	 * 承認者
	 */
	private List<ApproverPrintDetails> approverPrintDetailsLst;
}
