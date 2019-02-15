package nts.uk.ctx.pereg.app.find.filemanagement;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;
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
import nts.gul.time.minutesbased.MinutesBasedTimeParser;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHist;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistByEmployee;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistItem;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.pereg.app.find.common.ComboBoxRetrieveFactory;
import nts.uk.ctx.pereg.app.find.layout.dto.EmpMaintLayoutDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.ActionRole;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.GridEmpHead;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoValueDto;
import nts.uk.ctx.pereg.app.find.person.info.item.DataTypeStateDto;
import nts.uk.ctx.pereg.app.find.person.info.item.DateItemDto;
import nts.uk.ctx.pereg.app.find.person.info.item.ItemTypeStateDto;
import nts.uk.ctx.pereg.app.find.person.info.item.NumericButtonDto;
import nts.uk.ctx.pereg.app.find.person.info.item.NumericItemDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefDto;
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
import nts.uk.ctx.sys.auth.dom.role.EmployeeReferenceRange;
import nts.uk.ctx.sys.auth.dom.role.Role;
import nts.uk.ctx.sys.auth.dom.role.RoleRepository;
import nts.uk.ctx.sys.auth.pub.employee.EmployeePublisher;
import nts.uk.ctx.sys.auth.pub.employee.NarrowEmpByReferenceRange;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
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
	private ComboBoxRetrieveFactory comboBoxRetrieveFactory;
	
	@Inject
	private ManagedParallelWithContext parallel;
	
	@Inject
	private PeregProcessor layoutProcessor;
	
	@Inject
	private AffCompanyHistRepository companyHistRepo;
	
	private static String header;
	
	private final static List<String> itemSpecialLst = Arrays.asList("IS00003","IS00004");
	
	private static final Map<String, String> startDateItemCodes;
	static {
		Map<String, String> aMap = new HashMap<>();
		
		// 所属会社履歴
		aMap.put("CS00003", "IS00020");
		// 分類１
		aMap.put("CS00004", "IS00026");
		// 雇用
		aMap.put("CS00014", "IS00066");
		// 職位本務
		aMap.put("CS00016", "IS00077");
		// 職場
		aMap.put("CS00017", "IS00082");
		// 休職休業
		aMap.put("CS00018", "IS00087");
		// 短時間勤務
		aMap.put("CS00019", "IS00102");
		// 労働条件
		aMap.put("CS00020", "IS00119");
		// 勤務種別
		aMap.put("CS00021", "IS00255");
		// 労働条件２
		aMap.put("CS00070", "IS00781");

		startDateItemCodes = Collections.unmodifiableMap(aMap);
	} 
	
	@SuppressWarnings("unused")
	private static GeneralDate valueStartCode;
	
	private static final String JP_SPACE = "　";
	
	public GridDto processingFile(CheckFileParams params) throws Exception {
		try {
			return processFile(params);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public GridDto processFile(CheckFileParams params) throws Exception {
		try {
			String cid = AppContexts.user().companyId();
			String contractCd = AppContexts.user().contractCode();
			UpdateMode updateMode = EnumAdaptor.valueOf(params.getModeUpdate(), UpdateMode.class);
			// read file import
			InputStream inputStream = this.fileStreamService.takeOutFromFileId(params.getFileId());
			Optional<PersonInfoCategory> ctgOptional =  this.ctgRepo.getDetailCategoryInfo(cid, params.getCategoryId(), contractCd);
			if(!ctgOptional.isPresent()) throw new BusinessException("category invalid");
			// data file
			NtsExcelImport excelReader = NtsExcelReader.read(inputStream);
			// header
			List<NtsExcelHeader> header = excelReader.headers(); 
			List<NtsExcelRow> rows = excelReader.rows();
			List<GridEmpHead> headerDb = this.getHeaderData(ctgOptional.get(), params.getColumnChange());
			
			// columns
			List<String> colums = this.getColumsChange(header, headerDb);
			List<EmployeeDataMngInfo> employees = this.getEmployeeIds(rows, params.getSids());
			String startCode = startDateItemCodes.get(ctgOptional.get().getCategoryCode().toString());
			GridDto dto = this.getGridInfo(excelReader, headerDb, ctgOptional.get(), employees, startCode, updateMode); 
			//受入するファイルの列に、メイン画面の「個人情報一覧（A3_001）」に表示している可変列で更新可能な項目が１件でも存在するかチェックする
			//check xem các header của item trong file import có khớp với màn hình A 
			if (colums.size() == 0) {
				throw new BusinessException("Msg_723");
			}
			return dto;
		} catch (ExcelFileTypeException e1) {
			throw e1;
		}
	}
	
	// get ColumnsFixed
	private List<String> getColumsChange(List<NtsExcelHeader> header, List<GridEmpHead> headerReal) throws Exception{
		List<String> colChange = new ArrayList<>();
		header.stream().forEach(c ->{
			NtsExcelCell mainCells = c.getMain();
			if(!mainCells.getValue().getText().equals(TextResource.localize("CPS003_28"))  && !mainCells.getValue().getText().equals(TextResource.localize("CPS003_29") ) && !mainCells.getValue().getText().contains("（コード）")) {
				Optional<GridEmpHead> gridHead = headerReal.stream().filter(head -> head.getItemName().equals( mainCells.getValue().getText())).findFirst();
				if(gridHead.isPresent()) {
					colChange.add( mainCells.getValue().getText());
				}
			}
		});
		return colChange;
	}
	
	//get EmployeeIds
	private List<EmployeeDataMngInfo> getEmployeeIds(List<NtsExcelRow> rows, List<String> sids){
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
	
	/**
	 * 	起動時処理
	 * getGridLayout
	 * @return
	 */
	public List<GridEmpHead> getHeaderData(PersonInfoCategory category, List<GridEmpHead> columItemChangeExel) {
		LoginUserContext loginUser = AppContexts.user();
		String contractCd = loginUser.contractCode();
		String roleId = loginUser.roles().forPersonalInfo();
		List<GridEmpHead> headerReal = new ArrayList<>();
		 List<PerInfoItemDefForLayoutDto> i = this.gridProcesor.getPerItemDefForLayout(category, contractCd, roleId);
		 List<GridEmpHead> headers = i.stream().map(m -> new GridEmpHead(m.getId(), m.getDispOrder(), m.getItemCode(), m.getItemParentCode(),
					m.getItemName(), m.getItemTypeState(), m.getIsRequired() == 1, m.getResourceId(),
					m.getLstChildItemDef().stream()
					.sorted(Comparator.comparing(PerInfoItemDefDto::getItemCode, Comparator.naturalOrder()))
							.map(c -> new GridEmpHead(c.getId(), m.getDispOrder(), c.getItemCode(),
									c.getItemParentCode(), c.getItemName(), c.getItemTypeState(),
									c.getIsRequired() == 1, c.getResourceId(), null))
							.collect(Collectors.toList())))
			.sorted(Comparator.comparing(GridEmpHead::getItemOrder, Comparator.naturalOrder()).thenComparing(GridEmpHead::getItemCode, Comparator.naturalOrder()))
			.collect(Collectors.toList());
		 headers.addAll(headers.stream().flatMap(m -> m.getChilds().stream()).collect(Collectors.toList()));
		 columItemChangeExel.stream().forEach(c ->{
			headers.stream().forEach(item ->{
				if((c.getItemId().equals(item.getItemId()))) {
					headerReal.add(item);
					
				}
			});
		});
		return headerReal;
	}
	
	/**
	 * đoc file và lấy ra những item bị ẩn
	 * @param excelReader
	 * @param headerReal
	 * @param category
	 * @param employees
	 * @return
	 */
	private GridDto getGridInfo(NtsExcelImport excelReader, List<GridEmpHead> headerReal, PersonInfoCategory category, List<EmployeeDataMngInfo> employees, String startCode, UpdateMode updateMode) {
		/* lien quan den bodyData*/
		List<GridEmpHead> headerRemain = new ArrayList<>();
		List<GridEmpHead> header =  new ArrayList<GridEmpHead>();
		header.addAll(headerReal);
		HashMap<String, Object> contraintList = generateContraint(header);
		List<EmployeeRowDto> employeeDtos = new ArrayList<>();
		List<ItemRowDto> itemErrors = new ArrayList<>();
		List<NtsExcelRow> rows = excelReader.rows();
		// đọc dữ liệu từ file import
		readEmployeeFromFile(category,rows, employees, headerReal, contraintList, headerRemain,  employeeDtos, startCode);
		
		List<EmployeeRowDto> result = Collections.synchronizedList(new ArrayList<>());
		// lấy ra những item bị thiếu không file import ko có trong setting ở màn hình A, set RecordId, set actionRole
		headerReal.removeAll(headerRemain);
		this.parallel.forEach(employeeDtos, pdt -> {

			// Miss infoId (get infoId by baseDate)
			PeregQuery subq = PeregQuery.createQueryCategory(null, category.getCategoryCode().v(),
					pdt.getEmployeeId(), pdt.getPersonId());
			List<ItemRowDto> itemDtos = new ArrayList<>();
			subq.setCategoryId(category.getPersonInfoCategoryId());
			subq.setStandardDate(GeneralDate.today());
			// lấy full value của các item
			EmpMaintLayoutDto empDto = layoutProcessor.getCategoryDetail(subq);
			List<LayoutPersonInfoValueDto> items = empDto.getClassificationItems().stream()
					.flatMap(f -> f.getItems().stream()).collect(Collectors.toList());
			// thực hiện lấy những item được hiển thị trên màn hình A mà  ko có trong import 
			//・上書き保存モードの場合、・t/h mode overwrite_Save、				
			//受入した項目のみ更新して、受入してない項目は変更しない update chỉ cac item đa import, cac item chưa import thi ko thay đổi.
			//・新規履歴追加モードの場合、・t/h mode  them lịch sử mới、
			//基準日時点の履歴の情報をベースにして、受入した項目のみ変更、lấy thong tin lịch sử tại thời điểm baseDate lam chuẩn, chỉ thay đổi item đa import、
			//受入していない項目は基準日時点の情報で追加する cac item chưa import thi them thong tin tại thời điểm baseDate
			//※更新権限がない項目は受入（値の変更）をしない。※item ko co quyền update thi ko import（thay đổi value）
			headerReal.stream().forEach(h ->{
				items.stream().forEach(item ->{
					if(h.getItemId().equals(item.getItemDefId())) {
						// trường hợp ghi đè, item ko có trong file import thì sẽ chỉ được view thôi, ko được update giá trị
						ItemRowDto dto = new ItemRowDto(h.getItemCode(), h.getItemName(), item.getType(),item.getValue(),
								item.getTextValue(), item.getRecordId(),h.getItemOrder(), updateMode == UpdateMode.OVERIDED? ActionRole.VIEW_ONLY: item.getActionRole(),item.getLstComboBoxValue(), false);
						itemDtos.add(dto);
					}
				});
			});
			
			if(itemDtos.size() > 0) {
				pdt.getItems().addAll(itemDtos);
			}
			//lấy thông tin recordId và actionRole
			items.stream().forEach(item -> {
				pdt.getItems().stream().forEach(itemDto -> {
					if (itemDto.getItemCode().equals(item.getItemCode())) {
						itemDto.setRecordId(item.getRecordId());
						// trường hợp ghi đè, item ko có trong file import thì sẽ chỉ được view thôi, ko được update giá trị
						if(itemDto.getActionRole() == null) {
							itemDto.setActionRole(item.getActionRole());
						}
						// trường hợp tạo mới lịch sử, item có trong file import nhưng không có quyền update thì sẽ lấy giá trị từ database
						if(item.getActionRole() == ActionRole.VIEW_ONLY || item.getActionRole() == ActionRole.HIDDEN) {
							itemDto.setValue(item.getValue());
						}
						if(itemDto.isError()) {
							itemErrors.add(itemDto);
						}
						
					}
				});
			});
			// đếm số item bị lỗi của một employee
			pdt.setNumberOfError(itemErrors.size());
			pdt.getItems().sort(Comparator.comparing(ItemRowDto::getItemOrder, Comparator.naturalOrder()));
			result.add(pdt);
		});
		
		// sắp xếp lại vị trí item theo số tự lỗi - エクセル受入データを並び替える - エラーの件数　DESC、社員コード　ASC
		result.sort(Comparator.comparing(EmployeeRowDto::getEmployeeCode)
				.thenComparing(EmployeeRowDto::getNumberOfError, Comparator.naturalOrder()).reversed());
		return new GridDto(header, result, itemErrors);
	}
	
	/**
	 * đọc dữ liệu từ file import
	 * @param category
	 * @param rows
	 * @param employees
	 * @param headerReal
	 * @param contraintList
	 * @param headerRemain
	 * @param employeeDtos
	 */
	private void readEmployeeFromFile(PersonInfoCategory category, List<NtsExcelRow> rows,
			List<EmployeeDataMngInfo> employees, List<GridEmpHead> headerReal, HashMap<String, Object> contraintList,
			List<GridEmpHead> headerRemain, List<EmployeeRowDto> employeeDtos, String startCode) {

		rows.stream().forEach( row ->{
			List<NtsExcelCell> cells = row.cells();		
			EmployeeRowDto employeeDto = new EmployeeRowDto();
			List<ItemRowDto> items = new ArrayList<>();
			
			cells.stream().forEach( cell -> {
				String[] itemChilds = cell.getHeader().getMain().getValue().getText().split("＿");
				String header = cell.getHeader().getMain().getValue().getText();
				String headerTemp = itemChilds.length > 0? itemChilds[itemChilds.length - 1]: header;
				setValueItemDto(category, employees, cell, employeeDto, header, headerTemp,  headerReal, contraintList, items,  headerRemain, startCode);
			});
			employeeDto.setItems(items);
			if(employeeDto.getEmployeeCode()!= null) {
				employeeDtos.add(employeeDto);
			}
			
		});

	}
	
	private void setValueItemDto(PersonInfoCategory category, List<EmployeeDataMngInfo> employees, NtsExcelCell cell, EmployeeRowDto employeeDto,String header, String headerTemp,  List<GridEmpHead> headerReal,
			 HashMap<String, Object> contraintList, List<ItemRowDto> items, List<GridEmpHead> headerRemain, String startCode) {
		// lấy emloyeeCode, employeeName
		if (header.equals(TextResource.localize("CPS003_28"))) {
			// employeeId
			Optional<EmployeeDataMngInfo> emp = employees.stream()
					.filter(c -> c.getEmployeeCode().toString().equals(cell.getValue().getText())).findFirst();
			if (!emp.isPresent())
				return;
			employeeDto.setEmployeeCode(cell.getValue() == null ? "" : cell.getValue().getText());
			employeeDto.setEmployeeId(emp.get().getEmployeeId());
			employeeDto.setPersonId(emp.get().getPersonId());
			if(!category.isHistoryCategory()) {
				AffCompanyHist affComHist = this.companyHistRepo.getAffCompanyHistoryOfEmployee(emp.get().getEmployeeId());
				AffCompanyHistByEmployee affHistEmp = affComHist.getAffCompanyHistByEmployee(emp.get().getEmployeeId());
				List<AffCompanyHistItem>  affHistItemLst = affHistEmp.getLstAffCompanyHistoryItem();
				AffCompanyHistItem affHistItem = affHistItemLst.get(0);
				valueStartCode = affHistItem.start();
			}
			
		} else if (header.equals(TextResource.localize("CPS003_29"))) {
			employeeDto.setEmployeeName(cell.getValue() == null ? "" : cell.getValue().getText());
		} else {

			// lấy dữ liệu của itemName
			boolean isSelectionCode = headerTemp.contains("（コード）");
			if (isSelectionCode) {
				CheckFileFinder.header = headerTemp.split("（")[0];
			}
			Optional<GridEmpHead> headerGridOpt = headerReal.stream().filter(c -> {
				if (isSelectionCode) {
					return c.getItemName().equals(CheckFileFinder.header);
				} else {
					return c.getItemName().equals(headerTemp);
				}
			}).findFirst();

			if (headerGridOpt.isPresent()) {
				GridEmpHead headerGrid = headerGridOpt.get();
				Object contraint = contraintList.get(headerGrid.getItemCode());
				// trường hợp lấy ra baseDate theo file import, lấy startDate
				if(headerGrid.getItemCode().equals(startCode)) {
					DateConstraint dateContraint = (DateConstraint) contraint;
					Optional<String> error = dateContraint.validateString(cell.getValue() == null ? "" : cell.getValue().getText());
					if (category.isHistoryCategory()) {
						if (error.isPresent()) {
							valueStartCode = GeneralDate.today();
						}
						valueStartCode = cell.getValue() == null ? GeneralDate.today()
								: GeneralDate.fromString(cell.getValue().getText(), "yyyy/MM/dd");
					}
				}
				ItemRowDto empBody = new ItemRowDto();
				if (isSelectionCode) {
					String selectionCode = cell.getValue() == null ? "" : cell.getValue().getText();
					empBody.setItemCode(headerGrid.getItemCode());
					empBody.setItemName(headerGrid.getItemName());
					empBody.setItemOrder(headerGrid.getItemOrder());
					empBody.setValue(selectionCode);
					if (headerGrid.isRequired()) {
						if(cell.getValue() == null) {
							empBody.setError(true); 
						}
					}
					List<ComboBoxObject> comboxLst = this.getComboBox(headerGrid, category, employeeDto.getEmployeeId(), empBody, items);
					// thuật toán lấy selectionId, workplaceId, codeName,...
					Optional<ComboBoxObject> combo = comboxLst.stream().filter(c -> {
						if (c.getOptionText().contains(JP_SPACE)) {
							String[] stringSplit = c.getOptionText().split(JP_SPACE);
							if (stringSplit[0].equals(selectionCode)) {
								return true;
							}
							return false;
						} else {
							if (c.getOptionValue().equals(selectionCode)) {
								return true;
							}
							return false;
						}

					}).findFirst();
					empBody.setValue(combo.isPresent() == true ? combo.get().getOptionValue() : "");
					empBody.setLstComboBoxValue(comboxLst);
					items.add(empBody);
				} else {
					if (!headerGrid.getItemName().equals(CheckFileFinder.header)) {
						empBody.setItemCode(headerGrid.getItemCode());
						empBody.setItemName(headerGrid.getItemName());
						empBody.setItemOrder(headerGrid.getItemOrder());
						convertValue(empBody, headerGrid,
								cell.getValue() == null ? null : cell.getValue().getText(), contraint);
						empBody.setTextValue(empBody.getValue() == null ? "" : empBody.getValue().toString());
						items.add(empBody);
					}
				}
				headerRemain.add(headerGrid);
			}
		}
		
	}
	
	/**
	 * getComboBox
	 * @param headerGrid
	 * @param category
	 * @param sid
	 * @param empBody
	 * @param items
	 * @return
	 */
	public List<ComboBoxObject> getComboBox(GridEmpHead headerGrid, PersonInfoCategory category, String sid, ItemRowDto empBody, List<ItemRowDto> items) {
		ItemTypeStateDto dto = headerGrid.getItemTypeState();
		if(dto.getItemType() == ItemType.SINGLE_ITEM.value) {
			SingleItemDto singleDto = (SingleItemDto) dto;
				if (singleDto.getDataTypeState().getDataTypeValue() == DataTypeValue.SELECTION.value
						|| singleDto.getDataTypeState().getDataTypeValue() == DataTypeValue.SELECTION_BUTTON.value
						|| singleDto.getDataTypeState().getDataTypeValue() == DataTypeValue.SELECTION_RADIO.value) {
					boolean isDataType6 = singleDto.getDataTypeState().getDataTypeValue() == DataTypeValue.SELECTION.value?  true: false;
				SelectionItemDto selectionItemDto = (SelectionItemDto) singleDto.getDataTypeState();
				List<ComboBoxObject> combox = this.comboBoxRetrieveFactory.getComboBox(selectionItemDto, sid,
						GeneralDate.today(), headerGrid.isRequired(), category.getPersonEmployeeType(), isDataType6,
						category.getCategoryCode().v(), null, false);
				return combox;
				}
		}
		return new ArrayList<>();
	}
	
	/**
	 * generateContraint
	 * @param headerReal
	 * @return
	 */
	private HashMap<String, Object> generateContraint(List<GridEmpHead> headerReal){
		HashMap<String, Object> contraintList = new HashMap<>();
		
		headerReal.stream().forEach(c ->{
			ItemTypeStateDto itemTypeStateDto = c.getItemTypeState();
			Object obj;
			if(itemTypeStateDto.getItemType() == ItemType.SINGLE_ITEM.value) {
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
				default: break;
				}
				
			}
		});
		return contraintList;
	}
	
	/**
	 * convertValue kết hợp với validate
	 * @param itemDto
	 * @param gridHead
	 * @param value
	 * @param contraint
	 */
	private void convertValue(ItemRowDto itemDto, GridEmpHead gridHead, Object value, Object contraint) {
		if (gridHead.getItemTypeState().getItemType() == 2) {
			SingleItemDto singleDto = (SingleItemDto) gridHead.getItemTypeState();
			DataTypeStateDto dataTypeState = (DataTypeStateDto) singleDto.getDataTypeState();
			DataTypeValue itemValueType = EnumAdaptor.valueOf(dataTypeState.getDataTypeValue(), DataTypeValue.class);
			itemDto.setDataType(dataTypeState.getDataTypeValue());
			switch (itemValueType) {
			case STRING:
				itemDto.setValue(value == null? null: value.toString());
				StringConstraint stringContraint = (StringConstraint) contraint;
				if (gridHead.isRequired()) {
					if (value == null) {
						itemDto.setError(true);
						break;
					} else {
						Optional<String> string = stringContraint.validateString(value.toString());
						validateItemOfCS0002(itemDto, value.toString());
						if (string.isPresent()) {
							itemDto.setError(true);
							break;
						}
					}
				} else {
					if (value != null) {
						Optional<String> string = stringContraint.validateString(value.toString());
						validateItemOfCS0002(itemDto, value.toString());
						if (string.isPresent()) {
							itemDto.setError(true);
							break;
						}
					}
				}
				break;
			case NUMERIC:
			case NUMBERIC_BUTTON:
				itemDto.setValue(value == null ? null : new BigDecimal(value.toString()));
				NumericConstraint numberContraint = (NumericConstraint) contraint;
				if (gridHead.isRequired()) {
					if (value == null) {
						itemDto.setError(true);
						break;
					} else {
						Optional<String> string = numberContraint.validateString(value.toString());
						if (string.isPresent()) {
							itemDto.setError(true);
							break;
						}
					}
				} else {
					if (value != null && value != "") {
						Optional<String> string = numberContraint.validateString(value.toString());
						if (string.isPresent()) {
							itemDto.setError(true);
							break;
						}
					}
				}
				break;
			case DATE:
				itemDto.setValue(value == null || value == "" ? null : value.toString());
				DateConstraint dateContraint = (DateConstraint) contraint;
				if (gridHead.isRequired()) {
					if (value == null || value == "") {
						itemDto.setError(true);
						break;
					} else {
						Optional<String>  string = dateContraint.validateString(value.toString());
						if (string.isPresent()) {
							itemDto.setError(true);
							break;
						}
					}
				} else {
					if (value != null && value != "") {
						Optional<String>  string = dateContraint.validateString(value.toString());
						if (string.isPresent()) {
							itemDto.setError(true);
							break;
						}
					}
				}
				break;
			case TIME:
				TimeConstraint timeContraint = (TimeConstraint) contraint;
				if (gridHead.isRequired()) {
					if (value == null) {
						itemDto.setError(true);
						break;
					} else {
						String[] stringSplit = value.toString().split(":");
						if(stringSplit.length >= 1  && stringSplit.length < 3) {
							if(isNumeric(stringSplit[0]) && isNumeric(stringSplit[1])) {
								itemDto.setValue(new BigDecimal(MinutesBasedTimeParser.parse(value.toString()).asDuration()));
							}else {
								itemDto.setValue(value.toString());
							}
						}else {
							itemDto.setValue(value.toString());
						}
						
						Optional<String> string = timeContraint.validateString(value.toString());
						if (string.isPresent()) {
							itemDto.setError(true);
							break;
						}
					}
				} else {
					if (value != null && value != "") {
						String[] stringSplit = value.toString().split(":");
						if(stringSplit.length >= 1 && stringSplit.length < 3) {
							if(isNumeric(stringSplit[0]) && isNumeric(stringSplit[1])) {
								itemDto.setValue(new BigDecimal(MinutesBasedTimeParser.parse(value.toString()).asDuration()));
							}else {
								itemDto.setValue(value.toString());
							}
						}else {
							itemDto.setValue(value.toString());
						}
						Optional<String> string = timeContraint.validateString(value.toString());
						if (string.isPresent()) {
							itemDto.setError(true);
							break;
						}
					}
				}
				break;
			case TIMEPOINT:
				TimePointConstraint timePointContraint = (TimePointConstraint) contraint;
				if (gridHead.isRequired()) {
					if (value != null && value != "") {
						itemDto.setValue(convertTimepoint(value.toString()));
						Optional<String> string = timePointContraint.validateString(value.toString());
						if (string.isPresent()) {
							itemDto.setError(true);
							break;
						}
					} else {
						itemDto.setError(true);
					}
				} else {
					if (value != null && value != "") {
						itemDto.setValue(convertTimepoint(value.toString()));
						Optional<String> string = timePointContraint.validateString(value.toString());
						if (string.isPresent()) {
							itemDto.setError(true);
							break;
						}
					}
				}
				break;
				
			case SELECTION:
			case SELECTION_BUTTON:
			case SELECTION_RADIO:
				if (gridHead.isRequired()) {
					if(value == null || value == "") {
						itemDto.setError(true); break;
					}
				}
				break;
			default:
				break;
			}
		}

	}
	
	private boolean isNumeric(String str)
	{
	  return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}
	
	/**
	 * validate những item đặc biệt của category CS0002
	 * validateItemOfCS0002
	 * @param itemDto
	 * @param value
	 */
	private void validateItemOfCS0002(ItemRowDto itemDto, String value){
		for (String itemCode : itemSpecialLst) {
			if (itemDto.getItemCode().equals(itemCode)) {
				if (value.startsWith(JP_SPACE) || value.endsWith(JP_SPACE)
						|| !value.contains(JP_SPACE)) {
					itemDto.setError(true);
					break;
				}
			}
		}
	}
	
	/**
	 * danh cho item kieu timepoint - 5
	 * convertTimepoint -> int
	 * @param value
	 * @return
	 */
	private String convertTimepoint(String value) {
		List<String> day = Arrays.asList("当日","前日","翌日");
		for(int i = 0; i< day.size(); i++) {
			if(value.matches((day.get(i)+"(.*)"))) {
				String[] e = value.split("日");
				try {
				int minute= MinutesBasedTimeParser.parse(e[1]).asDuration();
				switch(i) {
				case 0:
					return String.valueOf(minute);
				case 1:
					return String.valueOf(-minute);
				case 2:
					return String.valueOf(minute+ 24*60);
				}
				
				}catch(RuntimeException re){
					 return value;
					
				}

			}
		}
		return value;
	}
	
	@RequiredArgsConstructor
	public enum UpdateMode {
		OVERIDED(1), ADDHISTORY(2);
		public final int value;

	}

}


