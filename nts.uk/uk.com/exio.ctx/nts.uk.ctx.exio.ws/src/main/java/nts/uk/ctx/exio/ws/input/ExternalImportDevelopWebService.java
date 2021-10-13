package nts.uk.ctx.exio.ws.input;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nts.uk.ctx.exio.app.input.develop.diagnose.DiagnoseExternalImportConstants;
import nts.uk.ctx.exio.app.input.develop.diagnose.DiagnoseResult;
import nts.uk.ctx.exio.app.input.develop.workspace.ImportWorkspaces;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;

@Path("develop/exio/input")
@Produces(MediaType.APPLICATION_JSON)
public class ExternalImportDevelopWebService {
	
	@Inject
	private DiagnoseExternalImportConstants diagnose;
	
	@GET
	@Path("diagnose")
	public DiagnoseResult diagnose() {
		return diagnose.diagnose();
	}
	
	@Inject
	private ImportWorkspaces importWorkspaces;

	@GET
	@Path("import-workspace/{domainId}")
	public Response importWorkspace(@PathParam("domainId") int domainId) {
		
		String csv = importWorkspaces.createCsvItems(ImportingDomainId.valueOf(domainId));

        return Response.ok(csv)
				  .header(
						  "Content-Disposition",
						  "attachment; filename=XIMCT_WORKSPACE_ITEM_ " + domainId + "_.csv")
				  .build();
	}
}
