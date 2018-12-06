package nts.uk.ctx.pereg.app.command.filemanagement;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.infra.file.storage.StoredFileStreamService;
import nts.gul.excel.ExcelFileTypeException;
import nts.gul.excel.NtsExcelCell;
import nts.gul.excel.NtsExcelImport;
import nts.gul.excel.NtsExcelReader;
import nts.gul.excel.NtsExcelRow;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
@Stateless
public class CheckFileCommandHandler extends CommandHandler<CheckFileCommand>{
	
	@Inject
	private EmployeeDataMngInfoRepository employeeRepo;
	
//	@Inject
//	private EmployeePublisher  employeePub;
	
	@Inject
	private StoredFileStreamService fileStreamService;
	
	@Override
	protected void handle(CommandHandlerContext<CheckFileCommand> context) {
		CheckFileCommand command = context.getCommand();
		try {
			getAllRecord(command);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public List<String> getAllRecord(CheckFileCommand command) throws Exception {
		// get excel header
		try {
			InputStream inputStream = this.fileStreamService.takeOutFromFileId(command.getFileId());
			NtsExcelImport excelReader = NtsExcelReader.read(inputStream);
			List<nts.gul.excel.ExcelHeader> header = excelReader.headers();
			List<NtsExcelRow> rows = excelReader.rows();
			List<String> colums = this.getColumsChange(header);
			List<String>  employeeIds = this.getEmployeeIds(rows);
			if(!colums.containsAll(command.getColumnChange())) {
				throw new Exception("Msg_723");
			}
			
			if(!employeeIds.isEmpty()) {
				// RequestList539 (RequestList338)
//				Optional<NarrowEmpByReferenceRange> narrow  = this.employeePub.findByEmpId(employeeIds, 8 );
				
			}
		} catch (ExcelFileTypeException e1) {
			e1.printStackTrace();
		}
		return new ArrayList<>();
	}
	
	private List<String> getColumsChange(List<nts.gul.excel.ExcelHeader> header){
		List<String> colChange = new ArrayList<>();
		header.stream().forEach(c ->{
//			NtsExcelCell mainCells = c.getMains();
//			ValueWithType valueWithType = mainCells.getValue();
//			colChange.add(valueWithType.getText());
		});
		return colChange;
	}
	
	private List<String> getEmployeeIds(List<NtsExcelRow> rows){
		String companyId = AppContexts.user().companyId();
		List<String> employeeCodes = new ArrayList<>();
		rows.stream().forEach(c ->{
			List<NtsExcelCell> cells = c.cells();
//			String employeeName = cells.get(0).getValue().getText(), CPS003_28
			nts.gul.excel.ExcelHeader header = cells.get(0).getHeader();
//			String nameCol = header.getMains().getText();
//			if(namCol.equals(TextResource.localize("CPS003_28"))) {
//				String employeeCode = cells.get(0).getValue().getText();
//				employeeCodes.add(employeeCode);
//			}
			
		});
		if(!employeeCodes.isEmpty()) {
			List<String> employeeIds = this.employeeRepo.findByListEmployeeCode(companyId, employeeCodes)
					.stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());
			return employeeIds;
		}
		return new ArrayList<>();
	}
	
}
