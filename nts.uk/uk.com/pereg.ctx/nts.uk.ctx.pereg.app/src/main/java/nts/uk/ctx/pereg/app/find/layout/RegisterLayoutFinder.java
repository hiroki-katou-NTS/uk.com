package nts.uk.ctx.pereg.app.find.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.app.command.addemployee.AddEmployeeCommand;
import nts.uk.ctx.pereg.app.find.common.ComboBoxRetrieveFactory;
import nts.uk.ctx.pereg.app.find.common.InitDefaultValue;
import nts.uk.ctx.pereg.app.find.copysetting.item.CopySettingItemFinder;
import nts.uk.ctx.pereg.app.find.initsetting.item.FindInitItemDto;
import nts.uk.ctx.pereg.app.find.initsetting.item.InitValueSetItemFinder;
import nts.uk.ctx.pereg.app.find.initsetting.item.SettingItemDto;
import nts.uk.ctx.pereg.app.find.layoutdef.NewLayoutDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsFinder;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoValueDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefDto;
import nts.uk.ctx.pereg.app.find.person.info.item.SelectionItemDto;
import nts.uk.ctx.pereg.app.find.person.info.item.SingleItemDto;
import nts.uk.ctx.pereg.app.find.person.setting.init.category.PerInfoInitValueSettingCtgFinder;
import nts.uk.ctx.pereg.dom.person.info.category.CategoryType;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.category.dto.DateRangeDto;
import nts.uk.ctx.pereg.dom.person.info.item.ItemType;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
import nts.uk.ctx.pereg.dom.person.layout.INewLayoutReposotory;
import nts.uk.ctx.pereg.dom.person.layout.NewLayout;
import nts.uk.ctx.pereg.dom.person.layout.classification.LayoutItemType;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregQuery;

/**
 * @author sonnlb
 *
 */
@Stateless
public class RegisterLayoutFinder {


	// sonnlb start code
	@Inject
	private INewLayoutReposotory repo;

	@Inject
	private LayoutPersonInfoClsFinder clsFinder;

	@Inject
	private PerInfoInitValueSettingCtgFinder initCtgSettingFinder;

	@Inject
	private CopySettingItemFinder copyItemFinder;

	@Inject
	private InitValueSetItemFinder initItemFinder;

	@Inject
	private ComboBoxRetrieveFactory cbbfact;
	@Inject

	private InitDefaultValue initDefaultValue;
	
	@Inject 
	private PerInfoCategoryRepositoty perInfoCategoryRepositoty;
	
	private static final String END_DATE_NAME = "終了日";
	
	// biến này dùng để tính baseDate là ngày nào, 
	// là ngày vào công ty hay là ngày start date của category, ko cần dùng static
	private GeneralDate baseDate = null;
	
	/**
	 * get Layout Dto by create type
	 * 
	 * @param command
	 *            : command from client push to webservice
	 * @return NewLayoutDto
	 */
	public NewLayoutDto getLayoutByCreateType(AddEmployeeCommand command) {

		Optional<NewLayout> layout = repo.getLayout();
		if (!layout.isPresent()) {

			return null;
		}

		NewLayout _layout = layout.get();

		StringBuilder wrkPlaceStartDate = new StringBuilder();
		List<LayoutPersonInfoClsDto> itemCls = getClassItemList(command, _layout,wrkPlaceStartDate);
		
		return NewLayoutDto.fromDomain(_layout, itemCls, wrkPlaceStartDate.toString());

	}

