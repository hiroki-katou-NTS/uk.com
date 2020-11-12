package nts.uk.ctx.at.function.dom.processexecution;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import java.util.List;
import java.util.stream.Collectors;
//import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;

/**
 * The class Index reconstruction.<br>
 * インデックス再構成
 *
 * @author ngatt-nws
 */
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class IndexReconstruction extends DomainObject {

	/**
	 * The Update statistics.<br>
	 * 統計情報を更新する
	 **/
	private NotUseAtr updateStatistics;

	/**
	 * The Index reorganization attribute.<br>
	 * 使用区分
	 **/
	private NotUseAtr indexReorgAttr;

	/**
	 * The Category list.<br>
	 * カテゴリリスト
	 **/
	private List<IndexReconstructionCategoryNO> categoryList;

	/**
	 * Instantiates a new <code>IndexReconstruction</code>.
	 *
	 * @param updateStatistics the update statistics
	 * @param indexReorgAttr   the index reorganization attribute
	 * @param categoryList     the category list
	 */
	public IndexReconstruction(int updateStatistics, int indexReorgAttr, List<Integer> categoryList) {
		this.updateStatistics = EnumAdaptor.valueOf(updateStatistics, NotUseAtr.class);
		this.indexReorgAttr = EnumAdaptor.valueOf(indexReorgAttr, NotUseAtr.class);
		this.categoryList = categoryList.stream().map(IndexReconstructionCategoryNO::new).collect(Collectors.toList());
	}

}
