package nts.uk.ctx.pr.proto.dom.paymentdata.dataitem.position;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.proto.dom.enums.CategoryAtr;

/**
 * 番目印字カテゴリ
 * 
 * @author vunv
 *
 */
public class PrintPositionCategory extends DomainObject {

	/**
	 * 位置
	 */
	@Getter
	private final CategoryAtr position;

	/**
	 * 行
	 */
	@Getter
	private final PrintPosCatalogLines lines;

	public PrintPositionCategory(CategoryAtr position, PrintPosCatalogLines lines) {
		super();
		this.position = position;
		this.lines = lines;
	}

}
