package nts.uk.nrl.request;

import javax.inject.Inject;

import nts.uk.nrl.Command;
import nts.uk.nrl.data.FrameItemArranger;
import nts.uk.nrl.data.ItemSequence;
import nts.uk.nrl.data.checker.BCCCalculable;
import nts.uk.nrl.exceptions.ErrorCode;
import nts.uk.nrl.exceptions.IllegalCommandException;
import nts.uk.nrl.exceptions.ParametersNotAcceptedException;
import nts.uk.nrl.repository.Terminal;
import nts.uk.nrl.repository.TerminalRepository;
import nts.uk.nrl.response.MeanCarryable;
import nts.uk.nrl.response.NRLResponse;
import nts.uk.nrl.xml.Element;
import nts.uk.nrl.xml.Frame;

/**
 * NRL request.
 * 
 * @author manhnd
 *
 * @param <T>
 */
public abstract class NRLRequest<T extends MeanCarryable> implements BCCCalculable {
	
	/**
	 * Terminal repository
	 */
	@Inject
	private TerminalRepository terminalRepository;
	
	/**
	 * Frame item arranger
	 */
	@Inject
	private FrameItemArranger itemArranger;
	
	/**
	 * Response.
	 * @param frame
	 * @return response
	 */
	public NRLResponse responseTo(T frame) {
		Terminal terminal = terminalRepository.get(frame.pickItem(Element.NRL_NO), frame.pickItem(Element.MAC_ADDR))
				.orElseThrow(ParametersNotAcceptedException::new);
		
		Named name = this.getClass().getAnnotation(Named.class);
		if (name == null) throw new RuntimeException("Request must have a name.");
		Command command = name.value();
		
		byte[] dataBytes = frame.getBytes(ItemSequence.enumerate(command, true)
								.orElseThrow(IllegalCommandException::new));
		if (checkSum(dataBytes) != 0) {
			return NRLResponse.noAccept(terminal.getNrlNo(), terminal.getMacAddress()).build()
					.addPayload(Frame.class, ErrorCode.BCC.value);
		}
		
		ResourceContext<T> context = new ResourceContext<>(frame, terminal, terminalRepository, command, itemArranger);
		sketch(context);
		return context.getResponse();
	} 
	
	/**
	 * IntrEntity.
	 * @param isCracked
	 * @return status
	 */
	public boolean intrEntity(boolean isCracked) {
		Named name = this.getClass().getAnnotation(Named.class);
		if (name == null) throw new RuntimeException("Request must have a name.");
		return name.decrypt() && !isCracked;
	}

	/**
	 * Sketch.
	 * @param context
	 */
	public abstract void sketch(ResourceContext<T> context);
	
	/**
	 * Response length.
	 * @return length of response
	 */
	public abstract String responseLength();
}
