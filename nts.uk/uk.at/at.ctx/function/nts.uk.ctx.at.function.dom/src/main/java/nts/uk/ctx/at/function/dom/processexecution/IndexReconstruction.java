package nts.uk.ctx.at.function.dom.processexecution;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;

/**
 * インデックス再構成
 * @author ngatt-nws
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class IndexReconstruction extends DomainObject {
	
	/* 統計情報を更新する */
	private NotUseAtr updateStats;
	
	/* 使用区分 */
	private NotUseAtr classificationOfUse;
	
	/* カテゴリリスト */
	private IndexReconstructionCategoryNO categoryNo;
}
