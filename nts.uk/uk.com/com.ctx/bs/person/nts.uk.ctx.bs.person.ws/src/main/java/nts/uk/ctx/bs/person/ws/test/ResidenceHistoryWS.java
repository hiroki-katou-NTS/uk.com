package nts.uk.ctx.bs.person.ws.test;

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
	public void testDomain(){
		
		List<String> listPID = Arrays.asList("ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570",
											 "8f9edce4-e135-4a1e-8dca-ad96abe405d6",
											 "9787c06b-3c71-4508-8e06-c70ad41f042a",
											 "62785783-4213-4a05-942b-c32a5ffc1d63",
											 "4859993b-8065-4789-90d6-735e3b65626b",
											 "aeaa869d-fe62-4eb2-ac03-2dde53322cb5");
		GeneralDateTime baseDate = GeneralDateTime.now();
		ListForeignerResidence result =  domain.getListForeignerResidenceHistoryInforItem(listPID, baseDate);
		
		domain.setListForeignerResidence(result);
		
		ForeignerResidenceHistoryInforItem mItem1 = domain.getForeignerResidenceHistoryInforItem("ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570", baseDate);
		
		ForeignerResidenceHistoryInforItem mItem2 = domain.getForeignerResidenceHistoryInforItem("ae7fe82e-a7bd-4ce3-adeb-5cd403a9d599", baseDate);
		
		System.out.println("helllo" + mItem1 + mItem2);
	}
	
	
}
