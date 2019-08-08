package nts.uk.shr.pereg.app.command;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.val;

public interface PeregAddListCommandHandler<C> extends PeregCommandHandler<C> {
	
	List<PeregAddCommandResult> handle(List<C> command);
	
	@SuppressWarnings("unchecked")
	default List<PeregAddCommandResult> handlePeregLstCommand(List<Object> peregCommand) {
		List<C> commandLst = peregCommand.parallelStream().map(c ->  (C)c).collect(Collectors.toList());
		return this.handle(commandLst);
	}

	default List<PeregAddCommandResult> handlePeregCommand(List<PeregInputContainerCps003> input) {
		val commandLst = new ArrayList<>();
		input.parallelStream().forEach(c ->{
			ItemsByCategory itemsByCategory = c.getInputs();
			commandLst.add(itemsByCategory.createCommandForSystemDomain(
					c.getPersonId(), c.getEmployeeId(), this.commandClass()));
		});
		return this.handlePeregLstCommand(commandLst);
	}
}
