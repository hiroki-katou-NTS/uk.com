package nts.uk.ctx.exio.ws.exi.codeconvert;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.exio.dom.exi.codeconvert.CdConvertDetails;

@Path("exio/exi/codeconvert")
@Produces("application/json")
public class CdConvertDetailsWebService {
	@POST
	@Path("getCodeConvertDetails/{convertCode}")
	public List<CdConvertDetails> getCodeConvertDetails(@PathParam("convertCode") String convertCode) {
		List<CdConvertDetails> list = new ArrayList<>();
		for (int i = 1; i < 5; i++) {
			list.add(new CdConvertDetails("1", "001", i, String.valueOf(i + 1), String.valueOf(i)));
		}
		return list;
	}
}