	private List<LayoutPersonInfoClsDto> getClassItemList(AddEmployeeCommand command, NewLayout _layout, StringBuilder wrkPlaceStartDate) {

		List<LayoutPersonInfoClsDto> classItemList = this.clsFinder.getListClsDtoHasCtgCd(_layout.getLayoutID());
		List<SettingItemDto> dataServer = new ArrayList<>();
		if (command.getCreateType() != 3) {
			// get data from server
			dataServer = this.getSetItems(command, false);
		}
		String workPlaceId = null;
		List<SettingItemDto> workPlace = dataServer.stream()
				.filter(i -> i.getCategoryCode().equals("CS00017") && (i.getItemCode().equals("IS00084") || i.getItemCode().equals("IS00082")) ).collect(Collectors.toList());
		if (!workPlace.isEmpty()){
			workPlaceId = workPlace.stream().filter(i -> i.getItemCode().equals("IS00084")).findFirst()
					.map(v -> v.getSaveData().getValue().toString()).orElse(null);
			wrkPlaceStartDate = wrkPlaceStartDate.append(workPlace.stream().filter(i -> i.getItemCode().equals("IS00082")).findFirst()
			.map(v -> v.getSaveData().getValue().toString()).orElse(""));
		}
		// set to layout's item
		mapToLayoutItems(classItemList, dataServer, command.getHireDate(),workPlaceId, command.getCreateType());;
		
		// check and set 9999/12/31 to endDate
		classItemList.forEach(classItem -> {
			if (classItem.getCtgType() == CategoryType.CONTINUOUSHISTORY.value && classItem.getItems().size() == 3) {
				LayoutPersonInfoValueDto theThirdItem = (LayoutPersonInfoValueDto) classItem.getItems().get(2);
				if (theThirdItem.getItemName().equals(END_DATE_NAME) && theThirdItem.getValue() == null ) {
					theThirdItem.setValue(GeneralDate.max());
				}
			}
		});

		// check and set employeeName to businessName
		Optional<LayoutPersonInfoClsDto> businessNameOpt = classItemList.stream()
				.filter(classItem -> classItem.getLayoutItemType() != LayoutItemType.SeparatorLine)
				.filter(classItem -> {
					LayoutPersonInfoValueDto item = (LayoutPersonInfoValueDto) classItem.getItems().get(0);
					return item.getItemCode().equals("IS00009");
				}).findFirst();
		if ( businessNameOpt.isPresent()) {
			LayoutPersonInfoClsDto businessName = businessNameOpt.get();
			LayoutPersonInfoValueDto item = (LayoutPersonInfoValueDto) businessName.getItems().get(0);
			if ( item.getValue() == null ) {
				item.setValue(command.getEmployeeName());
			}
		}
		
		// set default value
		initDefaultValue.setDefaultValue(classItemList);

		return classItemList;
	}
	
	private void mapToLayoutItems(List<LayoutPersonInfoClsDto> classItemList, List<SettingItemDto> dataServer, GeneralDate hireDate, String workPlaceId, int createType) {
		Map<String, List<LayoutPersonInfoClsDto>> mapByCategory = classItemList.stream()
				.filter(classItem -> classItem.getLayoutItemType() != LayoutItemType.SeparatorLine)
				.collect(Collectors.groupingBy(LayoutPersonInfoClsDto::getPersonInfoCategoryID));
		List<DateRangeDto> ctgCode = perInfoCategoryRepositoty.dateRangeCode();
		GeneralDate hireDateOld = hireDate;
		for (Map.Entry<String, List<LayoutPersonInfoClsDto>>  entry : mapByCategory.entrySet()) {
			
			Optional<PersonInfoCategory> perInfoCategory = perInfoCategoryRepositoty
					.getPerInfoCategory(entry.getKey(), AppContexts.user().contractCode());
			if (!perInfoCategory.isPresent()) {
				throw new RuntimeException("invalid PersonInfoCategory");
			}
			if(createType == 2 || createType == 1) {
				Optional<DateRangeDto> dateRangeOp = ctgCode.stream().filter(c -> c.getCtgCode().equals(perInfoCategory.get().getCategoryCode().toString())).findFirst();
				if(dateRangeOp.isPresent()) {
					DateRangeDto period = dateRangeOp.get();
					Optional<SettingItemDto> settingItemDto = dataServer.stream()
							.filter(item -> item.getItemCode().equals(period.getStartDateCode())).findFirst();
					if(settingItemDto.isPresent()) {
						hireDate = GeneralDate.fromString(settingItemDto.get().getSaveData().getValue().toString(), "yyyy/MM/dd");
					}
				}
			}
			baseDate = hireDate;
			for (LayoutPersonInfoClsDto classItem : entry.getValue()) {
				List<LayoutPersonInfoValueDto> items = classItem.getListItemDf().stream().map(itemDef -> {
					Optional<SettingItemDto> dataServerItemOpt = dataServer.stream()
							.filter(item -> item.getItemDefId().equals(itemDef.getId())).findFirst();
					return createLayoutItemByDef(dataServerItemOpt, itemDef, classItem, baseDate, perInfoCategory.get(),workPlaceId);
				}).collect(Collectors.toList());
				
				// clear definitionItem's list
				classItem.getListItemDf().clear();

				classItem.setItems(items);
			}
			hireDate = hireDateOld;
		}
	}

