package nts.uk.ctx.pereg.dom.person.layout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewLayout extends AggregateRoot {

	private String companyId;

	private String layoutID;

	private LayoutCode layoutCode;

	private LayoutName layoutName;

	public static NewLayout createFromJavaType(String companyId, String layoutID, String layoutCode,
			String layoutName) {
		return new NewLayout(companyId, layoutID, new LayoutCode(layoutCode), new LayoutName(layoutName));
	}
}
