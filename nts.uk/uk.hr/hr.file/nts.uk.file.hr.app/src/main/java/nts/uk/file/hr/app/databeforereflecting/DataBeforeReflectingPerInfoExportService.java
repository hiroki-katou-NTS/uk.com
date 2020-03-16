package nts.uk.file.hr.app.databeforereflecting;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.screen.hr.app.databeforereflecting.find.DataBeforeReflectResultDto;
import nts.uk.screen.hr.app.databeforereflecting.find.DatabeforereflectingFinder;

@Stateless
public class DataBeforeReflectingPerInfoExportService extends ExportService<Object> {
	@Inject
	private DatabeforereflectingFinder finder;
	
	@Inject
	private DataBeforeReflectingPerInfoGenerator generator;

	@Override
	protected void handle(ExportServiceContext<Object> context) {
		DataBeforeReflectResultDto dto = this.finder.getDataBeforeReflect();
		
		this.generator.generate(context.getGeneratorContext(), dto);
	}
}
