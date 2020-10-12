package nts.uk.ctx.at.function.app.nrl.response;

import nts.uk.ctx.at.function.app.nrl.Command;
import nts.uk.ctx.at.function.app.nrl.crypt.Codryptofy;

/**
 * Built NRL response.
 * 
 * @author manhnd
 */
public class BuiltNRLResponse extends NRLResponse {

	private Command command;
	private Object entity;
	private Status status;
	
	@Override
	public Command getCommand() {
		return this.command;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends MeanCarryable> T getEntity(Class<T> clazz) {
		return (T) entity;
	}

	@Override
	protected void setEntity(Object entity) {
		this.entity = entity;
	}

	@Override
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public BuiltNRLResponse(Command command, Object entity, Status status) {
		this.command = command;
		this.entity = entity;
		this.status = status;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.app.nrl.response.NRLResponse#encrypt(java.lang.String)
	 */
	@Override
	public String encrypt(String payload) {
		return Codryptofy.aesEncrypt(payload);
	}
}
