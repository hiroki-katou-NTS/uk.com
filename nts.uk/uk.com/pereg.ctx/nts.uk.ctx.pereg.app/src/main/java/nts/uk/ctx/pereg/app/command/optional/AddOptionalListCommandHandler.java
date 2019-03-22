package nts.uk.ctx.pereg.app.command.optional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pereg.dom.person.additemdata.category.EmInfoCtgDataRepository;
import nts.uk.ctx.pereg.dom.person.additemdata.category.EmpInfoCtgData;
import nts.uk.ctx.pereg.dom.person.additemdata.item.EmpInfoItemData;
import nts.uk.ctx.pereg.dom.person.additemdata.item.EmpInfoItemDataRepository;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.categor.PerInfoCtgData;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.categor.PerInfoCtgDataRepository;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.DataState;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.PerInfoItemDataRepository;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.PersonInfoItemData;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefAddCommand;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefAddListCommandHandler;

@Stateless
public class AddOptionalListCommandHandler extends CommandHandler<List<PeregUserDefAddCommand>>
		implements PeregUserDefAddListCommandHandler {
	@Inject
	private PerInfoCategoryRepositoty perInfoCategoryRepositoty;

	@Inject
	private PerInfoCtgDataRepository perInfoCtgDataRepository;

	@Inject
	private PerInfoItemDataRepository perInfoItemDataRepository;

	@Inject
	private EmInfoCtgDataRepository emInfoCtgDataRepository;

	@Inject
	private EmpInfoItemDataRepository empInfoItemDataRepository;

	@Override
	protected void handle(CommandHandlerContext<List<PeregUserDefAddCommand>> context) {
		val command = context.getCommand();
		List<PeregUserDefAddCommand> errorLst = new ArrayList<>();
		List<PeregUserDefAddCommand> addLst = new ArrayList<>();
		command.parallelStream().forEach(c -> {
			if (c.getItems() == null || c.getItems().isEmpty()) {
				errorLst.add(c);
			} else {
				addLst.add(c);
			}
		});

		String cid = AppContexts.user().companyId();

		// Do tất cả nhân viên đều có categoryCd giống nhau nên mình sẽ lấy categoryCd của nhân viên đầu tiên trong list
		Optional<PersonInfoCategory> ctg = perInfoCategoryRepositoty
				.getPerInfoCategoryByCtgCD(command.get(0).getCategoryCd(), cid);

		if (!ctg.isPresent()) {
			throw new RuntimeException("invalid PersonInfoCategory");
		}

		// In case of person
		if (ctg.get().getPersonEmployeeType() == PersonEmployeeType.PERSON) {
			insertPerson(ctg.get(), addLst);

		} else if (ctg.get().getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE){
			insertEmployee(ctg.get(), addLst);
		}

	}
	
	private void insertPerson(PersonInfoCategory ctg, List<PeregUserDefAddCommand> addLst) {
		Map<String, String> recordIds = addLst.parallelStream().collect(
				Collectors.toMap(PeregUserDefAddCommand::getPersonId, PeregUserDefAddCommand::getRecordId));

		// In case of optional category
		recordIds.entrySet().parallelStream().forEach(c -> {
			if (StringUtils.isEmpty(c.getValue()) || c.getValue() == null) {
				c.setValue(IdentifierUtil.randomUniqueId());
			}
		});

		// Insert category data
		List<PerInfoCtgData> ctgData = recordIds.entrySet().parallelStream().map(c -> {
			return new PerInfoCtgData(c.getValue(), ctg.getPersonInfoCategoryId(), c.getKey());
		}).collect(Collectors.toList());
		
		if(!ctg.isFixed() && ctgData.size() > 0) {
			perInfoCtgDataRepository.addAll(ctgData);				
		}
		
		List<PersonInfoItemData> items = new ArrayList<>();
		
		addLst.parallelStream().forEach(c -> {
			String recordId = recordIds.get(c.getPersonId());
			c.getItems().parallelStream().forEach(item -> {
				// Insert item data
				DataState state = null;
				state = OptionalUtil.createDataState(item);
				if (state != null) {
					PersonInfoItemData itemData = new PersonInfoItemData(item.definitionId(), recordId, state);
					items.add(itemData);
				}

			});
		});
		
		if(items.size() > 0) {
			perInfoItemDataRepository.addAll(items);
		}
		
	}
	
	private void insertEmployee(PersonInfoCategory ctg, List<PeregUserDefAddCommand> addLst) {
		Map<String, String> recordIds = addLst.parallelStream().collect(
				Collectors.toMap(PeregUserDefAddCommand::getEmployeeId, PeregUserDefAddCommand::getRecordId));
		
		// In case of optional category
		recordIds.entrySet().parallelStream().forEach(c -> {
			if (StringUtils.isEmpty(c.getValue()) || c.getValue() == null) {
				c.setValue(IdentifierUtil.randomUniqueId());
			}
		});
		
		// Insert category data
		List<EmpInfoCtgData> ctgData = recordIds.entrySet().parallelStream().map(c -> {
			return new EmpInfoCtgData(c.getValue(), ctg.getPersonInfoCategoryId(), c.getKey());
		}).collect(Collectors.toList());
		
		// Add emp category data
		if(!ctg.isFixed() && ctgData.size() > 0) {
			emInfoCtgDataRepository.addAll(ctgData);
		}
		
		// Add item data
		List<EmpInfoItemData> items = new ArrayList<>();
		
		addLst.parallelStream().forEach(c -> {
			String recordId = recordIds.get(c.getPersonId());
			c.getItems().parallelStream().forEach(item -> {
				// Insert item data
				DataState state = null;
				state = OptionalUtil.createDataState(item);
				if (state != null) {
					EmpInfoItemData itemData = new EmpInfoItemData(item.definitionId(), recordId, state);
					items.add(itemData);
				}

			});
		});
		
		if(items.size() > 0) {
			empInfoItemDataRepository.addAll(items);
		}
		
	}

}
