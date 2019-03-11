package nts.uk.shr.pereg.app.command;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.val;

public interface PeregUpdateListCommandHandler <C> extends PeregCommandHandler<C>{
	
	void handle(List<C> command);
	
	@SuppressWarnings("unchecked")
	default void handlePeregListCommand(List<Object> peregCommand) {
		List<C> commandLst = peregCommand.parallelStream().map(c ->  (C)c).collect(Collectors.toList());
		this.handle(commandLst);
	}

	default void handlePeregCommand(List<PeregInputContainerCps003> input) {
		val commandLst = new ArrayList<>();
		input.parallelStream().forEach(c ->{
			ItemsByCategory itemsByCategory = c.getInputs();
			commandLst.add(itemsByCategory.createCommandForSystemDomain(
					c.getPersonId(), c.getEmployeeId(), this.commandClass()));
		});
		this.handlePeregListCommand(commandLst);
	}
}
