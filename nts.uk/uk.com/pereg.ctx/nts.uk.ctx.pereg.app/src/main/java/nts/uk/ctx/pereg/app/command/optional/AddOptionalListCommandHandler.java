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
import nts.gul.text.StringUtil;
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
		String cid = AppContexts.user().companyId();
		List<String> itemIds = new ArrayList<>();
		List<String> recordIds = new ArrayList<>();
		List<PeregUserDefAddCommand> errorLst = new ArrayList<>();
		List<PeregUserDefAddCommand> addLst = new ArrayList<>();		
		
		command.stream().forEach(c ->{
			c.getItems().forEach(item  ->{
				itemIds.add(item.definitionId());
			});
		});
		
		// do itemId của các employee trong cung một công ty giống nhau  - ta sẽ lấy nhân viên đầu tiên để lấy ra được itemId
		command.stream().forEach(c -> {
			if (c.getItems() == null || c.getItems().isEmpty()) {
				errorLst.add(c);
			} else {
				if (StringUtil.isNullOrEmpty(c.getRecordId(), true)) {
					recordIds.add(c.getRecordId());
				}
				addLst.add(c);
			}
		});
		

		// Do tất cả nhân viên đều có categoryCd giống nhau nên mình sẽ lấy categoryCd của nhân viên đầu tiên trong list
		Optional<PersonInfoCategory> ctg = perInfoCategoryRepositoty
				.getPerInfoCategoryByCtgCD(command.get(0).getCategoryCd(), cid);

		if (!ctg.isPresent()) {
			throw new RuntimeException("invalid PersonInfoCategory");
		}
		
		// In case of person
		if (ctg.get().getPersonEmployeeType() == PersonEmployeeType.PERSON) {
			List<PersonInfoItemData> itemUpdate = perInfoItemDataRepository.getAllInfoItemByRecordIdsAndItemIds(itemIds.stream().distinct().collect(Collectors.toList()), recordIds);
			insertPerson(ctg.get(), addLst, itemUpdate);
		} else if (ctg.get().getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE){
			List<EmpInfoItemData> itemUpdate = empInfoItemDataRepository.getAllInfoItemByRecordId(itemIds.stream().distinct().collect(Collectors.toList()), recordIds);
			insertEmployee(ctg.get(), addLst, itemUpdate);
		}

	}
	
	private void insertPerson(PersonInfoCategory ctg, List<PeregUserDefAddCommand> addLst, List<PersonInfoItemData> itemUpdates) {
		
		List<PersonInfoItemData> insertLst = new ArrayList<>();
		List<PersonInfoItemData> updateLst = new ArrayList<>();
		
		addLst.stream().forEach(c -> {
			c.getItems().stream().forEach(item -> {
				Optional<PersonInfoItemData> itemUpdateOpt = itemUpdates.stream().filter(update -> update.getPerInfoItemDefId().equals(item.definitionId()) && update.getRecordId().equals(c.getRecordId())).findFirst();
				// Insert item data
				DataState state = null;
				state = OptionalUtil.createDataState(item);
				if (state != null) {
					PersonInfoItemData itemData = new PersonInfoItemData(item.definitionId(), c.getRecordId(), state);
					if(itemUpdateOpt.isPresent()) {
						updateLst.add(itemData);
					}else {
						Optional<PersonInfoItemData> insertOpt = insertLst.stream()
								.filter(insert -> insert.getPerInfoItemDefId().equals(item.definitionId())
										&& insert.getRecordId().equals(c.getRecordId()))
								.findFirst();
						if(!insertOpt.isPresent()) {
							insertLst.add(itemData);
						}
					}
				}
			});
		});
		
		if(insertLst.size() > 0) {
			Map<String, String> recordIds = addLst.stream().collect(
					Collectors.toMap(PeregUserDefAddCommand::getPersonId, PeregUserDefAddCommand::getRecordId));

			// In case of optional category
			recordIds.entrySet().stream().forEach(c -> {
				if (StringUtils.isEmpty(c.getValue()) || c.getValue() == null) {
					c.setValue(IdentifierUtil.randomUniqueId());
				}
			});

			// Insert category data
			List<PerInfoCtgData> ctgData = recordIds.entrySet().stream().map(c -> {
				return new PerInfoCtgData(c.getValue(), ctg.getPersonInfoCategoryId(), c.getKey());
			}).collect(Collectors.toList());
			
			if(!ctg.isFixed() && ctgData.size() > 0) {
				perInfoCtgDataRepository.addAll(ctgData);				
			}
			
			
			perInfoItemDataRepository.addAll(insertLst);
		}
		
		
		if(updateLst.size() > 0) {
			perInfoItemDataRepository.updateAll(updateLst);
		}
		
	}
	
	private void insertEmployee(PersonInfoCategory ctg, List<PeregUserDefAddCommand> addLst, List<EmpInfoItemData> itemUpdates) {
		// Add item data
		List<EmpInfoItemData> itemUpdateLst = new ArrayList<>();
		List<EmpInfoItemData> itemInsertLst = new ArrayList<>();
		
		addLst.stream().forEach(c -> {
			c.getItems().stream().forEach(item -> {
				Optional<EmpInfoItemData> itemUpdateOpt = itemUpdates.stream().filter(update -> update.getPerInfoDefId().equals(item.definitionId()) && update.getRecordId().equals(c.getRecordId())).findFirst();
				// Insert item data
				DataState state = null;
				state = OptionalUtil.createDataState(item);
				if (state != null) {
					EmpInfoItemData itemData = new EmpInfoItemData(item.definitionId(), c.getRecordId(), state);
					if(itemUpdateOpt.isPresent()) {
						itemUpdateLst.add(itemData);
					}else {
						Optional<EmpInfoItemData> insertOpt = itemInsertLst.stream()
								.filter(insert -> insert.getPerInfoDefId().equals(item.definitionId())
										&& insert.getRecordId().equals(c.getRecordId()))
								.findFirst();
						if(!insertOpt.isPresent()) {
							itemInsertLst.add(itemData);
						}
					}
				}
			});
		});
		
		if(itemInsertLst.size() > 0) {
			Map<String, String> recordIds = addLst.stream().collect(
					Collectors.toMap(PeregUserDefAddCommand::getEmployeeId, PeregUserDefAddCommand::getRecordId));
			
			// In case of optional category
			recordIds.entrySet().stream().forEach(c -> {
				if (StringUtils.isEmpty(c.getValue()) || c.getValue() == null) {
					c.setValue(IdentifierUtil.randomUniqueId());
				}
			});
			
			// Insert category data
			List<EmpInfoCtgData> ctgData = recordIds.entrySet().stream().map(c -> {
				return new EmpInfoCtgData(c.getValue(), ctg.getPersonInfoCategoryId(), c.getKey());
			}).collect(Collectors.toList());
			
			// Add emp category data
			if(!ctg.isFixed() && ctgData.size() > 0) {
				emInfoCtgDataRepository.addAll(ctgData);
			}
			
			empInfoItemDataRepository.addAll(itemInsertLst);
		}
		
		if(itemUpdateLst.size() > 0) {
			empInfoItemDataRepository.updateAll(itemUpdateLst);
		}
	}
}
