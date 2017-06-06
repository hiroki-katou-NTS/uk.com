package nts.uk.ctx.sys.portal.dom.webmenu.personaltying;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.sys.portal.dom.webmenu.WebMenuCode;

@AllArgsConstructor
public class PersonalTying extends AggregateRoot {
	
	@Getter
	private String companyCode;
    
	@Getter
	private WebMenuCode webMenuCode;
	
	@Getter
	private String employId;
}
