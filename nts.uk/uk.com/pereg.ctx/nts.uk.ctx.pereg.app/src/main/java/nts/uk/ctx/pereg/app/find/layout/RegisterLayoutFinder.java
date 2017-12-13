package nts.uk.ctx.pereg.app.find.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pereg.app.command.addemployee.AddEmployeeCommand;
import nts.uk.ctx.pereg.app.find.additionaldata.item.EmpInfoItemDataFinder;
import nts.uk.ctx.pereg.app.find.common.MappingFactory;
import nts.uk.ctx.pereg.app.find.copysetting.item.CopySettingItemFinder;
import nts.uk.ctx.pereg.app.find.copysetting.setting.EmpCopySettingFinder;
import nts.uk.ctx.pereg.app.find.initsetting.item.InitValueSetItemFinder;
import nts.uk.ctx.pereg.app.find.initsetting.item.SettingItemDto;
import nts.uk.ctx.pereg.app.find.initsetting.item.findInitItemDto;
import nts.uk.ctx.pereg.app.find.layoutdef.NewLayoutDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.ActionRole;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsFinder;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoValueDto;
import nts.uk.ctx.pereg.app.find.person.category.PerInfoCategoryFinder;
import nts.uk.ctx.pereg.app.find.person.category.PerInfoCtgFullDto;
import nts.uk.ctx.pereg.app.find.person.setting.init.category.PerInfoInitValueSettingCtgFinder;
import nts.uk.ctx.pereg.app.find.processor.LayoutingProcessor;
import nts.uk.ctx.pereg.dom.person.layout.INewLayoutReposotory;
import nts.uk.ctx.pereg.dom.person.layout.NewLayout;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.PeregDto;

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
	private EmpCopySettingFinder copySettingFinder;

	@Inject
	private LayoutingProcessor layoutProc;

	@Inject

	private CopySettingItemFinder copyItemFinder;

	@Inject

	private InitValueSetItemFinder initItemFinder;

	@Inject
	private PerInfoCategoryFinder infoCtgFinder;

	@Inject
	private EmpInfoItemDataFinder infoItemDataFinder;

	// sonnlb end

	// sonnlb code start

	/**
	 * get Layout Dto by create type
	 * 
	 * @param command
	 *            : command from client push to webservice
	 * @return NewLayoutDto
	 */
	public NewLayoutDto getByCreateType(GetLayoutByCreateTypeDto command) {

		Optional<NewLayout> layout = repo.getLayout(false);
		if (!layout.isPresent()) {

			return null;
		}

		NewLayout _layout = layout.get();

		List<LayoutPersonInfoClsDto> listItemCls = getlistItemCls(command, _layout);

		listItemCls.forEach(x -> {
			PerInfoCtgFullDto ctgInfo = this.infoCtgFinder.getPerInfoCtg(x.getPersonInfoCategoryID());
			if (ctgInfo != null) {
				x.setItems(x
						.getListItemDf().stream().map(itemDf -> LayoutPersonInfoValueDto
								.fromItemDef(ctgInfo.getCategoryCode(), itemDf, ActionRole.EDIT.value))
						.collect(Collectors.toList()));
			}
		});

		return NewLayoutDto.fromDomain(_layout, listItemCls);

	}

	private List<LayoutPersonInfoClsDto> getlistItemCls(GetLayoutByCreateTypeDto command, NewLayout _layout) {

		List<LayoutPersonInfoClsDto> listItemCls = this.clsFinder.getListClsDto(_layout.getLayoutID());

		if (command.getCreateType() != 3) {

			List<PeregQuery> queryList = loadQueryList(command.getCreateType(), command.getInitSettingId(),
					command.getBaseDate(), command.getEmployeeId());

			if (queryList.isEmpty()) {

				return null;

			}

			setDataSystem(queryList, listItemCls);

			setDataOptinal(queryList, listItemCls);

			if (command.getCreateType() == 2) {

				removeNotDefItem(listItemCls);

				return listItemCls.stream().filter(itemCls -> !CollectionUtil.isEmpty(itemCls.getItems()))
						.collect(Collectors.toList());
			}

		}

		return listItemCls;
	}

	private void removeNotDefItem(List<LayoutPersonInfoClsDto> listItemCls) {
		listItemCls.forEach(itemCls -> {
			if (!CollectionUtil.isEmpty(itemCls.getItems())) {
				itemCls.setItems(itemCls.getItems().stream().filter(item -> {

					LayoutPersonInfoValueDto subItem = (LayoutPersonInfoValueDto) item;

					return subItem.getValue() != null;
				}).collect(Collectors.toList()));
			}
		});

	}

	private void setDataOptinal(List<PeregQuery> queryList, List<LayoutPersonInfoClsDto> listItemCls) {
		List<PeregQuery> queryOptinalList = queryList.stream().filter(x -> x.getCategoryCode().charAt(1) == 'O')
				.collect(Collectors.toList());

		// set data for optinalCategory
		queryOptinalList.forEach(query -> {

			Optional<LayoutPersonInfoClsDto> clsDtoOpt = listItemCls.stream()
					.filter(itemCls -> itemCls.getPersonInfoCategoryID() == query.getCategoryId()).findFirst();
			if (clsDtoOpt.isPresent()) {

				List<SettingItemDto> dataList = this.infoItemDataFinder.loadInfoItemDataList(query.getCategoryCode(),
						AppContexts.user().companyId(), AppContexts.user().employeeId());

				if (!CollectionUtil.isEmpty(dataList) && !CollectionUtil.isEmpty(clsDtoOpt.get().getListItemDf())) {
					LayoutPersonInfoClsDto clsDto = clsDtoOpt.get();
					clsDto.setItems(clsDto
							.getListItemDf().stream().map(itemDf -> LayoutPersonInfoValueDto
									.fromItemDef(query.getCategoryCode(), itemDf, ActionRole.EDIT.value))
							.collect(Collectors.toList()));
					clsDto.getItems().forEach(item -> {
						LayoutPersonInfoValueDto itemData = (LayoutPersonInfoValueDto) item;
						itemData.setValue(getValueByItemCode(dataList, itemData.getItemCode()));
					});
				}

			}
		});

	}

	private Object getValueByItemCode(List<SettingItemDto> dataList, String itemCode) {

		Object returnData = null;
		Optional<SettingItemDto> itemDtoOpt = dataList.stream().filter(x -> x.getItemCode().equals(itemCode))
				.findFirst();

		if (itemDtoOpt.isPresent()) {
			SettingItemDto itemDto = itemDtoOpt.get();
			returnData = itemDto.getValueAsString();
		}
		return returnData;
	}

	private void setDataSystem(List<PeregQuery> queryList, List<LayoutPersonInfoClsDto> listItemCls) {

		List<PeregQuery> querySysTemList = queryList.stream().filter(x -> x.getCategoryCode().charAt(1) == 'S')
				.collect(Collectors.toList());
		// set data for systemCategory
		querySysTemList.forEach(query -> {
			Optional<LayoutPersonInfoClsDto> clsDtoOpt = listItemCls.stream()
					.filter(itemCls -> itemCls.getPersonInfoCategoryID() == query.getCategoryId()).findFirst();

			if (clsDtoOpt.isPresent()) {

				PeregDto peregDto = this.layoutProc.findSingle(query);

				if (peregDto != null && !CollectionUtil.isEmpty(clsDtoOpt.get().getListItemDf())) {
					LayoutPersonInfoClsDto clsDto = clsDtoOpt.get();

					clsDto.setItems(clsDto
							.getListItemDf().stream().map(itemDf -> LayoutPersonInfoValueDto
									.fromItemDef(query.getCategoryCode(), itemDf, ActionRole.EDIT.value))
							.collect(Collectors.toList()));

					MappingFactory.mapItemClass(peregDto, clsDto);

				}
			}

		});

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
	public List<PeregQuery> loadQueryList(int createType, String initSettingId, GeneralDate baseDate,
			String employeeCopyId) {

		List<PeregQuery> listQuery = new ArrayList<PeregQuery>();
		// Copy Type
		if (createType == 1) {

			this.copySettingFinder.getEmpCopySetting().forEach(x -> {
				listQuery.add(new PeregQuery(x.getCategoryCd(), employeeCopyId, null, baseDate));
			});

		} else {
			// Init Value Type

			this.initCtgSettingFinder.getAllCategoryBySetId(initSettingId).forEach(x -> {

				listQuery.add(new PeregQuery(x.getCategoryCd(), AppContexts.user().employeeId(), null, baseDate));
			});

		}
		return listQuery;
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
	public List<SettingItemDto> itemListByCreateType(AddEmployeeCommand command) {

		List<SettingItemDto> result = new ArrayList<SettingItemDto>();
		List<PeregQuery> listQuery = new ArrayList<PeregQuery>();
		// Copy Type
		if (command.getCreateType() == 1) {

			this.copySettingFinder.getEmpCopySetting().forEach(x -> {
				listQuery.add(
						new PeregQuery(x.getCategoryCd(), command.getEmployeeCopyId(), null, command.getHireDate()));
			});

			listQuery.forEach(x -> {
				result.addAll(this.copyItemFinder.getAllCopyItemByCtgCode(x.getCategoryCode(),
						command.getEmployeeCopyId(), command.getHireDate()));
			});

		} else {
			// Init Value Type

			this.initCtgSettingFinder.getAllCategoryBySetId(command.getInitSettingId()).forEach(x -> {

				listQuery.add(
						new PeregQuery(x.getCategoryCd(), command.getEmployeeCopyId(), null, command.getHireDate()));
			});

			listQuery.forEach(x -> {

				findInitItemDto findInitCommand = new findInitItemDto(command.getInitSettingId(), command.getHireDate(),
						x.getCategoryCode(), command.getEmployeeName(), command.getEmployeeCode(),
						command.getHireDate());
				result.addAll(this.initItemFinder.getAllInitItemByCtgCode(findInitCommand));
			});

		}
		return result;
	}
	// sonnlb code end

}
