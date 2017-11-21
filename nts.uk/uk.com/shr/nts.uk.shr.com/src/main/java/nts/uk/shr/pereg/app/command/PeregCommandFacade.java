package nts.uk.shr.pereg.app.command;

import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefAddCommand;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefAddCommandHandler;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefDeleteCommand;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefDeleteCommandHandler;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefUpdateCommand;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefUpdateCommandHandler;

@ApplicationScoped
public class PeregCommandFacade {

	@Inject
	private PeregCommandHandlerCollector handlerCollector;
	
	/** Command handlers to add */
	private Map<String, PeregAddCommandHandler<?>> addHandlers;
	
	/** Command handlers to update */
	private Map<String, PeregUpdateCommandHandler<?>> updateHandlers;
	
	/** Command handlers to delete */
	private Map<String, PeregDeleteCommandHandler<?>> deleteHandlers;

	/** this handles command to add data defined by user. */
	@Inject
	private PeregUserDefAddCommandHandler userDefAdd;
	
	/** this handles command to update data defined by user. */
	@Inject
	private PeregUserDefUpdateCommandHandler userDefUpdate;
	
	/** this handles command to delete data defined by user. */
	@Inject
	private PeregUserDefDeleteCommandHandler userDefDelete;

	/**
	 * Initializes.
	 */
	public void init(@Observes @Initialized(ApplicationScoped.class) Object event) {
		
		this.addHandlers = this.handlerCollector.collectAddHandlers().stream()
				.collect(Collectors.toMap(h -> h.targetCategoryId(), h -> h));
		
		this.updateHandlers = this.handlerCollector.collectUpdateHandlers().stream()
				.collect(Collectors.toMap(h -> h.targetCategoryId(), h -> h));
		
		this.deleteHandlers = this.handlerCollector.collectDeleteHandlers().stream()
				.collect(Collectors.toMap(h -> h.targetCategoryId(), h -> h));
		
	}
	
	/**
	 * Handles add commands.
	 * @param container inputs
	 */
	@Transactional
	public void add(PeregInputContainer container) {
		
		container.getInputs().forEach(itemsByCategory -> {
			val handler = this.addHandlers.get(itemsByCategory.getCategoryId());
			val result = handler.handlePeregCommand(container.getPersonId(), container.getEmployeeId(), itemsByCategory);
			
			// pass new record ID that was generated by add domain command handler
			val commandForUserDef = new PeregUserDefAddCommand(
					container.getPersonId(), container.getEmployeeId(), result.getAddedRecordId(), itemsByCategory);
			this.userDefAdd.handle(commandForUserDef);
		});
	}
	
	/**
	 * Handles update commands.
	 * @param container inputs
	 */
	@Transactional
	public void update(PeregInputContainer container) {
		
		container.getInputs().forEach(itemsByCategory -> {
			val handler = this.updateHandlers.get(itemsByCategory.getCategoryId());
			handler.handlePeregCommand(container.getPersonId(), container.getEmployeeId(), itemsByCategory);
			
			val commandForUserDef = new PeregUserDefUpdateCommand(
					container.getPersonId(), container.getEmployeeId(), itemsByCategory);
			this.userDefUpdate.handle(commandForUserDef);
		});
	}
	
	/**
	 * Handles delete command.
	 * @param command command
	 */
	@Transactional
	public void delete(PeregDeleteCommand command) {
		
		val handler = this.deleteHandlers.get(command.getCategoryId());
		handler.handlePeregCommand(command);
		
		val commandForUserDef = new PeregUserDefDeleteCommand(command);
		this.userDefDelete.handle(commandForUserDef);
	}
}
