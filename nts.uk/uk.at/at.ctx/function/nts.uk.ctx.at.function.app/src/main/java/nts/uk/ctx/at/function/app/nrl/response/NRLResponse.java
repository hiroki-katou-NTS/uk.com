package nts.uk.ctx.at.function.app.nrl.response;

import java.util.Arrays;
import java.util.List;

import javax.enterprise.inject.spi.CDI;

import nts.uk.ctx.at.function.app.nrl.Command;
import nts.uk.ctx.at.function.app.nrl.DefaultValue;
import nts.uk.ctx.at.function.app.nrl.crypt.Codryptofy;
import nts.uk.ctx.at.function.app.nrl.data.FrameItemArranger;
import nts.uk.ctx.at.function.app.nrl.data.ItemSequence;
import nts.uk.ctx.at.function.app.nrl.data.ItemSequence.MapItem;
import nts.uk.ctx.at.function.app.nrl.data.checker.BCCCalculable;
import nts.uk.ctx.at.function.app.nrl.exceptions.IllegalCommandException;
import nts.uk.ctx.at.function.app.nrl.xml.Element;

/**
 * NRL response.
 * 
 * @author manhnd
 */
public abstract class NRLResponse implements BCCCalculable {
	
	/**
	 * Get command.
	 * @return command
	 */
	public abstract Command getCommand();
	
	/**
	 * Get entity.
	 * @param clazz
	 * @return entity
	 */
	public abstract <T extends MeanCarryable> T getEntity(Class<T> clazz);
	
	/**
	 * Set entity.
	 * @param entity
	 */
	protected abstract void setEntity(Object entity);
	
	/**
	 * Get status.
	 * @return status
	 */
	public abstract Status getStatus();
	
	/**
	 * Encrypt payload.
	 * @param payload
	 * @return ecrypted payload
	 */
	public abstract String encrypt(String payload);
	
	/**
	 * Push BCC.
	 * @param clazz
	 * @return response
	 */
	public <T extends MeanCarryable> NRLResponse pushBCC(Class<T> clazz) {
		T entity = getEntity(clazz);
		entity.bcc(DefaultValue.INIT_BCC);
		List<String> orders = ItemSequence.enumerate(getCommand(), false).orElseThrow(IllegalCommandException::new);
		byte[] bytes = entity.getBytes(orders);
		entity.bcc(bccToString(checkSum(bytes)));
		return this;
	}
	
	/**
	 * Encrypt payload.
	 * @param clazz
	 * @param payload
	 * @return response
	 */
	public <T extends MeanCarryable> NRLResponse encryptPayload(Class<T> clazz, String payload) {
		T entity = getEntity(clazz);
		entity.setBBytes(Codryptofy.decode(payload));
		addPayload(clazz, encrypt(payload));
		return this;
	}
	
	/**
	 * Add payload.
	 * @param clazz
	 * @param payload
	 * @return response
	 */
	public <T extends MeanCarryable> NRLResponse addPayload(Class<T> clazz, String payload) {
		T entity = getEntity(clazz);
		entity.payload(payload);
		entity.bcc(DefaultValue.INIT_BCC);
		List<String> orders = ItemSequence.enumerate(getCommand(), false).orElseThrow(IllegalCommandException::new);
		byte[] bytes = entity.getBytes(orders);
		entity.bcc(bccToString(checkSum(bytes)));
		return this;
	}
	
	/**
	 * To string.
	 * @param bcc
	 * @return text
	 */
	private String bccToString(short bcc) {
		StringBuilder sb = new StringBuilder();
		sb.append(Codryptofy.byteToHexString((byte)(bcc >> 8)));
		sb.append(Codryptofy.byteToHexString((byte)(bcc & 0x00ff)));
		return sb.toString().toUpperCase();
	}
	
	/**
	 * OK.
	 * @return response
	 */
	public static ResponseBuilder ok() {
		return ResponseBuilder.newInstance().status(Status.ACCEPT);
	}
	
