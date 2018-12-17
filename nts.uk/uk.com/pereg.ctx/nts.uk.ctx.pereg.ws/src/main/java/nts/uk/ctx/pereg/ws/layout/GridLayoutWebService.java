package nts.uk.ctx.pereg.ws.layout;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pereg.app.command.person.setting.matrix.GridSettingCommand;
import nts.uk.ctx.pereg.app.command.person.setting.matrix.matrixdisplayset.CreateMatrixDisplaySetCommandHandler;
import nts.uk.ctx.pereg.app.command.person.setting.matrix.personinfomatrixitem.CreatePersonInfoMatrixItemCommandHandler;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.GridEmployeeDto;
import nts.uk.ctx.pereg.app.find.person.setting.matrix.matrixdisplayset.MatrixDisplaySetFinder;
import nts.uk.ctx.pereg.app.find.person.setting.matrix.personinfomatrixitem.DisplayItemColumnSetFinder;
import nts.uk.ctx.pereg.app.find.processor.GridPeregProcessor;
import nts.uk.ctx.pereg.dom.person.setting.matrix.matrixdisplayset.MatrixDisplaySetting;
import nts.uk.ctx.pereg.dom.person.setting.matrix.personinfomatrixitem.PersonInfoMatrixItem;
import nts.uk.shr.pereg.app.find.PeregGridQuery;

@Path("ctx/pereg/grid-layout")
@Produces(MediaType.APPLICATION_JSON)
public class GridLayoutWebService extends WebService {

	@Inject
	GridPeregProcessor gridProcessor;

	@Inject
	MatrixDisplaySetFinder mdsFinder;

	@Inject
	DisplayItemColumnSetFinder dicFinder;

	@Inject
	CreateMatrixDisplaySetCommandHandler cmdsHandler;

	@Inject
	CreatePersonInfoMatrixItemCommandHandler cpimHandler;

	@POST
	@Path("get-data")
	public GridEmployeeDto get(PeregGridQuery query) {
		return gridProcessor.getGridLaoyout(query);
	}

	@POST
	@Path("save-data")
	public void save(Object command) {

	}

	@POST
	@Path("get-setting/{categoryId}")
	public Object getFixedSetting(@PathParam("categoryId") String categoryId) {
		return new Object() {
			@SuppressWarnings("unused")
			public final List<PersonInfoMatrixItem> personInfoItems = dicFinder.get(categoryId);
			@SuppressWarnings("unused")
			public final MatrixDisplaySetting matrixDisplay = mdsFinder.findByKey().orElse(null);
		};
	}

	@POST
	@Path("save-setting")
	public void saveFixedSetting(GridSettingCommand command) {
		cpimHandler.handle(command.getPersonInfoItems());
		cmdsHandler.handle(command.getMaxtrixDisplays());
	}
}
