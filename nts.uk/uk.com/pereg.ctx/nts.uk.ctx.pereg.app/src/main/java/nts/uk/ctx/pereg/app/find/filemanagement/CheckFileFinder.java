package nts.uk.ctx.pereg.app.find.filemanagement;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.infra.file.storage.StoredFileStreamService;
import nts.gul.excel.ExcelFileTypeException;
import nts.gul.excel.NtsExcelCell;
import nts.gul.excel.NtsExcelHeader;
import nts.gul.excel.NtsExcelImport;
import nts.gul.excel.NtsExcelReader;
import nts.gul.excel.NtsExcelRow;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.CodeName;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.GridEmpBody;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.GridEmpHead;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.GridEmployeeDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.GridEmployeeInfoDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefForLayoutDto;
import nts.uk.ctx.pereg.app.find.processor.GridPeregProcessor;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCtgByCompanyRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.setting.matrix.matrixdisplayset.MatrixDisplaySetting;
import nts.uk.ctx.pereg.dom.person.setting.matrix.matrixdisplayset.MatrixDisplaySettingRepo;
import nts.uk.ctx.pereg.dom.person.setting.matrix.personinfomatrixitem.PersonInfoMatrixData;
import nts.uk.ctx.pereg.dom.person.setting.matrix.personinfomatrixitem.PersonInfoMatrixItemRepo;
import nts.uk.ctx.sys.auth.dom.role.EmployeeReferenceRange;
import nts.uk.ctx.sys.auth.dom.role.Role;
import nts.uk.ctx.sys.auth.dom.role.RoleRepository;
import nts.uk.ctx.sys.auth.pub.employee.EmployeePublisher;
import nts.uk.ctx.sys.auth.pub.employee.NarrowEmpByReferenceRange;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.enumcommon.NotUseAtr;
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
	
	@Inject
	private GridPeregProcessor gridProcesor;
	
	@Inject 
	private PerInfoCtgByCompanyRepositoty ctgRepo;
	
	@Inject
	private MatrixDisplaySettingRepo matrixDisplayRepo;;

	@Inject
	private PersonInfoMatrixItemRepo matrixItemRepo;
	
	private static String code = "コード";
	
	public GridEmployeeDto processingFile(CheckFileParams params) {
		try {
			return processFile(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public GridEmployeeDto processFile(CheckFileParams params) throws Exception {
		try {
			String cid = AppContexts.user().companyId();
			String userId = AppContexts.user().userId();
			// read file import
			InputStream inputStream = this.fileStreamService.takeOutFromFileId(params.getFileId());
			// data file
			NtsExcelImport excelReader = NtsExcelReader.read(inputStream);
			// header
			List<NtsExcelHeader> header = excelReader.headers(); 
			List<NtsExcelRow> rows = excelReader.rows();
			List<String> fixedCol = fixedColums(cid, userId);
			
			// columns
			List<String> colums = this.getColumsChange(header);
			
			this.getEmployeeIds(rows);
			
//			GridEmployeeDto dto = this.getGridInfo(excelReader, colums); 
			GridEmployeeDto z  = this.getHeaderData(params.getCategoryId(), colums, colums);
			//remove những cột cố định
			colums.removeAll(fixedCol);
			
			//受入するファイルの列に、メイン画面の「個人情報一覧（A3_001）」に表示している可変列で更新可能な項目が１件でも存在するかチェックする
			//check xem các header của item trong file import có khớp với màn hình A 
			if(!colums.containsAll(params.getColumnChange())) {
				throw new Exception("Msg_723");
			}
			
			return null;
		} catch (ExcelFileTypeException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	// get ColumnsFixed
	private List<String> getColumsChange(List<NtsExcelHeader> header) throws Exception{
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
		//アルゴリズム「受入社員情報取得処理」を実行する
		rows.stream().forEach(c ->{
			List<NtsExcelCell> cells = c.cells();
			NtsExcelHeader header = cells.get(0).getHeader();
			String nameCol = header.getMain().getValue().toString();
			//Excelファイルから、「社員コード」列を取得する
			if(nameCol.equals(TextResource.localize("CPS003_28"))) {
				String employeeCode = cells.get(0).getValue().toString();
				employeeCodes.add(employeeCode);
			}
		});
		
		if(!employeeCodes.isEmpty()) {
			//「社員コード」列から１行ずつ取得して、ドメインモデル「社員データ管理情報」を取得する 
			employeeIds.addAll(this.employeeRepo.findByListEmployeeCode(companyId, employeeCodes)
					.stream().map(c -> c.getEmployeeId()).collect(Collectors.toList()));
		}
		
		if(!employeeIds.isEmpty()) {
			// 社員リストを参照範囲で絞り込む - RequestList539 (RequestList338)
			Optional<NarrowEmpByReferenceRange> narrow  = this.employeePub.findByEmpId(employeeIds, 8 );
			Optional<Role> optRole = roleRepo.findByRoleId(roleId);
			//受入する社員が存在する（ログイン者が操作できる社員として存在する）かチェックする
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

	
	private List<String> fixedColums(String cid, String userId){
		
		Optional<MatrixDisplaySetting> optData = matrixDisplayRepo.find(cid, userId);
		List<String> fixedColumn =  new ArrayList<>(); 
		fixedColumn.addAll(Arrays.asList(
				TextResource.localize("CPS003_28"),
				TextResource.localize("CPS003_29")));
		
		if(optData.isPresent()) {
			MatrixDisplaySetting settingMatrix = optData.get();
			if(settingMatrix.getDepartmentATR() == NotUseAtr.USE) {
				List<String> x = Arrays.asList(TextResource.localize("CPS003_30"), TextResource.localize("CPS003_30")+"("+code+")");
				fixedColumn.addAll(x);
			}
			
			if(settingMatrix.getWorkPlaceATR()== NotUseAtr.USE) {
				fixedColumn.addAll(Arrays.asList(TextResource.localize("CPS003_31"), TextResource.localize("CPS003_31")+"("+code+")"));
			}
			
			if(settingMatrix.getJobATR() == NotUseAtr.USE) {
				fixedColumn.addAll(Arrays.asList(TextResource.localize("CPS003_32"), TextResource.localize("CPS003_32")+"("+code+")"));
			}
			
			if(settingMatrix.getEmploymentATR() == NotUseAtr.USE) {
				fixedColumn.addAll(Arrays.asList(TextResource.localize("CPS003_33"), TextResource.localize("CPS003_33")+"("+code+")"));
			}
			
			if(settingMatrix.getClsATR()== NotUseAtr.USE) {
				fixedColumn.addAll(Arrays.asList(TextResource.localize("CPS003_34"), TextResource.localize("CPS003_34")+"("+code+")"));
			}
		}
		return fixedColumn;
	}
	
	/**
	 * 	起動時処理
	 * getGridLayout
	 * @return
	 */
	public GridEmployeeDto getHeaderData(String  categoryId, List<String> columItemChangeExel, List<String> columNotChangeExcel) {
		LoginUserContext loginUser = AppContexts.user();
		String cid = loginUser.companyId();
		String userId = loginUser.userId();
		String contractCd = loginUser.contractCode();
		String roleId = loginUser.roles().forPersonalInfo();
		List<GridEmpHead> headerReal = new ArrayList<>();

		
		Optional<PersonInfoCategory> ctgOptional =  this.ctgRepo.getDetailCategoryInfo(cid, categoryId, contractCd);
		
		List<String> columNotChange =  this.fixedColums(cid, userId);
		
		
		if(!ctgOptional.isPresent()) return null;
		
		// map PersonInfoItemDefinition → GridEmpHead
		 List<PerInfoItemDefForLayoutDto> i = this.gridProcesor.getPerItemDefForLayout(ctgOptional.get(), contractCd, roleId);
		 List<GridEmpHead> headers = i.stream()
				.map(m -> new GridEmpHead(m.getId(), m.getDispOrder(), m.getItemCode(), m.getItemParentCode(),
						m.getItemName(), m.getItemTypeState(), m.getIsRequired() == 1, m.getResourceId(),
						m.getLstChildItemDef().stream()
						.sorted(Comparator.comparing(PerInfoItemDefDto::getItemCode, Comparator.naturalOrder()))
								.map(c -> new GridEmpHead(c.getId(), m.getDispOrder(), c.getItemCode(),
										c.getItemParentCode(), c.getItemName(), c.getItemTypeState(),
										c.getIsRequired() == 1, c.getResourceId(), null))
								.collect(Collectors.toList())))
				.sorted(Comparator.comparing(GridEmpHead::getItemOrder, Comparator.naturalOrder()).thenComparing(GridEmpHead::getItemCode, Comparator.naturalOrder()))
				.collect(Collectors.toList());
		
		
		List<PersonInfoMatrixData> itemData = matrixItemRepo.findInfoData(ctgOptional.get().getPersonInfoCategoryId())
				.stream().filter(c -> c.isRegulationAtr() == true).collect(Collectors.toList());
		
		itemData.stream().forEach(c ->{
			headers.stream().forEach(item ->{
				if((c.getPerInfoItemDefID().equals(item.getItemId()))) {
					headerReal.add(item);
				}
			});
		});
		
		headerReal.stream().forEach(header ->{
			columItemChangeExel.forEach(itemName ->{
				
				
			});
			
		});
		
		
		
		
		
		
		
		
		return null;
	}
	
	
	private GridEmployeeDto getGridInfo(NtsExcelImport excelReader, List<GridEmpHead> headerReal, List<String> columFixed) {
		/* lien quan den bodyData*/
		List<GridEmployeeInfoDto> bodyDatas = new ArrayList<>();
		List<NtsExcelRow> rows = excelReader.rows();
		rows.stream().forEach( row ->{
			List<NtsExcelCell> cells = row.cells();		
			List<GridEmpBody> items = new ArrayList<>();
			GridEmployeeInfoDto dto = new GridEmployeeInfoDto();
			
			cells.stream().forEach( cell -> {
				// lấy dữ liệu của itemName
				headerReal.stream().forEach(item ->{
					if(item.getItemName().equals(cell.getHeader().getMain().getValue())){
						GridEmpBody empBody = new GridEmpBody();
						empBody.setValue(cell.getValue()==null? null: cell.getValue().toString());
						items.add(empBody);
					}
				});
				
				// lấy thông tin department
				
			});
			dto.setItems(items);
			bodyDatas.add(dto);
		});
		return new GridEmployeeDto("", null, headerReal, bodyDatas);
	}
	
	
}
