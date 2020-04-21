package nts.uk.file.com.infra.employee.workplace.workplacegroup;

import java.io.OutputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.aspose.cells.Worksheet;

import approve.employee.workplace.workplacegroup.CreateUnsetWorkplaceGenerator;
import approve.employee.workplace.workplacegroup.CreateUnsetWorkplaceGeneratorExportDto;
import approve.employee.workplace.workplacegroup.OutputExportKSM007;
import approve.employee.workplace.workplacegroup.WorkplaceInforDto;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.layer.infra.file.export.WorkingFile;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.bs.company.dom.company.Company;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class AsposeCreateUnsetWorkplaceGenerator extends AsposeCellsReportGenerator implements CreateUnsetWorkplaceGenerator {
	@Inject
	private CompanyRepository companyRepo;
	
	private static final String TEMPLATE_FILE = "report/KSM007.xlsx";

//	private static final int START_ROW = 4;

	@Override
	public CreateUnsetWorkplaceGeneratorExportDto generate(FileGeneratorContext fileGeneratorContext,
			OutputExportKSM007 query) {
		String companyId = AppContexts.user().companyId();
		try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
			reportContext.getWorkbook().getWorksheets().get(0).setName(TextResource.localize("KSM007_1"));
			
			Optional<Company> optionalCompany = companyRepo.find(companyId);
			
			String companyName = optionalCompany.isPresent()?optionalCompany.get().getCompanyName().v():"";
			String headerA = TextResource.localize("KSM007_13","#Com_Workplace");
			String headerB = TextResource.localize("KSM007_14","#Com_Workplace");
			String headerC = TextResource.localize("KSM007_15","#Com_Workplace");
			List<WorkplaceInforDto> listWorkplaceInfor = query.getListWorkplaceInfor()
					.stream().sorted((x,y)->x.getWorkplaceCode().compareTo(y.getWorkplaceCode()))
					.collect(Collectors.toList());
			String titleName = query.getHeader().getTitle();
			GeneralDateTime dateNow = GeneralDateTime.now();
			String dateTime ="【対象期間】  "+ dateNow.toString("yyyy/MM/dd HH:mm");
			
			reportContext.setDataSource("item",listWorkplaceInfor );
			reportContext.setDataSource("titleName", titleName);
			reportContext.setDataSource("companyName", companyName);
			reportContext.setDataSource("headerA", headerA);
			reportContext.setDataSource("headerB", headerB);
			reportContext.setDataSource("headerC", headerC);
			reportContext.setDataSource("dateTime", dateTime);
			reportContext.getWorkbook().getWorksheets().get(0).getPageSetup().setHeader(0,"&\"ＭＳ ゴシック\"&9" + companyName);
			reportContext.getWorkbook().getWorksheets().get(0).getPageSetup().setHeader(1, "&\"ＭＳ ゴシック\"&16"+titleName);
			String fileName = "未設定職場リスト.xlsx";
			OutputStream outputStream = this.createNewFile(fileGeneratorContext, fileName);
			reportContext.processDesigner();
			reportContext.saveAsExcel(outputStream);
			WorkingFile workingFile = fileGeneratorContext.getWorkingFiles().get(0);
			return new CreateUnsetWorkplaceGeneratorExportDto(workingFile.getTempFile().getPath(), fileName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
