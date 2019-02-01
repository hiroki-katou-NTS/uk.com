package nts.uk.ctx.pereg.app.find.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.PerEmpData;
import nts.uk.ctx.pereg.app.find.common.ComboBoxRetrieveFactory;
import nts.uk.ctx.pereg.app.find.layout.dto.EmpMaintLayoutDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.ActionRole;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.CodeName;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.GridEmpBody;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.GridEmpHead;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.GridEmployeeDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.GridEmployeeInfoDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoValueDto;
import nts.uk.ctx.pereg.app.find.person.category.PerInfoCategoryFinder;
import nts.uk.ctx.pereg.app.find.person.category.PerInfoCtgFullDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefFinder;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefForLayoutDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefForLayoutFinder;
import nts.uk.ctx.pereg.app.find.person.info.item.SelectionItemDto;
import nts.uk.ctx.pereg.app.find.person.info.item.SingleItemDto;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuth;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuthRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.GridComboBoxSettingQuery;
import nts.uk.shr.pereg.app.find.PeregGridQuery;
import nts.uk.shr.pereg.app.find.PeregQuery;

@Stateless
public class GridPeregProcessor {
	@Inject
	private ManagedParallelWithContext parallel;

	@Inject
	private PeregProcessor layoutProcessor;

	@Inject
	private PerInfoItemDefRepositoty perItemRepo;

	@Inject
	private PersonInfoItemAuthRepository itemAuthRepo;

	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRepositoty;

	@Inject
	private PerInfoItemDefForLayoutFinder itemForLayoutFinder;

	@Inject
	private EmployeeDataMngInfoRepository employeeMngRepo;
	
	@Inject
	private ComboBoxRetrieveFactory cbxFactory;
	
	@Inject
	private PerInfoItemDefFinder itemFinder;
	
	@Inject 
	private PerInfoCategoryFinder ctgFinder;

	public GridEmployeeDto getGridLayout(PeregGridQuery query) {
		// app context
		LoginUserContext loginUser = AppContexts.user();
		String contractCode = loginUser.contractCode();
		String roleId = loginUser.roles().forPersonalInfo();

		GridEmployeeDto geDto = new GridEmployeeDto(query.getCategoryId(), query.getStandardDate(), Arrays.asList(),
				Arrays.asList());
		// get category
		PersonInfoCategory perInfoCtg = perInfoCtgRepositoty.getPerInfoCategory(query.getCategoryId(), contractCode)
				.get();

		// get header
		{
			// map PersonInfoItemDefinition → GridEmpHead
			List<GridEmpHead> headers = getPerItemDefForLayout(perInfoCtg, contractCode, roleId).stream()
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

			headers.addAll(headers.stream().flatMap(m -> m.getChilds().stream()).collect(Collectors.toList()));
			geDto.setHeadDatas(headers.stream()
					.map(m -> new GridEmpHead(m.getItemId(), m.getItemOrder(), m.getItemCode(), m.getItemParentCode(),
							m.getItemName(), m.getItemTypeState(), m.isRequired(), m.getResourceId(), null))
					.collect(Collectors.toList()));
		}

		// get body
		if (!CollectionUtil.isEmpty(query.getLstEmployee())) {
			List<PerEmpData> personDatas = employeeMngRepo.getEmploymentInfos(query.getLstEmployee(), query.getStandardDate());
			List<GridEmployeeInfoDto> resultsSync = Collections.synchronizedList(new ArrayList<>());

			this.parallel.forEach(personDatas, pdt -> {

				// Miss infoId (get infoId by baseDate)
				PeregQuery subq = PeregQuery.createQueryCategory(null, perInfoCtg.getCategoryCode().v(),
						pdt.getEmployeeId(), pdt.getPersonId());

				subq.setCategoryId(query.getCategoryId());
				subq.setStandardDate(query.getStandardDate());

				EmpMaintLayoutDto dto = layoutProcessor.getCategoryDetail(subq);

				List<LayoutPersonInfoValueDto> items = dto.getClassificationItems().stream()
						.flatMap(f -> f.getItems().stream()).collect(Collectors.toList());

				resultsSync.add(new GridEmployeeInfoDto(pdt.getPersonId(), pdt.getEmployeeId(),
						new CodeName(pdt.getEmployeeCode(), pdt.getEmployeeName()),
						pdt.getEmployeeBirthday(),
						new CodeName(pdt.getDepartmentCode(), pdt.getDepartmentName()),
						new CodeName(pdt.getWorkplaceCode(), pdt.getWorkplaceName()),
						new CodeName(pdt.getPositionCode(),pdt.getPositionName()),
						new CodeName(pdt.getEmploymentCode(), pdt.getEmploymentName()),
						new CodeName(pdt.getClassificationCode(), pdt.getClassificationName()),
						items.stream()
								.map(m -> new GridEmpBody(m.getItemCode(), m.getItemParentCode(), m.getActionRole(),
										m.getValue(), m.getTextValue(), m.getRecordId(), m.getLstComboBoxValue()))
								.collect(Collectors.toList())));
			});

			geDto.setBodyDatas(new ArrayList<>(resultsSync));
		}

		return geDto;
	}

