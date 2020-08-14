package nts.uk.screen.at.ws.shiftmanagement.shifttable;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.screen.at.app.shiftmanagement.shifttable.Ksu001uScreenQuery;
import nts.uk.screen.at.app.shiftmanagement.shifttable.PublicInfoOganizationDto;

/**
 * 
 * @author quytb
 *
 */
@Path("screen/at/shiftmanagement/shifttable")
@Produces(MediaType.APPLICATION_JSON)
public class Ksu001uWs {
	@Inject
	private Ksu001uScreenQuery ksu001uScreenQuery;

	@POST
	@Path("getPublicInfoOrg")
	public PublicInfoOganizationDto getPublicShiftTable(Ksu001uRequest request) {
		TargetOrganizationUnit unit = TargetOrganizationUnit.valueOf(request.getUnit());
		TargetOrgIdenInfor targetOrgIdenInfor = new TargetOrgIdenInfor(unit, Optional.of(request.getWorkplaceId()),
				Optional.of(request.getWorkplaceGroupId()));
		
		return ksu001uScreenQuery.getPublicInfoOganization(targetOrgIdenInfor, request.toDate());
	}
}
