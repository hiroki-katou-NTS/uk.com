package nts.uk.ctx.at.request.app.command.application.remainingnumber.checkfunc;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.at.request.dom.application.remainingnumer.ExcelInforCommand;
import nts.uk.ctx.at.request.dom.application.remainingnumer.RemainingNumberGenerator;

@Stateless
public class RemainingNumberExportExcel extends ExportService<List<ExcelInforCommand>> {

	@Inject
	private RemainingNumberGenerator remainingNumberGenerator;

	@Override
	protected void handle(ExportServiceContext<List<ExcelInforCommand>> context) {
		// TODO Auto-generated method stub
		List<ExcelInforCommand> listOuput = context.getQuery();
		Collections.sort(listOuput, Comparator.comparing(ExcelInforCommand :: getEmployeeCode));
		this.remainingNumberGenerator.generate(context.getGeneratorContext(), listOuput);
	}

}
