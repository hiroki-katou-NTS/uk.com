package nts.uk.file.com.infra.generate.person.matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import com.aspose.cells.BackgroundType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Color;
import com.aspose.cells.Column;
import com.aspose.cells.Font;
import com.aspose.cells.PageSetup;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
import nts.uk.file.com.app.person.matrix.PersonInfoMatrixGenerator;
import nts.uk.file.com.app.person.matrix.datasource.FixedColumnDisplay;
import nts.uk.file.com.app.person.matrix.datasource.GridEmpBodyDataSource;
import nts.uk.file.com.app.person.matrix.datasource.GridEmployeeInfoDataSource;
import nts.uk.file.com.app.person.matrix.datasource.GridHeaderData;
import nts.uk.file.com.app.person.matrix.datasource.PersonInfoMatrixDataSource;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;
import nts.uk.shr.pereg.app.ComboBoxObject;
@Stateless
public class AsposePersonInfoMatrixGenerator extends AsposeCellsReportGenerator implements PersonInfoMatrixGenerator{

	private static final String REPORT_ID = "PersonMatrix";
	 /** The Constant EXTENSION_FILE. */
    private static final String EXTENSION_FILE = ".xlsx";
    
    private static final String SPACE = "＿";
    //（コード
    private static final String SELCODE = "（コード）";
	private static final String JP_SPACE = "　";
	@Override
	public void generate(FileGeneratorContext generatorContext, PersonInfoMatrixDataSource dataSource) {
		val reportContext = this.createEmptyContext(REPORT_ID);
		String fileName = dataSource.getCategoryName()+ GeneralDateTime.now().toString("yyyyMMddHHmmss")+ EXTENSION_FILE;
		Workbook workbook = reportContext.getWorkbook(); 
		Worksheet worksheet = workbook.getWorksheets().get(0);
		FixedColumnDisplay fixedCol = new FixedColumnDisplay(dataSource.getFixedHeader().isShowDepartment(),
				dataSource.getFixedHeader().isShowWorkplace(), dataSource.getFixedHeader().isShowPosition(),
				dataSource.getFixedHeader().isShowEmployment(), dataSource.getFixedHeader().isShowClassification());
		setWidthColum(worksheet, dataSource);
		List<GridHeaderData> gridHeader =  converHeaderData(dataSource.getDynamicHeader());
		printPage(worksheet);
        int numberOfColumnHeader = numberOfColumnHeader(dataSource, gridHeader);
		int row = 0;
		setHeader(worksheet, dataSource, gridHeader, numberOfColumnHeader, row);
		printEmployeeInfo(worksheet, dataSource, fixedCol, gridHeader, numberOfColumnHeader, row);
		reportContext.processDesigner();
		reportContext.saveAsExcel(this.createNewFile(generatorContext, fileName));
	}
	