	private LayoutPersonInfoValueDto createLayoutItemByDef(Optional<SettingItemDto> dataServerItemOpt,
			PerInfoItemDefDto itemDef, LayoutPersonInfoClsDto classItem, GeneralDate hireDate,
			PersonInfoCategory perInfoCategory, String workPlaceId) {
		
		// initial basic info from definition item
		LayoutPersonInfoValueDto item = LayoutPersonInfoValueDto.createFromDefItem(perInfoCategory, itemDef);
		
		item.setCategoryName(perInfoCategory.getCategoryName().toString());
		item.setCtgType(perInfoCategory.getCategoryType().value);
		
		// get value
		if (dataServerItemOpt.isPresent()) {
			item.setValue(dataServerItemOpt.get().getSaveData().getValue());
		}

		if (itemDef.getItemTypeState().getItemType() == ItemType.SINGLE_ITEM.value) {
			// set DataTypeState
			SingleItemDto sigleItem = (SingleItemDto) itemDef.getItemTypeState();
			item.setItem(sigleItem.getDataTypeState());

			// get and set comboBox
			int dataTypeValue = item.getItem().getDataTypeValue();

			if (dataTypeValue == DataTypeValue.SELECTION.value || dataTypeValue == DataTypeValue.SELECTION_BUTTON.value
					|| dataTypeValue == DataTypeValue.SELECTION_RADIO.value) {

				SelectionItemDto selectionItemDto = null;
				selectionItemDto = (SelectionItemDto) item.getItem();
				boolean isDataType6 = dataTypeValue == DataTypeValue.SELECTION.value;
				List<ComboBoxObject> comboValues = cbbfact.getComboBox(selectionItemDto, null, hireDate,
						item.isRequired(), perInfoCategory.getPersonEmployeeType(), isDataType6,
						perInfoCategory.getCategoryCode().v(), workPlaceId, false);
				item.setLstComboBoxValue(comboValues);

				// value of item in comboBox is string
				if (item.getValue() != null) {
					item.toStringValue();
				}

			}
		}
		
		return item;
	}

	/**
	 * load All PeregDto in database by createType
	 * 
	 * @param createType
	 *            : type client need create data
	 * @param initSettingId
	 *            : settingId need find item in
	 * @param baseDate
	 *            : date need find
	 * @param employeeCopyId
	 *            : id of employee copy
	 * @return SettingItemDto List
	 */
	public List<SettingItemDto> getSetItems(AddEmployeeCommand command , boolean isRegisFrLayoutCPS002) {

		// Copy Type
		if (command.getCreateType() == 1) {

			return getCopyItems(command);

		} else {
			// Init Value Type

			return getInitItemsBySetId(command , isRegisFrLayoutCPS002);

		}
	}
	// sonnlb code end

	public List<SettingItemDto> getInitItemsBySetId(AddEmployeeCommand command , boolean isRegisFrLayoutCPS002) {
		List<PeregQuery> querys = new ArrayList<PeregQuery>();
		List<SettingItemDto> result = new ArrayList<SettingItemDto>();

		this.initCtgSettingFinder.getAllCategoryBySetId(command.getInitSettingId()).forEach(x -> {

			querys.add(PeregQuery.createQueryLayout(x.getCategoryCd(), command.getEmployeeCopyId(), null, command.getHireDate()));
		});

		querys.forEach(x -> {

			FindInitItemDto findInitCommand = new FindInitItemDto(command.getInitSettingId(), command.getHireDate(),
					x.getCategoryCode(), command.getEmployeeName(), command.getEmployeeCode(), command.getHireDate());
			result.addAll(this.initItemFinder.getAllInitItemByCtgCode(false, findInitCommand, isRegisFrLayoutCPS002 , false));
		});
		return result;
	}

	public List<SettingItemDto> getCopyItems(AddEmployeeCommand command) {
		return this.copyItemFinder.getValueCopyItem(command.getEmployeeCopyId(), command.getHireDate());
	}
	
}
