package nts.uk.shr.pereg.app.command;

import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefAddCommand;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefAddCommandHandler;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefUpdateCommand;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefUpdateCommandHandler;

@ApplicationScoped
public class PeregCommandFacade {

//	@Inject
	private PeregCommandHandlerCollector handlerCollector;
	
	/** Command handlers to add */
	private Map<String, PeregAddCommandHandler<?>> addHandlers;
	
	/** Command handlers to update */
	private Map<String, PeregUpdateCommandHandler<?>> updateHandlers;

	/** this handles command to add data defined by user. */
//	@Inject
	private PeregUserDefAddCommandHandler userDefAdd;
	
	/** this handles command to update data defined by user. */
//	@Inject
	private PeregUserDefUpdateCommandHandler userDefUpdate;

	/**
	 * Initializes.
	 */
	@PostConstruct
	private void init() {
		this.addHandlers = this.handlerCollector.collectAddHandlers().stream()
				.collect(Collectors.toMap(h -> h.targetCategoryId(), h -> h));
		
		this.updateHandlers = this.handlerCollector.collectUpdateHandlers().stream()
				.collect(Collectors.toMap(h -> h.targetCategoryId(), h -> h));
		
	}
	
	/**
	 * Handles add commands.
	 * @param inputContainer inputs
	 */
	@Transactional
	public void add(PeregInputContainer inputContainer) {
		
		inputContainer.getInputs().forEach(inputsByCategory -> {
			val handler = this.addHandlers.get(inputsByCategory.getCategoryId());
			val commandForSystemDomain = inputsByCategory.createCommandForSystemDomain(handler.commandClass());
			handler.handlePeregCommand(commandForSystemDomain);
			
			val commandForUserDef = new PeregUserDefAddCommand(inputsByCategory);
			this.userDefAdd.handle(commandForUserDef);
		});
	}
	
	/**
	 * Handles update commands.
	 * @param inputContainer inputs
	 */
	@Transactional
	public void update(PeregInputContainer inputContainer) {
		
		inputContainer.getInputs().forEach(inputsByCategory -> {
			val handler = this.updateHandlers.get(inputsByCategory.getCategoryId());
			val commandForSystemDomain = inputsByCategory.createCommandForSystemDomain(handler.commandClass());
			handler.handlePeregCommand(commandForSystemDomain);
			
			val commandForUserDef = new PeregUserDefUpdateCommand(inputsByCategory);
			this.userDefUpdate.handle(commandForUserDef);
		});
	}
	
}
