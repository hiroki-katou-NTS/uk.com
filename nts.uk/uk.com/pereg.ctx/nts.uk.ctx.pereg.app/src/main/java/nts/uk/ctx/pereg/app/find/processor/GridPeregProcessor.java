package nts.uk.ctx.pereg.app.find.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pereg.app.find.layout.dto.EmpMaintLayoutDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.ActionRole;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.GridEmployeeDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoValueDto;
import nts.uk.ctx.pereg.app.find.person.category.PerInfoCategoryFinder;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefForLayoutDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefForLayoutFinder;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoAuthType;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuth;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuthRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
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
	private PerInfoCategoryFinder perInfoCategoryFinder;

	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRepositoty;

	@Inject
	private PerInfoItemDefForLayoutFinder itemForLayoutFinder;

	public GridEmployeeDto getGridLaoyout(PeregGridQuery query) {
		// app context
		LoginUserContext loginUser = AppContexts.user();
		String contractCode = loginUser.contractCode();
		String loginEmpId = loginUser.employeeId();
		String roleId = loginUser.roles().forPersonalInfo();

		GridEmployeeDto geDto = new GridEmployeeDto(query.getCategoryId(), query.getStandardDate(), Arrays.asList(),
				Arrays.asList());
		
		// get header
		{
			// get category
			PersonInfoCategory perInfoCtg = perInfoCtgRepositoty.getPerInfoCategory(query.getCategoryId(), contractCode).get();
	
			// map PersonInfoItemDefinition →→ PerInfoItemDefForLayoutDto
			List<PerInfoItemDefForLayoutDto> lstPerInfoItemDefForLayout = getPerItemDefForLayout(perInfoCtg, contractCode, roleId);
			
			
			geDto.setHeadDatas(lstPerInfoItemDefForLayout);
			
		}

		// get body
		if (!CollectionUtil.isEmpty(query.getLstEmployee())) {
			List<EmpMaintLayoutDto> resultsSync = Collections.synchronizedList(new ArrayList<>());

			this.parallel.forEach(query.getLstEmployee(), empl -> {
				// Miss infoId (get infoId by baseDate)
				EmpMaintLayoutDto dto = layoutProcessor.getCategoryDetail(PeregQuery.createQueryCategory("",
						query.getCategoryCode(), empl.getEmployeeId(), empl.getPersonId()));

				resultsSync.add(dto);

				List<LayoutPersonInfoValueDto> items = dto.getClassificationItems().stream()
						.flatMap(f -> f.getItems().stream()).collect(Collectors.toList());

				// geDto.setHeadDatas(headDatas);
			});
		}

		return geDto;
	}

	private List<PerInfoItemDefForLayoutDto> getPerItemDefForLayout(PersonInfoCategory category, String contractCode,
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
			PerInfoItemDefForLayoutDto itemDto = itemForLayoutFinder.createItemLayoutDto(category, itemDefinition, i, ActionRole.EDIT);

			// get and convert childrenItems
			List<PerInfoItemDefForLayoutDto> childrenItems = itemForLayoutFinder.getChildrenItems(fullItemDefinitionList, category, itemDefinition, i, ActionRole.EDIT);

			itemDto.setLstChildItemDef(childrenItems);

			lstReturn.add(itemDto);
		}

		return lstReturn;
	}
}
