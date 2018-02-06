package nts.uk.ctx.pereg.ws.common;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pereg.app.find.common.ComboBoxParam;
import nts.uk.ctx.pereg.app.find.common.ComboBoxRetrieveFactory;
import nts.uk.shr.pereg.app.ComboBoxObject;

@Path("ctx/pereg/person/common")
@Produces("application/json")
public class FlexibleComboBoxWebService extends WebService{

	@Inject
	private ComboBoxRetrieveFactory comboBoxFactory;
	
	@POST
	@Path("getFlexComboBox")
	public List<ComboBoxObject> getFlexibleComboBox(ComboBoxParam comboBoxParam) {
		return comboBoxFactory.getFlexibleComboBox(comboBoxParam);
	}
	
}
