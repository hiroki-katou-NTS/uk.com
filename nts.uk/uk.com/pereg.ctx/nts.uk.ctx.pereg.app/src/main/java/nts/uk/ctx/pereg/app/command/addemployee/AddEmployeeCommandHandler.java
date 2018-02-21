package nts.uk.ctx.pereg.app.command.addemployee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.app.command.shortworktime.AddShortWorkTimeCommand;
import nts.uk.ctx.at.shared.app.command.workingcondition.AddWorkingConditionCommand;
import nts.uk.ctx.at.shared.app.command.workingcondition.AddWorkingConditionCommandAssembler;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinitionSimple;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.ItemValue;
import nts.uk.shr.pereg.app.command.ItemsByCategory;

/**
 * @author sonnlb
 *
 */
@Stateless
public class AddEmployeeCommandHandler extends CommandHandlerWithResult<AddEmployeeCommand, String> {

	@Inject
	private AddEmployeeCommandHelper helper;

	@Inject
	private AddEmployeeCommandFacade process;

	@Inject
	private AddWorkingConditionCommandAssembler wkCodAs;

	@Inject
	private PerInfoItemDefRepositoty perInfoItemRepo;

	@Override
	protected String handle(CommandHandlerContext<AddEmployeeCommand> context) {

		val command = context.getCommand();

		String employeeId = IdentifierUtil.randomUniqueId();

		String userId = IdentifierUtil.randomUniqueId();

		String personId = IdentifierUtil.randomUniqueId();

		String companyId = AppContexts.user().companyId();

		String comHistId = IdentifierUtil.randomUniqueId();

		List<ItemsByCategory> inputs = process.createData(command, personId, employeeId, comHistId);

		validateTime(inputs, employeeId, personId);

		checkRequiredInputs(inputs, employeeId, personId, companyId);

		helper.addBasicData(command, personId, employeeId, comHistId, companyId, userId);

		process.addNewFromInputs(command, personId, employeeId, comHistId, inputs);

		return employeeId;

	}

	private void checkRequiredInputs(List<ItemsByCategory> inputs, String employeeId, String personId,
			String companyId) {

		List<String> ctgCodes = inputs.stream().map(x -> x.getCategoryCd()).collect(Collectors.toList());

		List<ItemValue> items = new ArrayList<ItemValue>();

		inputs.forEach(ctg -> {

			items.addAll(ctg.getItems());

		});

		List<PersonInfoItemDefinitionSimple> requiredItems = perInfoItemRepo
				.getRequiredItemFromCtgCdLst(AppContexts.user().contractCode(), companyId, ctgCodes);

		List<String> nodataItems = new ArrayList<String>();
		requiredItems.forEach(item -> {
			if (!items.stream().filter(x -> x.itemCode().equals(item.getItemCode().v())).findFirst().isPresent()) {
				nodataItems.add(item.getItemName().v());
			}
		});

		if (!CollectionUtil.isEmpty(nodataItems)) {
			throw new BusinessException("Msg_925", String.join(",", nodataItems));

		}

	}

	private void validateTime(List<ItemsByCategory> inputs, String employeeId, String personId) {
		Optional<ItemsByCategory> shortWkOpt = inputs.stream().filter(ctg -> ctg.getCategoryCd().equals("CS00019"))
				.findFirst();
		if (shortWkOpt.isPresent()) {
			AddShortWorkTimeCommand shortWk = (AddShortWorkTimeCommand) shortWkOpt.get()
					.createCommandForSystemDomain(personId, employeeId, AddShortWorkTimeCommand.class);
			shortWk.getLstTimeSlot();
		}

		Optional<ItemsByCategory> wkCodOpt = inputs.stream().filter(ctg -> ctg.getCategoryCd().equals("CS00020"))
				.findFirst();
		if (wkCodOpt.isPresent()) {
			AddWorkingConditionCommand wkCod = (AddWorkingConditionCommand) wkCodOpt.get()
					.createCommandForSystemDomain(personId, employeeId, AddWorkingConditionCommand.class);
			wkCodAs.fromDTO(null, wkCod);
		}

	}

}
