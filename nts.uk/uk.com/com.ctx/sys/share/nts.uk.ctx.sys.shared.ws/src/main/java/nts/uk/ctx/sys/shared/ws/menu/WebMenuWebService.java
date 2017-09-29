package nts.uk.ctx.sys.shared.ws.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.sys.shared.dom.web.menu.WebMenuService;
import nts.uk.ctx.sys.shared.dom.web.menu.WebMenuSet;

@Path("/shared/menu")
@Produces("application/json")
public class WebMenuWebService {

	@Inject
	private WebMenuService menuService;
	
	@POST
	@Path("get")
	public WebMenuSet get() {
		return menuService.get();
	}
	
	// TODO: Temp
	@POST
	@Path("companies")
	public List<String> companyName() {
		return Arrays.asList("日通システム株式会社", "KSB", "日通システムベトナム");
	}
	
	@POST
	@Path("username")
	public StringData userName() {
		return new StringData("日通　太郎");
	}
	
	@POST
	@Path("program")
	public StringData programName() {
		return new StringData("CMM009　部門・職場の登録");
	}
	
	@Setter @Getter
	public class StringData {
		private String value;
		public StringData(String name) {
			this.value = name;
		}
	}
}
