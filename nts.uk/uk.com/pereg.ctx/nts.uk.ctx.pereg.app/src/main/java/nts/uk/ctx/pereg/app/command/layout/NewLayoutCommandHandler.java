package nts.uk.ctx.pereg.app.command.layout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.error.I18NErrorMessage;
import nts.arc.error.RawErrorMessage;
import nts.arc.i18n.I18NText;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pereg.app.command.common.FacadeUtils;
import nts.uk.ctx.pereg.app.find.person.category.PerInfoCtgFinder;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefFinder;
import nts.uk.ctx.pereg.dom.person.info.item.ItemType;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.person.layout.INewLayoutReposotory;
import nts.uk.ctx.pereg.dom.person.layout.LayoutCode;
import nts.uk.ctx.pereg.dom.person.layout.LayoutName;
import nts.uk.ctx.pereg.dom.person.layout.NewLayout;
import nts.uk.ctx.pereg.dom.person.layout.classification.ILayoutPersonInfoClsRepository;
import nts.uk.ctx.pereg.dom.person.layout.classification.LayoutPersonInfoClassification;
import nts.uk.ctx.pereg.dom.person.layout.classification.definition.ILayoutPersonInfoClsDefRepository;
import nts.uk.ctx.pereg.dom.person.layout.classification.definition.LayoutPersonInfoClsDefinition;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class NewLayoutCommandHandler extends CommandHandlerWithResult<NewLayoutCommand, List<String>> {

	@Inject
	INewLayoutReposotory layoutRepo;

	@Inject
	ILayoutPersonInfoClsRepository classfRepo;

	@Inject
	ILayoutPersonInfoClsDefRepository clsDefRepo;

	@Inject
	PerInfoItemDefFinder itemDefFinder;

	@Inject
	PerInfoCtgFinder itemCtgFinder;
	
	@Inject 
	private FacadeUtils facadeUtils;
	
	@Inject
	private PerInfoItemDefRepositoty perInfoItemDefRepositoty;
	
	private static final String PERSONNAME_CODE = "IS00003";
	
	private static final String HIREDATE_CODE = "IS00020";
	
	private static final String EMPLOYEE_CODE = "IS00001";

	@Override
	protected List<String> handle(CommandHandlerContext<NewLayoutCommand> context) {
		List<String> result = new ArrayList<>();
		// get new layout domain and command
		NewLayoutCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		NewLayout update = layoutRepo.getLayout().orElse(new NewLayout(companyId, IdentifierUtil.randomUniqueId(),
				new LayoutCode("001"), new LayoutName("レイアウト")));

		// validate all item classification before save
		validateArg(command);

		// Validate
		result = validateRequiredItem(command);
		
		// update layout
		layoutRepo.save(update);

		// remove all classification in this layout
		classfRepo.removeAllByLayoutId(update.getLayoutID());

		// remove all itemdefinition relation with classification in this layout
		clsDefRepo.removeAllByLayoutId(update.getLayoutID());

		// push all classification and item definition to db
		List<ClassificationCommand> classCommands = command.getItemsClassification();
		if (!classCommands.isEmpty()) {
			// add all classification on client to db
			classfRepo.addClassifications(classCommands.stream()
					.map(m -> toClassificationDomain(m, update.getLayoutID())).collect(Collectors.toList()));

			// add all item definition relation with classification to db
			for (ClassificationCommand classCommand : classCommands) {
				List<ClassificationItemDfCommand> clsIDfs = classCommand.getListItemClsDf();
				if (!clsIDfs.isEmpty()) {
					clsDefRepo.addClassificationItemDefines(clsIDfs.stream()
							.map(m -> toClassItemDefDomain(m, update.getLayoutID(), classCommand.getDispOrder()))
							.collect(Collectors.toList()));
				}
			}
		}
		return result;
	}

	private LayoutPersonInfoClassification toClassificationDomain(ClassificationCommand command, String layoutId) {
		return LayoutPersonInfoClassification.createFromJaveType(layoutId, command.getDispOrder(),
				command.getPersonInfoCategoryID(), command.getLayoutItemType());
	}

	private LayoutPersonInfoClsDefinition toClassItemDefDomain(ClassificationItemDfCommand command, String layoutId,
			int classDispOrder) {
		return LayoutPersonInfoClsDefinition.createFromJavaType(layoutId, classDispOrder, command.getDispOrder(),
				command.getPersonInfoItemDefinitionID());
	}

	private void validateArg(NewLayoutCommand command) {
		// validate all usecase [Registration] at here
		// throw exception if not valid
		List<String> requiredIds = itemDefFinder.getRequiredIds();
		List<String> allSaveItemIds = command.getItemsClassification().stream().map(m -> m.listItemClsDf)
				.flatMap(List::stream).map(m -> m.getPersonInfoItemDefinitionID()).sorted()
				.collect(Collectors.toList());

		// エラーメッセージ（#Msg_201,システム必須項目のうち配置されていない項目（カンマ区切りの文字列））を表示する
		if (!allSaveItemIds.containsAll(requiredIds)) {
			requiredIds = requiredIds.stream().filter(m -> allSaveItemIds.indexOf(m) == -1)
					.collect(Collectors.toList());
			List<PerInfoItemDefDto> dto = itemDefFinder.getPerInfoItemDefByListId(requiredIds);
			if (!dto.isEmpty()) {
				String alert = String.join(", ", dto.stream().map(m -> m.getItemName()).collect(Collectors.toList()));

				throw new BusinessException(new I18NErrorMessage(I18NText.main("Msg_201").addRaw(alert).build()));
			}

			throw new BusinessException(new I18NErrorMessage(I18NText.main("Msg_201").build()));
		}

		// エラーメッセージ（#Msg_289#,２つ以上配置されている項目名）を表示する
		for (int i = 0; i < allSaveItemIds.size() - 2; i++) {
			if (allSaveItemIds.get(i).equals(allSaveItemIds.get(i + 1))) {
				throw new BusinessException(new RawErrorMessage("Msg_289"));
			}
		}
	}
	
	/**
	 * Validate all required item
	 * @param command
	 * @return
	 */
	private List<String> validateRequiredItem(NewLayoutCommand command) {

		List<String> result = new ArrayList<>();

		// Get all item requried
		Map<String, List<PersonInfoItemDefinition>> itemByCtgId = perInfoItemDefRepositoty
				.getByListCategoryIdWithoutAbolition(command.getItemsClassification().stream()
						.map(ClassificationCommand::getPersonInfoCategoryID).distinct().collect(Collectors.toList()),
						AppContexts.user().contractCode());

		if (itemByCtgId.size() == 0) {
			return null;
		}
		// Map to list item id 's on screen to easy use
		List<String> listItemId = command.getItemsClassification().stream().map(ClassificationCommand::getListItemClsDf)
				.flatMap(Collection::stream).collect(Collectors.toList()).stream()
				.map(ClassificationItemDfCommand::getPersonInfoItemDefinitionID).collect(Collectors.toList());
		
		List<String> listDefault = facadeUtils
				.getListDefaultItem(itemByCtgId.entrySet().stream().map(i -> i.getKey()).collect(Collectors.toList()));
		
		itemByCtgId.forEach((k, value) -> {
			value.stream().forEach(item -> {
				if (item.getItemTypeState().getItemType() != ItemType.SINGLE_ITEM
						|| PERSONNAME_CODE.equals(item.getItemCode().v())
						|| HIREDATE_CODE.equals(item.getItemCode().v())
						|| EMPLOYEE_CODE.equals(item.getItemCode().v())) {
					return;
				}
				
				if (listItemId.contains(item.getPerInfoItemDefId()) || listDefault.contains(item.getItemCode().v())) {
					return;
				}
				result.add(item.getItemName().v());
			});
		});

		return result;
	}
	
}