	/**
	 * setHeader
	 * @param worksheet
	 * @param dataSource
	 * @param numberOfColumnHeader
	 * @param row
	 */
	private void setHeader(Worksheet worksheet, PersonInfoMatrixDataSource dataSource, List<GridHeaderData> gridHeader, int numberOfColumnHeader, int row) {
		FixedColumnDisplay fixedCol = dataSource.getFixedHeader();
		// tính thêm cột selectionCode
		List<GridHeaderData> itemSelection = gridHeader.stream()
				.filter(c -> c.getItemTypeState().getItemType() == 2
						&& (c.getItemTypeState().getDataTypeState().getDataTypeValue() == DataTypeValue.SELECTION.value
								|| c.getItemTypeState().getDataTypeState().getDataTypeValue() == DataTypeValue.SELECTION_BUTTON.value
								|| c.getItemTypeState().getDataTypeState().getDataTypeValue() == DataTypeValue.SELECTION_RADIO.value))
				.collect(Collectors.toList());
		int numberOfFixed = numberOfColumnHeader - gridHeader.size() - itemSelection.size();
		for(int i = 0; i < numberOfColumnHeader; i++) {
			Cell cell = worksheet.getCells().get(row, i );
			if(i < numberOfFixed) {
				setHeaderStyle(cell, false, true);
			}
			if (i == 0) {
				cell.setValue(TextResource.localize("CPS003_28"));
			} else if (i == 1) {
				cell.setValue(TextResource.localize("CPS003_29"));
			} else {
				// in ra những cột cố định
				if(i < numberOfFixed && i > 1) {
					if(fixedCol.isShowDepartment()) {
						cell.setValue(TextResource.localize("CPS003_30"));
						fixedCol.setShowDepartment(false);
						continue;
					}
					
					if(fixedCol.isShowWorkplace()) {
						cell.setValue(TextResource.localize("CPS003_31"));
						fixedCol.setShowWorkplace(false);
						continue;
					}
					
					if(fixedCol.isShowPosition()) {
						cell.setValue(TextResource.localize("CPS003_32"));
						fixedCol.setShowPosition(false);
						continue;
					}
					
					if(fixedCol.isShowEmployment()) {
						cell.setValue(TextResource.localize("CPS003_33"));
						fixedCol.setShowEmployment(false);
						continue;
						
					}
					
					if(fixedCol.isShowClassification()) {
						cell.setValue(TextResource.localize("CPS003_34"));
						fixedCol.setShowClassification(false);
						continue;
					}
				}else {
					// in ra những cột động
					int dynamicPositionStart = i;
					for(int j = 0; j < gridHeader.size(); j ++) {
						GridHeaderData header = gridHeader.get(j);
						if(header.getItemTypeState().getDataTypeState().getDataTypeValue() == DataTypeValue.SELECTION.value
								|| header.getItemTypeState().getDataTypeState().getDataTypeValue() == DataTypeValue.SELECTION_BUTTON.value
								|| header.getItemTypeState().getDataTypeState().getDataTypeValue() == DataTypeValue.SELECTION_RADIO.value ) {
							Cell selCodeCell = worksheet.getCells().get(row, dynamicPositionStart + j );
							selCodeCell.setValue(header.getItemName()+ SELCODE);
							setHeaderStyle(selCodeCell, header.isRequired(), false);
							Cell selNameCell = worksheet.getCells().get(row, dynamicPositionStart + j + 1);
							selNameCell.setValue(header.getItemName());
							setHeaderStyle(selNameCell, header.isRequired(), false);
							// tăng dynamicPositionStart  thêm 1 bởi vì thêm cột code
							dynamicPositionStart = dynamicPositionStart + 1;
							 i = i + 2;
						}else {
							Cell cellDynamic = worksheet.getCells().get(row, dynamicPositionStart + j );
							cellDynamic.setValue(header.getItemName());
							setHeaderStyle(cellDynamic, header.isRequired(), false);
							 i++;
						}
						
					}
				}
				
			}
			
		}
		
	}
	
	private List<GridHeaderData> converHeaderData(List<GridHeaderData> dynamicHeader){
		List<GridHeaderData> itemSetLst = dynamicHeader.stream()
				.filter(c -> c.getItemTypeState().getItemType() == 1 || c.getItemTypeState().getItemType() == 3)
				.collect(Collectors.toList());
		List<GridHeaderData> itemSingleLst = dynamicHeader.stream()
				.filter(c -> c.getItemTypeState().getItemType() == 2)
				.collect(Collectors.toList());
		itemSingleLst.stream().forEach(c ->{
			if(c.getItemParentCode()  != null  || !c.getItemParentCode().isEmpty()) {
				Optional<GridHeaderData> parentItemOpt = itemSetLst.stream().filter(set -> set.getItemCode().equals(c.getItemParentCode())).findFirst();
				if(parentItemOpt.isPresent()) {
					GridHeaderData parentItem = parentItemOpt.get();
					String itemName = c.getItemName();
					c.setItemName(parentItem.getItemName() + SPACE + itemName);
				}
			}
			
			
		});
		return itemSingleLst;
	}
	
