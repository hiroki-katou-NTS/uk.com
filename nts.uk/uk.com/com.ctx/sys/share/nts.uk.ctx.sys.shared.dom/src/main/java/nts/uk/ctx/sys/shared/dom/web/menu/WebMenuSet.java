package nts.uk.ctx.sys.shared.dom.web.menu;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebMenuSet {
	
	private List<WebMenuCategory> categories;
	
	public WebMenuSet filter() {
		// TODO: Filter categories and its items by permission context
		//       then return new WebMenuSet with filtered categories and items.
		return new WebMenuSet();
	}
}
