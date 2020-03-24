package nts.uk.file.at.infra.statement.stamp;

import java.io.OutputStream;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.layer.infra.file.export.WorkingFile;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.app.find.statement.export.DataExport;
import nts.uk.ctx.at.function.dom.alarm.export.AlarmExportDto;
import nts.uk.ctx.at.function.dom.statement.StampingOutputItemSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.RetrieveNoStampCardRegisteredService;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.file.at.app.export.statement.stamp.StampGeneratorExportDto;
import nts.uk.file.at.app.export.statement.stamp.OutputConditionListOfStampGenerator;
import nts.uk.file.at.app.export.statement.stamp.OutputConditionListOfStampQuery;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class AsposeOutputConditionListOfStampGenerator extends AsposeCellsReportGenerator implements OutputConditionListOfStampGenerator {
	
	private static final String TEMPLATE_FILE = "report/KAL001-アラームリスト(個人別).xlsx";
	
	private static final String COMPANY_ERROR = "Company is not found!!!!";
	
	/** The stamping output item set repository. */
	
	@Inject
	private RetrieveNoStampCardRegisteredService stampCardService;
	
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;

	@Override
	public StampGeneratorExportDto generate(FileGeneratorContext fileGeneratorContext,
			OutputConditionListOfStampQuery query) {
		try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
			//setHeaderAndHeaderColumn(reportContext,currentAlarmCode);
			// set data source named "item"
			reportContext.setDataSource("item", query);
			// process data binginds in template
			reportContext.processDesigner();

			// save as Excel file
			GeneralDateTime dateNow = GeneralDateTime.now();
			String dateTime = dateNow.toString("yyyyMMddHHmmss");
			String fileName = "ListOfStamp_" + dateTime + ".xlsx";
			OutputStream outputStream = this.createNewFile(fileGeneratorContext, fileName);
			reportContext.saveAsExcel(outputStream);
			WorkingFile workingFile = fileGeneratorContext.getWorkingFiles().get(0);
			StampGeneratorExportDto stampGeneratorExportDto = new StampGeneratorExportDto(workingFile.getTempFile().getPath(), fileName);
			return stampGeneratorExportDto;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public void generateExcelScreen(FileGeneratorContext generatorContext,
			List<OutputConditionListOfStampQuery> dataSource) {
		// TODO Auto-generated method stub
		
	}
	

		
	}
	


