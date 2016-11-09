package nts.uk.ctx.pr.proto.dom.layoutmaster;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.proto.dom.enums.CategoryAtr;

/**
 * LayoutMasterCategory valueObject
 * @author lamvt
 *
 */
public class LayoutMasterCategory extends DomainObject {

	/** カテゴリ区分*/
	@Getter
	private CategoryAtr categoryAttribute;
	
	/** カテゴリ表示位置*/
	@Getter
	private CategoryPosition categoryPosition;
	
	@Getter
	private List<LayoutMasterLine> layoutMasterLines;

	public LayoutMasterCategory(CategoryAtr categoryAttribute, CategoryPosition categoryPosition,
			List<LayoutMasterLine> layoutMasterLines) {
		super();
		this.categoryAttribute = categoryAttribute;
		this.categoryPosition = categoryPosition;
		this.layoutMasterLines = layoutMasterLines;
	}
	
}
