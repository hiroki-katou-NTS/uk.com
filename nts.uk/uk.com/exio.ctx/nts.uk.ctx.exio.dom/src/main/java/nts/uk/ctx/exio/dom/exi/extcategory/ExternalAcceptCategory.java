package nts.uk.ctx.exio.dom.exi.extcategory;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.enumcommon.NotUseAtr;
/**
 * 外部受入カテゴリ
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExternalAcceptCategory extends AggregateRoot {
	/**外部受入カテゴリID	 */
	private int categoryId;
	/**カテゴリ名	 */
	private String categoryName;
	/**
	 * 就業システム区分
	 */
	private NotUseAtr atSysFlg;
	/**
	 * 人事システム区分
	 */
	private NotUseAtr persSysFlg;
	/**
	 * 給与システム区分
	 */
	private NotUseAtr salarySysFlg;
	/**
	 * オフィスヘルパー区分
	 */
	private NotUseAtr officeSysFlg;
	/**
	 * 新規可能区分
	 */
	private NotUseAtr insertFlg;
	/**
	 * 削除可能区分
	 */
	private NotUseAtr deleteFlg;
	/**
	 * 外部受入カテゴリ項目データ
	 */
	private List<ExternalAcceptCategoryItem> lstAcceptItem;
}
