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

		List<String> listPid = param.listPid;
		String pid = param.pid;
		GeneralDateTime baseDate = GeneralDateTime.legacyDateTime(param.baseDate.date()).addMonths(-1);

		if (pid == null || pid == "") {
			ListForeignerResidence result = domain.getListForeignerResidenceHistoryInforItem(listPid, baseDate);
			return result;

		} else {
			ListForeignerResidence result = domain.getListForeignerResidenceHistoryInforItem(listPid, baseDate);
			domain.setListForeignerResidence(result);
			ForeignerResidenceHistoryInforItem mItem1 = domain.getForeignerResidenceHistoryInforItem(param.pid,
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
