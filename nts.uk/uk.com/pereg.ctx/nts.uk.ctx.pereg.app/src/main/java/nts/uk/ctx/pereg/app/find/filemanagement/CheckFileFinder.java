package nts.uk.ctx.pereg.app.find.filemanagement;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.infra.file.storage.StoredFileStreamService;
import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.arc.time.GeneralDate;
import nts.gul.excel.ExcelFileTypeException;
import nts.gul.excel.NtsExcelCell;
import nts.gul.excel.NtsExcelHeader;
import nts.gul.excel.NtsExcelImport;
import nts.gul.excel.NtsExcelReader;
import nts.gul.excel.NtsExcelRow;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.pereg.app.find.common.ComboBoxRetrieveFactory;
import nts.uk.ctx.pereg.app.find.layout.dto.EmpMaintLayoutDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.GridEmpHead;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoValueDto;
import nts.uk.ctx.pereg.app.find.person.info.item.DateItemDto;
import nts.uk.ctx.pereg.app.find.person.info.item.ItemTypeStateDto;
import nts.uk.ctx.pereg.app.find.person.info.item.MasterRefConditionDto;
import nts.uk.ctx.pereg.app.find.person.info.item.NumericButtonDto;
import nts.uk.ctx.pereg.app.find.person.info.item.NumericItemDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefForLayoutDto;
import nts.uk.ctx.pereg.app.find.person.info.item.SelectionItemDto;
import nts.uk.ctx.pereg.app.find.person.info.item.SingleItemDto;
import nts.uk.ctx.pereg.app.find.person.info.item.StringItemDto;
import nts.uk.ctx.pereg.app.find.person.info.item.TimeItemDto;
import nts.uk.ctx.pereg.app.find.person.info.item.TimePointItemDto;
import nts.uk.ctx.pereg.app.find.processor.GridPeregProcessor;
import nts.uk.ctx.pereg.app.find.processor.PeregProcessor;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCtgByCompanyRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.ItemType;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
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
import nts.uk.shr.com.validate.constraint.implement.DateConstraint;
import nts.uk.shr.com.validate.constraint.implement.DateType;
import nts.uk.shr.com.validate.constraint.implement.NumericConstraint;
import nts.uk.shr.com.validate.constraint.implement.StringCharType;
import nts.uk.shr.com.validate.constraint.implement.StringConstraint;
import nts.uk.shr.com.validate.constraint.implement.TimeConstraint;
import nts.uk.shr.com.validate.constraint.implement.TimePointConstraint;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregQuery;

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
	
	@Inject
	private ComboBoxRetrieveFactory comboBoxRetrieveFactory;
	
	@Inject
	private ManagedParallelWithContext parallel;
	
	@Inject
	private PeregProcessor layoutProcessor;
	
	private static String code = "コード";
	
	private static String header;
	
	public GridDto processingFile(CheckFileParams params) {
		try {
			return processFile(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public GridDto processFile(CheckFileParams params) throws Exception {
		try {
			String cid = AppContexts.user().companyId();
			String userId = AppContexts.user().userId();
			String contractCd = AppContexts.user().contractCode();
			// read file import
			InputStream inputStream = this.fileStreamService.takeOutFromFileId(params.getFileId());
			Optional<PersonInfoCategory> ctgOptional =  this.ctgRepo.getDetailCategoryInfo(cid, params.getCategoryId(), contractCd);
			if(!ctgOptional.isPresent()) throw new BusinessException("category invalid");
			// data file
			NtsExcelImport excelReader = NtsExcelReader.read(inputStream);
			// header
			List<NtsExcelHeader> header = excelReader.headers(); 
			List<NtsExcelRow> rows = excelReader.rows();
			List<String> fixedCol = fixedColums(cid, userId);
			
			// columns
			List<String> colums = this.getColumsChange(header);
			
			List<EmployeeDataMngInfo> employees = this.getEmployeeIds(rows);
			
			List<GridEmpHead> headerFinal  = this.getHeaderData(ctgOptional.get(), colums, colums);
			GridDto dto = this.getGridInfo(excelReader, headerFinal, colums, ctgOptional.get(), employees); 
			//remove những cột cố định
			colums.removeAll(fixedCol);
			
			//受入するファイルの列に、メイン画面の「個人情報一覧（A3_001）」に表示している可変列で更新可能な項目が１件でも存在するかチェックする
			//check xem các header của item trong file import có khớp với màn hình A 
			//if(!colums.containsAll(params.getColumnChange())) {
			//throw new Exception("Msg_723");
			//			}
			
			return dto;
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
	private List<EmployeeDataMngInfo> getEmployeeIds(List<NtsExcelRow> rows){
		List<String> employeeIds = new ArrayList<>();
		List<String> employeeCodes = new ArrayList<>();
		String companyId = AppContexts.user().companyId();
		String roleId = AppContexts.user().roles().forPersonalInfo();
		List<EmployeeDataMngInfo> employeeMange = new ArrayList<>();
		List<EmployeeDataMngInfo> result = new ArrayList<>();
		//アルゴリズム「受入社員情報取得処理」を実行する
		rows.stream().forEach(c ->{
			List<NtsExcelCell> cells = c.cells();
			NtsExcelHeader header = cells.get(0).getHeader();
			String nameCol = header.getMain().getValue().getText();
			//Excelファイルから、「社員コード」列を取得する
			if(nameCol.equals(TextResource.localize("CPS003_28"))) {
				String employeeCode = cells.get(0).getValue().getText();
				employeeCodes.add(employeeCode);
			}
		});
		
		if(!employeeCodes.isEmpty()) {
			//「社員コード」列から１行ずつ取得して、ドメインモデル「社員データ管理情報」を取得する 
			employeeMange.addAll(this.employeeRepo.findByListEmployeeCode(companyId, employeeCodes));
			employeeIds.addAll(employeeMange.stream().map(c -> c.getEmployeeId()).collect(Collectors.toList()));
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
						narrow.get().getEmployeeID().stream().forEach(c ->{
							Optional<EmployeeDataMngInfo> employeeOpt = employeeMange.stream().filter(emp -> emp.getEmployeeId().equals(c.toString())).findFirst();
							if(employeeOpt.isPresent()) {
								result.add(employeeOpt.get());
							}
						});
					}
				}
			}
			
		}
		 return result;
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
	public List<GridEmpHead> getHeaderData(PersonInfoCategory category, List<String> columItemChangeExel, List<String> columNotChangeExcel) {
		LoginUserContext loginUser = AppContexts.user();
		String contractCd = loginUser.contractCode();
		String roleId = loginUser.roles().forPersonalInfo();
		List<GridEmpHead> headerReal = new ArrayList<>();
		//List<String> columNotChange =  this.fixedColums(cid, userId);
		// map PersonInfoItemDefinition → GridEmpHead
		 List<PerInfoItemDefForLayoutDto> i = this.gridProcesor.getPerItemDefForLayout(category, contractCd, roleId);
		 List<GridEmpHead> headers = i.stream()
				.map(m -> new GridEmpHead(m.getId(), m.getDispOrder(), m.getItemCode(), m.getItemParentCode(),
						m.getItemName(), m.getItemTypeState(), m.getIsRequired() == 1, m.getResourceId()))
				.sorted(Comparator.comparing(GridEmpHead::getItemOrder, Comparator.naturalOrder()).thenComparing(GridEmpHead::getItemCode, Comparator.naturalOrder()))
				.collect(Collectors.toList());
		
		
		List<PersonInfoMatrixData> itemData = matrixItemRepo.findInfoData(category.getPersonInfoCategoryId())
				.stream().filter(c -> c.isRegulationAtr() == true).collect(Collectors.toList());
		
		itemData.stream().forEach(c ->{
			headers.stream().forEach(item ->{
				if((c.getPerInfoItemDefID().equals(item.getItemId()))) {
					headerReal.add(item);
				}
			});
		});
		return headerReal;
	}
	
	private HashMap<String, Object> generateContraint(List<GridEmpHead> headerReal){
		HashMap<String, Object> contraintList = new HashMap<>();
		
		headerReal.stream().forEach(c ->{
			ItemTypeStateDto itemTypeStateDto = c.getItemTypeState();
			Object obj;
			if(itemTypeStateDto.getItemType() == 1) {
				SingleItemDto singleDto = (SingleItemDto) itemTypeStateDto;
				switch(singleDto.getDataTypeState().getDataTypeValue()) {
				case 1:
					StringItemDto stringDto = (StringItemDto) singleDto.getDataTypeState();
					StringCharType type;
					switch (stringDto.getStringItemType()) {
					case 1:
						type = StringCharType.ANY;
						break;
					case 2:
						type = StringCharType.ANY_HALF_WIDTH;
						break;
					case 3:
						type = StringCharType.ALPHA_NUMERIC;
						break;
					case 4:
						type = StringCharType.NUMERIC;
						break;
					case 5:
						type = StringCharType.KATAKANA;
						break;
					case 6:
						
					case 7:
					default: 
						type = StringCharType.ANY;
						break;
					}

					obj = new StringConstraint( 0, type, stringDto.getStringItemLength());
					contraintList.put(c.getItemCode(), obj);
					break;
				case 2:
					NumericItemDto numericItemDto = (NumericItemDto) singleDto.getDataTypeState();
					
					obj = new NumericConstraint(0, numericItemDto.getNumericItemMinus() == 0 ? true : false,
							numericItemDto.getNumericItemMin(), numericItemDto.getNumericItemMax(),
							numericItemDto.getIntegerPart(),
							numericItemDto.getDecimalPart() == null ? 0 : numericItemDto.getDecimalPart().intValue());
					contraintList.put(c.getItemCode(), obj);
					break;
					
				case 3:
					DateItemDto dateDto = (DateItemDto) singleDto.getDataTypeState();
					DateType dateType = DateType.DATE;
					switch (dateDto.getDateItemType()) {
					case 1:
						dateType = DateType.DATE;
						break;
					case 2:
						dateType = DateType.YEARMONTH;
						break;
					case 3:
						dateType = DateType.YEAR;
						break;
					default: 
						dateType = DateType.DATE;
						break;
					}
					obj = new DateConstraint(0, dateType);
					contraintList.put(c.getItemCode(), obj);
					break;
				case 4:
					TimeItemDto timeItemDto = (TimeItemDto) singleDto.getDataTypeState();
					obj = new TimeConstraint(0, (int) timeItemDto.getMin(), (int) timeItemDto.getMax());
					contraintList.put(c.getItemCode(), obj);
					break;
					
				case 5:
					TimePointItemDto timePointItemDto = (TimePointItemDto) singleDto.getDataTypeState();
					obj = new TimePointConstraint(0, (int) timePointItemDto.getTimePointItemMin(), (int) timePointItemDto.getTimePointItemMax());
					contraintList.put(c.getItemCode(), obj);
					break;
				case 11:
					NumericButtonDto numbericButtonDto = (NumericButtonDto) singleDto.getDataTypeState();
					obj = new NumericConstraint(0, numbericButtonDto.getNumericItemMinus() == 0 ? true : false,
							numbericButtonDto.getNumericItemMin(), numbericButtonDto.getNumericItemMax(),
							numbericButtonDto.getIntegerPart(),
							numbericButtonDto.getDecimalPart());
					contraintList.put(c.getItemCode(), obj);
					break;
//				case 6:
//				case 7:
//				case 8:
//					SelectionItemDto selectionItemDto = (SelectionItemDto)singleDto.getDataTypeState();
//					switch (selectionItemDto.getReferenceType().value) {
//					case 1:
//						MasterRefConditionDto master = (MasterRefConditionDto) selectionItemDto;
//						if (master.getMasterType().equals("M00006")) {
//							obj = new NumericConstraint(0, false, new BigDecimal(0), new BigDecimal(10), 2, 0);
//							contraintList.put(c.getItemCode(), obj);
//							break;
//						}else {
//							obj = new StringConstraint( 0, StringCharType.ANY, 300);
//							contraintList.put(c.getItemCode(), obj);
//						}
//						break;
//					//2:コード名称(CodeName)
//					case 2: break;
//					// 3:列挙型(Enum)
//					case 3: break;
//						
//					}
//					break;
				default: break;
				}
				
			}
		});
		return contraintList;
	}
	
	
	private GridDto getGridInfo(NtsExcelImport excelReader, List<GridEmpHead> headerReal, List<String> columFixed, PersonInfoCategory category, List<EmployeeDataMngInfo> employees) {
		/* lien quan den bodyData*/
		List<GridEmpHead> headerRemain = new ArrayList<>();
		List<GridEmpHead> x =  new ArrayList<GridEmpHead>();
		HashMap<String, Object> contraintList = new HashMap<>();
		x.addAll(headerReal);

		
		List<EmployeeRowDto> employeeDtos = new ArrayList<>();
		List<NtsExcelRow> rows = excelReader.rows();
		rows.stream().forEach( row ->{
			List<NtsExcelCell> cells = row.cells();		
			EmployeeRowDto employeeDto = new EmployeeRowDto();
			List<ItemRowDto> items = new ArrayList<>();
			
			cells.stream().forEach( cell -> {
				String header = cell.getHeader().getMain().getValue().getText();
				
				// lấy emloyeeCode, employeeName
				if (header.equals(TextResource.localize("CPS003_28"))) {
					
					//employeeId
					Optional<EmployeeDataMngInfo> emp = employees.stream().filter(c -> c.getEmployeeCode().toString().equals(cell.getValue().getText())).findFirst();
					if(!emp.isPresent()) return;
					employeeDto.setEmployeeCode(cell.getValue() == null ? "" : cell.getValue().getText());
					employeeDto.setEmployeeId(emp.get().getEmployeeId());
					employeeDto.setPersonId(emp.get().getPersonId());
					
				} else if (header.equals(TextResource.localize("CPS003_29"))) {
					employeeDto.setEmployeeName(cell.getValue() == null ? "" : cell.getValue().getText());
				} else {
					
					// lấy dữ liệu của itemName
					boolean isSelectionCode = header.contains("（コード）");
					if(isSelectionCode) {
						CheckFileFinder.header = header.split("（")[0];
					}
					Optional<GridEmpHead> headerGridOpt = headerReal.stream().filter(c -> {
						if(isSelectionCode) {
							return c.getItemName().equals(CheckFileFinder.header);
						}else{
							return c.getItemName().equals(header);
						}
					}).findFirst();
					
					
					if(headerGridOpt.isPresent()) {
						GridEmpHead headerGrid = headerGridOpt.get();
						ItemRowDto empBody = new ItemRowDto();
						if(isSelectionCode) {
							String selectionCode = cell.getValue() == null ? "" : cell.getValue().getText();
							empBody.setItemCode(headerGrid.getItemCode());
							empBody.setItemName(headerGrid.getItemName());
							empBody.setItemOrder(headerGrid.getItemOrder());
							empBody.setValue(cell.getValue() == null ? "" : cell.getValue().getText());
							List<ComboBoxObject> comboxLst = this.getComboBox(headerGrid, category, employeeDto.getEmployeeId(), empBody, items);
							Optional<ComboBoxObject> combo = comboxLst.stream().filter(c -> c.getOptionValue().equals(selectionCode)).findFirst();
							empBody.setValue(combo.isPresent() == true? combo.get().getOptionValue(): "");
							empBody.setLstComboBoxValue(comboxLst);
							items.add(empBody);
						}else {
							if (!headerGrid.getItemName().equals(CheckFileFinder.header)) {
								empBody.setItemCode(headerGrid.getItemCode());
								empBody.setItemName(headerGrid.getItemName());
								empBody.setItemOrder(headerGrid.getItemOrder());
								empBody.setValue(cell.getValue() == null ? "" : cell.getValue().getText());
								empBody.setTextValue(cell.getValue() == null ? "" : cell.getValue().getText());
								items.add(empBody);
							}
						}

						
						
						headerRemain.add(headerGrid);
					}
				}
			});
			employeeDto.setItems(items);
			if(employeeDto.getEmployeeCode()!= null) {
				employeeDtos.add(employeeDto);
			}
			
		});
		
		List<EmployeeRowDto> result = Collections.synchronizedList(new ArrayList<>());
		// lấy ra những item bị thiếu không file import ko có trong setting ở màn hình A, set RecordId
		headerReal.removeAll(headerRemain);
		if(headerReal.size() > 0) {
			this.parallel.forEach(employeeDtos, pdt -> {

				// Miss infoId (get infoId by baseDate)
				PeregQuery subq = PeregQuery.createQueryCategory(null, category.getCategoryCode().v(),
						pdt.getEmployeeId(), pdt.getPersonId());

				subq.setCategoryId(category.getPersonInfoCategoryId());
				subq.setStandardDate(GeneralDate.today());

				EmpMaintLayoutDto empDto = layoutProcessor.getCategoryDetail(subq);

				List<LayoutPersonInfoValueDto> items = empDto.getClassificationItems().stream()
						.flatMap(f -> f.getItems().stream()).collect(Collectors.toList());
				List<ItemRowDto> itemDtos = new ArrayList<>();
				headerReal.stream().forEach(h ->{
					items.stream().forEach(item ->{
						if(h.getItemId().equals(item.getItemDefId())) {
							ItemRowDto dto = new ItemRowDto(h.getItemCode(), h.getItemName(), item.getValue(),
									item.getTextValue(), item.getRecordId(),h.getItemOrder(), item.getLstComboBoxValue());
							itemDtos.add(dto);
						}
					});
				});
				
				if(itemDtos.size() > 0) {
					pdt.getItems().addAll(itemDtos);
						items.stream().forEach(item ->{
							pdt.getItems().stream().forEach(itemDto ->{
								if(itemDto.getItemCode().equals(item.getItemCode())) {
									itemDto.setRecordId(item.getRecordId());
								}
							});

						});
						
						// sắp xếp lại vị trí item
						pdt.getItems().sort(Comparator.comparing(ItemRowDto::getItemOrder, Comparator.naturalOrder()));
						
					result.add(pdt);
				}
				

			});
		}
		return new GridDto(x, result, new ArrayList<>());
	}
	
	public List<ComboBoxObject> getComboBox(GridEmpHead headerGrid, PersonInfoCategory category, String sid, ItemRowDto empBody, List<ItemRowDto> items) {
		ItemTypeStateDto dto = headerGrid.getItemTypeState();
		SingleItemDto singleDto = (SingleItemDto) dto;
		
		if (singleDto.getItemType() == ItemType.SINGLE_ITEM.value) {
			if (singleDto.getDataTypeState().getDataTypeValue() == DataTypeValue.SELECTION.value
					|| singleDto.getDataTypeState().getDataTypeValue() == DataTypeValue.SELECTION_BUTTON.value
					|| singleDto.getDataTypeState().getDataTypeValue() == DataTypeValue.SELECTION_RADIO.value) {
				boolean isDataType6 = singleDto.getDataTypeState().getDataTypeValue() == DataTypeValue.SELECTION.value?  true: false;
				SelectionItemDto selectionItemDto = (SelectionItemDto)singleDto.getDataTypeState();
				List<ComboBoxObject> combox = this.comboBoxRetrieveFactory.getComboBox(selectionItemDto, sid,
						GeneralDate.today(), headerGrid.isRequired(), category.getPersonEmployeeType(), isDataType6,
						category.getCategoryCode().v(), null, false);
				return combox;
			}
		}
		return new ArrayList<>();
	}
	
	
}