	/**
	 * OK.
	 * @param nrlNo
	 * @param macAddr
	 * @return response
	 */
	public static ResponseBuilder ok(String nrlNo, String macAddr) {
		return ResponseBuilder.newInstance(nrlNo, macAddr).status(Status.ACCEPT)
				.command(Command.ACCEPT).accept();
	}
	
	/**
	 * OK.
	 * @param entity
	 * @return response
	 */
	public static ResponseBuilder ok(Object entity) {
		return ResponseBuilder.newInstance().status(Status.ACCEPT).entity(entity);
	}
	
	/**
	 * No accept.
	 * @param nrlNo
	 * @param macAddr
	 * @return response
	 */
	public static ResponseBuilder noAccept(String nrlNo, String macAddr) {
		return ResponseBuilder.newInstance(nrlNo, macAddr).status(Status.NOACCEPT)
				.command(Command.NOACCEPT).noAccept();
	}
	
	/**
	 * Mute.
	 * @return response
	 */
	public static NRLResponse mute() {
		return ResponseBuilder.newInstance().build();
	}
	
	public static class ResponseBuilder {
		private Command command;
		private Object entity;
		private Status status;
		private MapItem nrlNo;
		private MapItem macAddr;
		
		private ResponseBuilder() {}
		
		private ResponseBuilder(String nrlNo, String macAddr) { 
			this.nrlNo = new MapItem(Element.NRL_NO, nrlNo);
			this.macAddr = new MapItem(Element.MAC_ADDR, macAddr);
		}
		
		/**
		 * Create new instance.
		 * @return instance
		 */
		public static ResponseBuilder newInstance() {
			return new ResponseBuilder();
		}
		
		/**
		 * Create new instance.
		 * @param nrlNo
		 * @param macAddr
		 * @return instance
		 */
		public static ResponseBuilder newInstance(String nrlNo, String macAddr) {
			return new ResponseBuilder(nrlNo, macAddr);
		}
		
		/**
		 * Set command.
		 * @param command
		 * @return response
		 */
		public ResponseBuilder command(Command command) {
			this.command = command;
			return this;
		}
		
		/**
		 * Set entity.
		 * @param entity
		 * @return response
		 */
		public ResponseBuilder entity(Object entity) {
			this.entity = entity;
			return this;
		}
		
		/**
		 * Set status.
		 * @param s
		 * @return response
		 */
		public ResponseBuilder status(Status s) {
			this.status = s;
			return this;
		}
		
		/**
		 * No accept.
		 * @return response
		 */
		public ResponseBuilder noAccept() {
			if (nrlNo == null || macAddr == null) throw new RuntimeException("NRL no and MAC address are required.");
			List<MapItem> nak = Arrays.asList(FrameItemArranger.SOH(), FrameItemArranger.HDR_NAK(),
					FrameItemArranger.Length_NAK(), FrameItemArranger.Version(), FrameItemArranger.FlagEndNoAck(), 
					FrameItemArranger.NoFragment(), this.nrlNo, this.macAddr, FrameItemArranger.ZeroPadding());
			entity = CDI.current().select(FrameItemArranger.class).get().plot(nak, Command.NOACCEPT);
			return this;
		}
		
		/**
		 * Accept.
		 * @return response
		 */
		public ResponseBuilder accept() {
			if (nrlNo == null || macAddr == null) throw new RuntimeException("NRL no and MAC Address are required.");
			List<MapItem> items = Arrays.asList(FrameItemArranger.SOH(), new MapItem(Element.HDR, Command.ACCEPT.Response),
					new MapItem(Element.LENGTH, Element.Value.ACCEPT_RES_LEN), FrameItemArranger.Version(),
					FrameItemArranger.FlagEndNoAck(), FrameItemArranger.NoFragment(), this.nrlNo, this.macAddr, 
					FrameItemArranger.ZeroPadding());
			entity = CDI.current().select(FrameItemArranger.class).get().plot(items, Command.ACCEPT);
			return this;
		}
		
		/**
		 * Build response.
		 * @return response
		 */
		public NRLResponse build() {
			return new BuiltNRLResponse(this.command, this.entity, this.status);
		}
	}
	
}
