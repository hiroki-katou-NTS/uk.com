package nts.uk.ctx.sys.portal.app.find.toppagesetting;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.sys.portal.app.find.toppage.TopPageNewDto;

@Getter
@Setter
@NoArgsConstructor
public class DisplayInTopPage {
	
	private List<List<?>> listLayout;
	
	private String urlLayout1;
	
	private Integer	layoutDisplayType;
	
	private TopPageNewDto topPage;
}
