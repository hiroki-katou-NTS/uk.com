package nts.uk.ctx.pereg.app.find.filemanagement;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.infra.file.storage.StoredFileStreamService;
import nts.gul.excel.ExcelFileTypeException;
import nts.gul.excel.NtsExcelCell;
import nts.gul.excel.NtsExcelImport;
import nts.gul.excel.NtsExcelReader;
import nts.gul.excel.NtsExcelRow;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.CodeName;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.GridEmpBody;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.GridEmpHead;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.GridEmployeeDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.GridEmployeeInfoDto;
import nts.uk.ctx.sys.auth.dom.role.EmployeeReferenceRange;
import nts.uk.ctx.sys.auth.dom.role.Role;
import nts.uk.ctx.sys.auth.dom.role.RoleRepository;
import nts.uk.ctx.sys.auth.pub.employee.EmployeePublisher;
import nts.uk.ctx.sys.auth.pub.employee.NarrowEmpByReferenceRange;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class CheckFileFinder {
	@Inject
	private EmployeeDataMngInfoRepository employeeRepo;
	
	@Inject
	private EmployeePublisher  employeePub;
	
	@Inject
	private StoredFileStreamService fileStreamService;
	
	@Inject
	private RoleRepository roleRepo;
	
//	@Inject
//	private MatrixDisplaySettingRepo displaySettingRepo;
	
	private static String code = "コード";
	public GridEmployeeDto processingFile(CheckFileParams params) {
		try {
			return processFile(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public GridEmployeeDto processFile(CheckFileParams command) throws Exception {
		try {
			// read file import
			InputStream inputStream = this.fileStreamService.takeOutFromFileId(command.getFileId());
//			String cid = AppContexts.user().companyId();
//			String userId = AppContexts.user().userId();
//			Optional<MatrixDisplaySetting> displaySetting = this.displaySettingRepo.find(cid, userId);
			// data file
			NtsExcelImport excelReader = NtsExcelReader.read(inputStream);
			// header
			List<nts.gul.excel.ExcelHeader> header = excelReader.headers(); 
			List<NtsExcelRow> rows = excelReader.rows();
			List<String> fixedCol = fixedColums();
			
			// columns
			List<String> colums = this.getColumsChange(header);
			
			this.getEmployeeIds(rows);
			
			GridEmployeeDto dto = this.getGridInfo(excelReader, colums);
			
			//remove những cột cố định
			colums.removeAll(fixedCol);
			
			//check xem các header của item trong file import có khớp với màn hình A 
			if(!colums.containsAll(command.getColumnChange())) {
				throw new Exception("Msg_723");
			}
			
			return dto;
		} catch (ExcelFileTypeException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	// get ColumnsFixed
	private List<String> getColumsChange(List<nts.gul.excel.ExcelHeader> header) throws Exception{
		List<String> colChange = new ArrayList<>();
		header.stream().forEach(c ->{
			NtsExcelCell mainCells = c.getMain();
			colChange.add( mainCells.getValue().toString());
		});
		return colChange;
	}
	
	//get EmployeeIds
	private List<String> getEmployeeIds(List<NtsExcelRow> rows){
		List<String> employeeIds = new ArrayList<>();
		List<String> employeeCodes = new ArrayList<>();
		String companyId = AppContexts.user().companyId();
		String roleId = AppContexts.user().roles().forPersonalInfo();
		
		rows.stream().forEach(c ->{
			List<NtsExcelCell> cells = c.cells();
			nts.gul.excel.ExcelHeader header = cells.get(0).getHeader();
			String nameCol = header.getMain().getValue().toString();
			if(nameCol.equals(TextResource.localize("CPS003_28"))) {
				String employeeCode = cells.get(0).getValue().toString();
				employeeCodes.add(employeeCode);
			}
			
		});
		
		if(!employeeCodes.isEmpty()) {
			employeeIds.addAll(this.employeeRepo.findByListEmployeeCode(companyId, employeeCodes)
					.stream().map(c -> c.getEmployeeId()).collect(Collectors.toList()));
		}
		
		if(!employeeIds.isEmpty()) {
			// RequestList539 (RequestList338)
			Optional<NarrowEmpByReferenceRange> narrow  = this.employeePub.findByEmpId(employeeIds, 8 );
			Optional<Role> optRole = roleRepo.findByRoleId(roleId);
			
			if (narrow.isPresent()) {
				if (optRole.get().getEmployeeReferenceRange() != EmployeeReferenceRange.ALL_EMPLOYEE) {
					throw new BusinessException("Msg_724");
				} else {
					if (narrow.isPresent()) {
						return narrow.get().getEmployeeID();
					}
				}
			}
		}
		 return new ArrayList<>();
	}
	
	private GridEmployeeDto getGridInfo(NtsExcelImport excelReader, List<String> headerDatas) {
		// get setting display 固定列
		List<GridEmpHead> headDatas = new ArrayList<>();
		headerDatas.stream().forEach(c ->{
			GridEmpHead gridEmpHead = new GridEmpHead();
			gridEmpHead.setItemName(c);
			headDatas.add(gridEmpHead);
		});
		/* lien quan den bodyData*/
		List<GridEmployeeInfoDto> bodyDatas = new ArrayList<>();
		List<NtsExcelRow> rows = excelReader.rows();
		rows.stream().forEach( row ->{
			CodeName codeName = new CodeName();
			List<NtsExcelCell> cells = row.cells();		
			
			List<GridEmpBody> items = new ArrayList<>();
			GridEmployeeInfoDto dto = new GridEmployeeInfoDto();
			
			cells.stream().forEach( cell -> {
					GridEmpBody empBody = new GridEmpBody();
					empBody.setValue(cell.getValue()==null? null: cell.getValue().toString());
					items.add(empBody);
			});
			dto.setItems(items);
			dto.setEmployee(codeName);
			bodyDatas.add(dto);
		});
		return new GridEmployeeDto("", null, headDatas, bodyDatas);
	}
	
	private static List<String> fixedColums(){
		List<String> fixedColumn = Arrays.asList(
				TextResource.localize("CPS003_28"),
				TextResource.localize("CPS003_29"),
				TextResource.localize("CPS003_30"),
				TextResource.localize("CPS003_30")+"("+code+")",
				TextResource.localize("CPS003_31"),
				TextResource.localize("CPS003_31")+"("+code+")",
				TextResource.localize("CPS003_32"),
				TextResource.localize("CPS003_32")+"("+code+")",
				TextResource.localize("CPS003_33"),
				TextResource.localize("CPS003_33")+"("+code+")",
				TextResource.localize("CPS003_34"),
				TextResource.localize("CPS003_34")+"("+code+")");
		return fixedColumn;
	}
}