	private void printEmployeeInfo(Worksheet worksheet, PersonInfoMatrixDataSource dataSource, FixedColumnDisplay fixedCol, List<GridHeaderData> gridHeader, int numberOfColumnHeader, int row) {
		// bởi vì row = 0 in ra header, bắt đầu in ra employee thì row sẽ  = 1
		row = 1;
		// tính thêm cột selectionCode
		List<GridHeaderData> itemSelection = gridHeader.stream()
				.filter(c -> c.getItemTypeState().getItemType() == 2
						&& (c.getItemTypeState().getDataTypeState().getDataTypeValue() == DataTypeValue.SELECTION.value
								|| c.getItemTypeState().getDataTypeState().getDataTypeValue() == DataTypeValue.SELECTION_BUTTON.value
								|| c.getItemTypeState().getDataTypeState().getDataTypeValue() == DataTypeValue.SELECTION_RADIO.value))
				.collect(Collectors.toList());
		int numberOfFixed = numberOfColumnHeader - gridHeader.size() - itemSelection.size();
		List<GridEmployeeInfoDataSource> detailData = dataSource.getDetailData();
		FixedColumnDisplay fixedColSource = new FixedColumnDisplay(fixedCol.isShowDepartment(),
				fixedCol.isShowWorkplace(), fixedCol.isShowPosition(),
				fixedCol.isShowEmployment(), fixedCol.isShowClassification());
		for(GridEmployeeInfoDataSource employeeInfo : detailData) {
			FixedColumnDisplay fixedColTemp = new FixedColumnDisplay(fixedColSource.isShowDepartment(),
					fixedColSource.isShowWorkplace(), fixedColSource.isShowPosition(),
					fixedColSource.isShowEmployment(), fixedColSource.isShowClassification());
			for(int i = 0; i < numberOfColumnHeader; i++) {
				Cell cell = worksheet.getCells().get(row, i );
				if (i == 0) {
					cell.setValue(employeeInfo.getEmployeeCode());
					setBodyStyle(cell);
				} else if (i == 1) {
					cell.setValue(employeeInfo.getEmployeeName());
					setBodyStyle(cell);
				} else {
					// in ra những cột cố định
					if(i < numberOfFixed) {
						if(fixedColTemp.isShowDepartment()) {
							cell.setValue(employeeInfo.getDepartmentName());
							setBodyStyle(cell);
							fixedColTemp.setShowDepartment(false);
							continue;
						}
						
						if(fixedColTemp.isShowWorkplace()) {
							cell.setValue(employeeInfo.getWorkplaceName());
							setBodyStyle(cell);
							fixedColTemp.setShowWorkplace(false);
							continue;
						}
						
						if(fixedColTemp.isShowPosition()) {
							cell.setValue(employeeInfo.getPositionName());
							setBodyStyle(cell);
							fixedColTemp.setShowPosition(false);
							continue;
						}
						
						if(fixedColTemp.isShowEmployment()) {
							cell.setValue(employeeInfo.getEmploymentName());
							setBodyStyle(cell);
							fixedColTemp.setShowEmployment(false);
							continue;
							
						}
						
						if(fixedColTemp.isShowClassification()) {
							cell.setValue(employeeInfo.getClassificationName());
							setBodyStyle(cell);
							fixedColTemp.setShowClassification(false);
							continue;
						}
					}else {
						// in ra những cột động
						int dynamicPositionStart = i;
						List<GridEmpBodyDataSource> items = employeeInfo.getItems();
						for(int j = 0; j < gridHeader.size(); j ++) {
							GridHeaderData header = gridHeader.get(j);
							for(GridEmpBodyDataSource item: items) {
								if(item.getItemCode().equals(gridHeader.get(j).getItemCode())) {
									if (header.getItemTypeState().getDataTypeState().getDataTypeValue() == DataTypeValue.SELECTION.value
											|| header.getItemTypeState().getDataTypeState().getDataTypeValue() == DataTypeValue.SELECTION_BUTTON.value
											|| header.getItemTypeState().getDataTypeState().getDataTypeValue() == DataTypeValue.SELECTION_RADIO.value) {
										Cell selCodeCell = worksheet.getCells().get(row, dynamicPositionStart + j );
										Cell selNameCell = worksheet.getCells().get(row, dynamicPositionStart + j + 1 );
										if(item.getValue() != null) {
											Optional<ComboBoxObject> comboxObjOpt = item.getLstComboBoxValue().stream().filter(combo -> combo.getOptionValue().equals(item.getValue().toString())).findFirst();
											if(comboxObjOpt.isPresent()) {
												ComboBoxObject comboxObj = comboxObjOpt.get();
												if(!header.getItemTypeState().getDataTypeState().getReferenceType().equals("ENUM")) {
													if(header.getItemTypeState().getDataTypeState().getReferenceType().equals("CODE_NAME")) {
														String[] stringSplit = comboxObj.getOptionText().split(JP_SPACE);
														String selCode = stringSplit.length > 0? stringSplit[0]: "";
														selCodeCell.setValue(selCode);
														selNameCell.setValue(stringSplit.length > 1? stringSplit[1]: stringSplit[0]);	
													}else {
														String[] stringSplit = comboxObj.getOptionText().split(JP_SPACE);
														String selCode = dataSource.getCategoryCode().equals("CS00016") || dataSource.getCategoryCode().equals("CS00017")?  (stringSplit.length > 0? stringSplit[0]: ""): comboxObj.getOptionValue();
														selCodeCell.setValue(selCode);
														selNameCell.setValue(stringSplit.length > 1? stringSplit[1]: stringSplit[0]);														
													}

												}else {
													selCodeCell.setValue(comboxObj.getOptionValue());
													selNameCell.setValue(comboxObj.getOptionText());
												}

											}else {
												selCodeCell.setValue(null);
												selNameCell.setValue(null);
											}
											
										}else {
											selCodeCell.setValue(null);
											selNameCell.setValue(null);
										}
										setBodyStyle(selCodeCell);
										setBodyStyle(selNameCell);
										// tăng dynamicPositionStart  thêm 1 bởi vì thêm cột code
										dynamicPositionStart = dynamicPositionStart + 1;
										i =  i + 2;
										continue;

									}else {
										Cell cellDynamic = worksheet.getCells().get(row, dynamicPositionStart + j );
										if(header.getItemTypeState().getDataTypeState().getDataTypeValue() == DataTypeValue.TIMEPOINT.value) {
											String value = item.getValue() == null? null: item.getValue().toString();
											cellDynamic.setValue(value == null? null: convertTimepoint(value));
											
										}else {
											cellDynamic.setValue(item.getValue() == null? null: item.getValue().toString());
										}
										setBodyStyle(cellDynamic);
										i++;
										continue;

										
									}

								}
							}
						}
					}
					
				}
				
			}
			row ++;
		}
		
	}
	
