package nts.uk.ctx.pereg.app.find.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pereg.app.find.common.MappingFactory;
import nts.uk.ctx.pereg.app.find.copysetting.item.CopySettingItemFinder;
import nts.uk.ctx.pereg.app.find.copysetting.setting.EmpCopySettingFinder;
import nts.uk.ctx.pereg.app.find.initsetting.item.InitValueSetItemFinder;
import nts.uk.ctx.pereg.app.find.initsetting.item.SettingItemDto;
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

	// sonnlb end

	// sonnlb code start

	/**
	 * get Layout Dto by create type
	 * 
	 * @param command
	 *            : command from client push to webservice
	 * @return NewLayoutDto
	 */
	public NewLayoutDto getByCreateType(GetLayoutByCeateTypeDto command) {

		Optional<NewLayout> layout = repo.getLayout();
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

	private List<LayoutPersonInfoClsDto> getlistItemCls(GetLayoutByCeateTypeDto command, NewLayout _layout) {

		List<LayoutPersonInfoClsDto> listItemCls = this.clsFinder.getListClsDto(_layout.getLayoutID());

		if (command.getCreateType() != 3) {

			List<PeregQuery> queryList = loadQueryList(command.getCreateType(), command.getInitSettingId(),
					command.getBaseDate(), command.getEmployeeId());

			if (queryList.isEmpty()) {

				return null;

			}

			queryList.forEach(query -> {
				Optional<LayoutPersonInfoClsDto> clsDtoOpt = listItemCls.stream()
						.filter(itemCls -> itemCls.getPersonInfoCategoryID() == query.getCategoryId()).findFirst();

				if (clsDtoOpt.isPresent()) {

					PeregDto peregDto = this.layoutProc.findSingle(query);

					if (peregDto != null) {
						LayoutPersonInfoClsDto clsDto = clsDtoOpt.get();
						clsDto.setItems(clsDto
								.getListItemDf().stream().map(itemDf -> LayoutPersonInfoValueDto
										.fromItemDef(query.getCategoryCode(), itemDf, ActionRole.EDIT.value))
								.collect(Collectors.toList()));

						MappingFactory.mapItemClass(peregDto, clsDto);

					}
				}

			});
			if (command.getCreateType() == 2) {

				listItemCls.forEach(itemCls -> {
					if (!CollectionUtil.isEmpty(itemCls.getItems())) {
						itemCls.setItems(itemCls.getItems().stream().filter(item -> {

							LayoutPersonInfoValueDto subItem = (LayoutPersonInfoValueDto) item;

							return subItem.getValue() != null;
						}).collect(Collectors.toList()));
					}
				});

				return listItemCls.stream().filter(itemCls -> !CollectionUtil.isEmpty(itemCls.getItems()))
						.collect(Collectors.toList());
			}

		}

		return listItemCls;
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
	public List<SettingItemDto> itemListByCreateType(int createType, String initSettingId, GeneralDate baseDate,
			String employeeCopyId) {

		List<SettingItemDto> result = new ArrayList<SettingItemDto>();
		List<PeregQuery> listQuery = new ArrayList<PeregQuery>();
		// Copy Type
		if (createType == 1) {

			this.copySettingFinder.getEmpCopySetting().forEach(x -> {
				listQuery.add(new PeregQuery(x.getCategoryCd(), employeeCopyId, null, baseDate));
			});

			listQuery.forEach(x -> {
				result.addAll(
						this.copyItemFinder.getAllCopyItemByCtgCode(x.getCategoryCode(), employeeCopyId, baseDate));
			});

		} else {
			// Init Value Type

			this.initCtgSettingFinder.getAllCategoryBySetId(initSettingId).forEach(x -> {

				listQuery.add(new PeregQuery(x.getCategoryCd(), employeeCopyId, null, baseDate));
			});

			listQuery.forEach(x -> {
				result.addAll(
						this.initItemFinder.getAllInitItemByCtgCode(initSettingId, x.getCategoryCode(), baseDate));
			});

		}
		return result;
	}
	// sonnlb code end

}
