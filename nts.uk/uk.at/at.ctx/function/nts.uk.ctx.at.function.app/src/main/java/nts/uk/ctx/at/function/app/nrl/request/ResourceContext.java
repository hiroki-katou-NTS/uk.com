package nts.uk.ctx.at.function.app.nrl.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.app.nrl.Command;
import nts.uk.ctx.at.function.app.nrl.data.ItemSequence.MapItem;
import nts.uk.ctx.at.function.app.nrl.data.Sequential;
import nts.uk.ctx.at.function.app.nrl.exceptions.ErrorCode;
import nts.uk.ctx.at.function.app.nrl.repository.Terminal;
import nts.uk.ctx.at.function.app.nrl.repository.TerminalRepository;
import nts.uk.ctx.at.function.app.nrl.response.MeanCarryable;
import nts.uk.ctx.at.function.app.nrl.response.NRLResponse;

/**
 * Resource context.
 * 
 * @author manhnd
 *
 * @param <T>
 */
@Getter @Setter
@AllArgsConstructor
public class ResourceContext<T extends MeanCarryable> extends RequestContext<T> {

	/**
	 * Entity
	 */
	private T entity;
	
	/**
	 * Terminal 
	 */
	private Terminal terminal;
	
	/**
	 * Terminal repository 
	 */
	private TerminalRepository terminalRepository;
	
	/**
	 * Command 
	 */
	private Command command;
	
	/**
	 * Sequence 
	 */
	private Sequential<? extends MeanCarryable> sequence;
	
	/**
	 * Response
	 */
	private NRLResponse response;
	
	public ResourceContext(T entity, Terminal terminal, TerminalRepository repo, 
			Command command, Sequential<? extends MeanCarryable> sequence) {
		this.entity = entity;
		this.terminal = terminal;
		this.terminalRepository = repo;
		this.command = command;
		this.sequence = sequence;
	}

	/**
	 * Collect items.
	 * @param items
	 */
	@SuppressWarnings("unchecked")
	public void collect(List<MapItem> items) {
		T f = (T) sequence.plot(items, command);
		response = NRLResponse.ok().entity(f).command(command).build().pushBCC(getType());
	}
	
	/**
	 * Collect items and payload.
	 * @param items
	 * @param payload
	 */
	@SuppressWarnings("unchecked")
	public void collect(List<MapItem> items, String payload) {
		T f = (T) sequence.plot(items, command);
		response = NRLResponse.ok().entity(f).command(command).build().addPayload(getType(), payload);
	}
	
	/**
	 * Collect items and encrypt payload.
	 * @param items
	 * @param payload
	 */
	@SuppressWarnings("unchecked")
	public void collectEncrypt(List<MapItem> items, String payload) {
		T f = (T) sequence.plot(items, command);
		response = NRLResponse.ok().entity(f).command(command).build().encryptPayload(getType(), payload);
	}
	
	/**
	 * Response no accept.
	 * @param error
	 */
	public void responseNoAccept(ErrorCode error) {
		response = NRLResponse.noAccept(terminal.getNrlNo(), terminal.getMacAddress()).build()
				.addPayload(getType(), error.value);
	}

	/**
	 * Response accept.
	 */
	public void responseAccept() {
		response = NRLResponse.ok(terminal.getNrlNo(), terminal.getMacAddress()).build().pushBCC(getType());
	}

	/**
	 * Get type.
	 * @return type
	 */
	@SuppressWarnings("unchecked")
	private Class<T> getType() {
		return (Class<T>) entity.getClass();
	}
}
