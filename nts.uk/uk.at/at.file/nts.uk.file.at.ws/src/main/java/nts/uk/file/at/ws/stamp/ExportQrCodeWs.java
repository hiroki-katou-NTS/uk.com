package nts.uk.file.at.ws.stamp;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.file.at.app.export.statement.stamp.ExportQrcodeByCardNumber;
import nts.uk.file.at.app.export.statement.stamp.GetExtractedEmployeeCardNoInput;

/**
 * 
 * @author tutt
 *
 */
@Path("screen/at/kmp001/j")
@Produces("application/json")
public class ExportQrCodeWs extends WebService {
	
	@Inject
	private ExportQrcodeByCardNumber exportQR;
	
	@POST
	@Path("exportQR")
	public ExportServiceResult getStatusEmployee (GetExtractedEmployeeCardNoInput input) {
		return this.exportQR.start(input);
	}

}
