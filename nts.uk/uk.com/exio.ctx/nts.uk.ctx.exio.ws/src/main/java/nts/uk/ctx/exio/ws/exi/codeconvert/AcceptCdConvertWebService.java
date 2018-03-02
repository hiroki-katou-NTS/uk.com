package nts.uk.ctx.exio.ws.exi.codeconvert;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.exio.dom.exi.codeconvert.AcceptCdConvert;

@Path("exio/exi/codeconvert")
@Produces("application/json")
public class AcceptCdConvertWebService {

	@POST
	@Path("getCodeConvert")
	public List<AcceptCdConvert> getCodeConvert() {
		List<AcceptCdConvert> list = new ArrayList<>();
		for (int i = 1; i < 10; i++) {
			list.add(new AcceptCdConvert("1", "00" + i, "Item " + i, i % 2 == 0 ? 1 : 0, null));
		}
		return list;
	}

	@POST
	@Path("getAcceptCodeConvert/{convertCode}")
	public AcceptCdConvert getAcceptCodeConvert(@PathParam("convertCode") String convertCode) {
		List<AcceptCdConvert> list = new ArrayList<>();
		for (int i = 1; i < 10; i++) {
			list.add(new AcceptCdConvert("1", "00" + i, "Item " + i, i % 2 == 0 ? 1 : 0, null));
		}
		AcceptCdConvert codeConvert = list.stream().filter(p -> p.getConvertCd().equals(convertCode)).findFirst()
				.orElse(null);
		return codeConvert;
	}
}