	private String convertTimepoint(String value) {
		String result = "";
		String[] valueSplit = value.split(":");
		if(valueSplit.length > 0) {
			if(valueSplit.length == 2) {
				if(!isNumeric(valueSplit[0]) || !isNumeric(valueSplit[1])) return value;
			}
			if(valueSplit.length > 2) {
				 return value;
			}
			
			if(valueSplit.length == 1) {
				if(!isNumeric(valueSplit[0])) return value;
			}
			int hours = Integer.valueOf(valueSplit[0]).intValue();
			int day = hours/24;
			
			if (hours < -1) {
				return "前日" + Math.abs(hours) + ":"+ valueSplit[1];
			}
			
			if (day == 0) {
				return "当日" + hours + ":"+ valueSplit[1];
			}
			
			if(day == 1) {
				return "翌日" + (hours - 24) + ":"+ valueSplit[1];
			}
			
			if(day == 2) {
				return "翌々日" + (hours - 48) + ":"+ valueSplit[1];
				
			}
		}
		return result;
	}
	
	private boolean isNumeric(String str)
	{
	  return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}
	private int numberOfColumnHeader(PersonInfoMatrixDataSource dataSource, List<GridHeaderData> gridHeader ) {
		// tính thêm cột selectionCode
		List<GridHeaderData> itemSelection = gridHeader.stream()
				.filter(c -> c.getItemTypeState().getItemType() == 2
						&& (c.getItemTypeState().getDataTypeState().getDataTypeValue() == DataTypeValue.SELECTION.value
								|| c.getItemTypeState().getDataTypeState().getDataTypeValue() == DataTypeValue.SELECTION_BUTTON.value
								|| c.getItemTypeState().getDataTypeState().getDataTypeValue() == DataTypeValue.SELECTION_RADIO.value))
				.collect(Collectors.toList());
		int indexColumns = gridHeader.size()+ itemSelection.size() + 2; //cộng thêm cột employeeCode và employeeName
		if(dataSource.getFixedHeader().isShowDepartment()) {
			indexColumns++;
		}
		
		if(dataSource.getFixedHeader().isShowWorkplace()) {
			indexColumns++;
		}
		
		if(dataSource.getFixedHeader().isShowPosition()) {
			indexColumns++;
		}
		
		if(dataSource.getFixedHeader().isShowEmployment()) {
			indexColumns++;
		}
		
		if(dataSource.getFixedHeader().isShowClassification()) {
			indexColumns++;
		}
		return indexColumns;
	}
	

