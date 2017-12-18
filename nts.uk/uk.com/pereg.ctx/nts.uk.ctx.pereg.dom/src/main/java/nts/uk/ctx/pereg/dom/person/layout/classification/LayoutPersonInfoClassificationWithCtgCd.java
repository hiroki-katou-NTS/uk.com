package nts.uk.ctx.pereg.dom.person.layout.classification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LayoutPersonInfoClassificationWithCtgCd extends LayoutPersonInfoClassification {

	private String personInfoCategoryCD;

	public static LayoutPersonInfoClassificationWithCtgCd createFromJavaType(String layoutID, int dispOrder,
			String personInfoCategoryID, int layoutItemType, String personInfoCategoryCD) {

		return new LayoutPersonInfoClassificationWithCtgCd(layoutID, dispOrder, personInfoCategoryID, layoutItemType,
				personInfoCategoryCD);

	}

	public LayoutPersonInfoClassificationWithCtgCd(String layoutID, int dispOrder, String personInfoCategoryID,
			int layoutItemType, String personInfoCategoryCD) {
		super(layoutID, new DispOrder(dispOrder), personInfoCategoryID,
				EnumAdaptor.valueOf(layoutItemType, LayoutItemType.class));
		this.personInfoCategoryCD = personInfoCategoryCD;
	}

}
