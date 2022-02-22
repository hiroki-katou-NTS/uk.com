package nts.uk.screen.at.ws.kmp.kmp001;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.query.kmp.kmp001.j.ExportQrcodeByCardNumber;
import nts.uk.screen.at.app.query.kmp.kmp001.j.GetExtractedEmployeeCardNoInput;

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
	public void getStatusEmployee (GetExtractedEmployeeCardNoInput input) {
		this.exportQR.exportQRCode(input);
	}

}
