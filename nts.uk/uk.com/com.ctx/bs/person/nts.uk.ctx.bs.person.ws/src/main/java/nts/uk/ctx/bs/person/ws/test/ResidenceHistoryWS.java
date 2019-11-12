package nts.uk.ctx.bs.person.ws.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.bs.person.dom.person.foreigner.residence.hisinfo.ForeignerResidenceHistoryInforItem;
import nts.uk.ctx.bs.person.dom.person.foreigner.residence.hisinfo.ListForeignerResidence;

@Path("residencehistory")
@Produces(MediaType.APPLICATION_JSON)
public class ResidenceHistoryWS {

	@Inject
	private ForeignerResidenceHistoryInforItem domain;

	@POST
	@Path("/get") // test service
	public ListForeignerResidence testDomain(ParamTest param) {

		List<String> listSid = param.listSid;
		String sid = param.sid;
		GeneralDateTime baseDate = GeneralDateTime.legacyDateTime(param.baseDate.date());

		if (sid == null || sid == "") {
			ListForeignerResidence result = domain.getListForeignerResidenceHistoryInforItem(listSid, baseDate);
			return result;

		} else {
			ListForeignerResidence result = domain.getListForeignerResidenceHistoryInforItem(listSid, baseDate);
			domain.setListForeignerResidence(result);
			ForeignerResidenceHistoryInforItem mItem1 = domain.getForeignerResidenceHistoryInforItem(param.sid,
					baseDate);

			if (mItem1 == null) {
				result.setListForeignerResidenceHistoryInforItem(new ArrayList<ForeignerResidenceHistoryInforItem>());
			} else {
				result.setListForeignerResidenceHistoryInforItem(new ArrayList<ForeignerResidenceHistoryInforItem>());
				result.setListForeignerResidenceHistoryInforItem(Arrays.asList(mItem1));
			}

			return result;

		}
	}
}
