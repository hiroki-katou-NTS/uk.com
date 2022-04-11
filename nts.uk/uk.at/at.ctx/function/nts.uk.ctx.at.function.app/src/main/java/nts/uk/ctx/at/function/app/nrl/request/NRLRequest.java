package nts.uk.ctx.at.function.app.nrl.request;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.app.nrl.Command;
import nts.uk.ctx.at.function.app.nrl.data.FrameItemArranger;
import nts.uk.ctx.at.function.app.nrl.data.ItemSequence;
import nts.uk.ctx.at.function.app.nrl.data.checker.BCCCalculable;
import nts.uk.ctx.at.function.app.nrl.exceptions.ErrorCode;
import nts.uk.ctx.at.function.app.nrl.exceptions.IllegalCommandException;
import nts.uk.ctx.at.function.app.nrl.repository.Terminal;
import nts.uk.ctx.at.function.app.nrl.response.MeanCarryable;
import nts.uk.ctx.at.function.app.nrl.response.NRLResponse;
import nts.uk.ctx.at.function.app.nrl.xml.Element;
import nts.uk.ctx.at.function.app.nrl.xml.Frame;

/**
 * NRL request.
 * 
 * @author manhnd
 *
 * @param <T>
 */
@Stateless
public abstract class NRLRequest<T extends MeanCarryable> implements BCCCalculable {
	
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
	public NRLResponse responseTo(String empInfoTerCode, T frame) {
		Terminal terminal = new Terminal(frame.pickItem(Element.NRL_NO), frame.pickItem(Element.MAC_ADDR),
				frame.pickItem(Element.CONTRACT_CODE));

		Named name = this.getClass().getAnnotation(Named.class);
		if (name == null) throw new RuntimeException("Request must have a name.");
		Command command = name.value();
		
		byte[] dataBytes = frame.getBytes(ItemSequence.enumerate(command, true)
								.orElseThrow(IllegalCommandException::new));
		if (checkSum(dataBytes) != 0) {
			return NRLResponse.noAccept(terminal.getNrlNo(), terminal.getMacAddress(), frame.pickItem(Element.CONTRACT_CODE)).build()
					.addPayload(Frame.class, ErrorCode.BCC.value);
		}
		
		ResourceContext<T> context = new ResourceContext<>(frame, terminal, command, itemArranger);
		if(command.onlyCreate()) {
			context.responseAccept();
			return context.getResponse();
		}
		sketch(empInfoTerCode, context);
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
	public abstract void sketch(String empInfoTerCode, ResourceContext<T> context);
	
	/**
	 * Response length.
	 * @return length of response
	 */
	public abstract String responseLength();
}