	public List<PerInfoItemDefForLayoutDto> getPerItemDefForLayout(PersonInfoCategory category, String contractCode,
			String roleId) {

		// get per info item def with order
		List<PersonInfoItemDefinition> fullItemDefinitionList = perItemRepo
				.getAllItemDefByCategoryId(category.getPersonInfoCategoryId(), contractCode);

		List<PersonInfoItemDefinition> parentItemDefinitionList = fullItemDefinitionList.stream()
				.filter(item -> item.haveNotParentCode()).collect(Collectors.toList());

		List<PerInfoItemDefForLayoutDto> lstReturn = new ArrayList<>();

		Map<String, PersonInfoItemAuth> mapItemAuth = itemAuthRepo
				.getAllItemAuth(roleId, category.getPersonInfoCategoryId()).stream()
				.collect(Collectors.toMap(e -> e.getPersonItemDefId(), e -> e));

		for (int i = 0; i < parentItemDefinitionList.size(); i++) {
			PersonInfoItemDefinition itemDefinition = parentItemDefinitionList.get(i);

			// check authority
			PersonInfoItemAuth personInfoItemAuth = mapItemAuth.get(itemDefinition.getPerInfoItemDefId());

			if (personInfoItemAuth == null) {
				continue;
			}

			// convert item-definition to layoutDto
			PerInfoItemDefForLayoutDto itemDto = itemForLayoutFinder.createItemLayoutDto(category, itemDefinition, i,
					ActionRole.EDIT);

			// get and convert childrenItems
			List<PerInfoItemDefForLayoutDto> childrenItems = itemForLayoutFinder
					.getChildrenItems(fullItemDefinitionList, category, itemDefinition, i, ActionRole.EDIT);

			itemDto.setLstChildItemDef(childrenItems);

			lstReturn.add(itemDto);
		}

		return lstReturn;
	}    
	
	/**
	 * Get list option for dropdownlist in CPS003F
	 * @return
	 */
	public List<ComboBoxObject> getComboBox(GridComboBoxSettingQuery query) {

		PerInfoItemDefDto item = itemFinder.getPerInfoItemDefByIdForLayout(query.getItemId());
		PerInfoCtgFullDto ctgItem = ctgFinder.getPerInfoCtg(item.getPerInfoCtgId());

		try {
			SingleItemDto sidto = (SingleItemDto) item.getItemTypeState();
			SelectionItemDto slidto = (SelectionItemDto) sidto.getDataTypeState();

			return cbxFactory.getComboBox(slidto, null, query.getBaseDate(), false,
					EnumAdaptor.valueOf(ctgItem.getPersonEmployeeType(), PersonEmployeeType.class), true,
					ctgItem.getCategoryCode(), null, true);
		} catch (Exception ex) {
			return Arrays.asList();
		}
	}
}