	/**
	 * PRINT PAGE
	 * 
	 * @param worksheet
	 * @param lstWorkplace
	 */
	private void printPage(Worksheet worksheet) {
		// Set print page
		PageSetup pageSetup = worksheet.getPageSetup();
		pageSetup.setFirstPageNumber(1);
	}
	
	/**
	 * ý tưởng cho đoan này: khi nhận được width thì làm cho giống index trên header để set width cho cột
	 * setWidthColum
	 * @param worksheet
	 * @param dataSource
	 */
	private void setWidthColum(Worksheet worksheet, PersonInfoMatrixDataSource dataSource) {
		
		Map<String, Integer> width = dataSource.getWidth();
		List<Integer> widthLst = new ArrayList<>();
		FixedColumnDisplay fixedCol = dataSource.getFixedHeader();
		List<GridHeaderData> dynamicCol = dataSource.getDynamicHeader();
		
		Integer employeeCode = width.get("employeeCode");
		widthLst.add(employeeCode.intValue());
		Integer employeeName = width.get("employeeName");
		widthLst.add(employeeName.intValue());
		
		if(fixedCol.isShowClassification()) {
			Integer w = width.get("className");
			widthLst.add(w.intValue());
		}
		
		if(fixedCol.isShowDepartment()) {
			Integer w = width.get("deptName");
			widthLst.add(w.intValue());
		}
		
		if(fixedCol.isShowEmployment()) {
			Integer w = width.get("employmentName");
			widthLst.add(w.intValue());
		}
		
		if(fixedCol.isShowPosition()) {
			Integer w = width.get("positionName");
			widthLst.add(w.intValue());
		}
		
		if(fixedCol.isShowWorkplace()) {
			Integer w = width.get("workplaceName");
			widthLst.add(w.intValue());
		}
		
		dynamicCol.stream().forEach(c ->{
			if(c.getItemTypeState().getItemType() == 2) {
				Integer w = width.get(c.getItemCode());
				widthLst.add(w);
			}
		});
		// chia cho 7 là vì so với tỉ lệ trên grid với excel lệch nhau 7 lần
		for(int i = 0; i < widthLst.size(); i++) {
			Column column = worksheet.getCells().getColumns().get(i);
			column.setWidth(widthLst.get(i).intValue()/7);
		}
	}
	
	/**
	 * Sets the title style.
	 *
	 * @param cell
	 *            the new title style
	 */
	private void setHeaderStyle(Cell cell, boolean isRequired, boolean isFixed) {
		Style style = cell.getStyle();
		//format cell string
		style.setNumber(49);
		style.setPattern(BackgroundType.SOLID);
		style.setBorder(BorderType.LEFT_BORDER, CellBorderType.THIN, Color.getBlack());
		style.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
		style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
		style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getBlack());
		style.setTextWrapped(true);
		
		if(isFixed) {
			style.setForegroundColor(Color.getGray());
		}else if(!isFixed && isRequired) {
			style.setForegroundColor(Color.getOrange());
		}else {
			style.setForegroundColor(Color.fromArgb(207, 241, 165));
		}
		Font font = style.getFont();
		font.setName("MS ゴシック");
		cell.setStyle(style);
	}
	
	/**
	 * Sets the title style.
	 *
	 * @param cell
	 *            the new title style
	 */
	private void setBodyStyle(Cell cell) {
		Style style = cell.getStyle();
		//format cell string
		style.setNumber(49);
		style.setPattern(BackgroundType.SOLID);
		style.setBorder(BorderType.LEFT_BORDER, CellBorderType.THIN, Color.getBlack());
		style.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
		style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
		style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getBlack());
		style.setTextWrapped(true);
		Font font = style.getFont();
		font.setName("MS ゴシック");
		cell.setStyle(style);
	}

}
