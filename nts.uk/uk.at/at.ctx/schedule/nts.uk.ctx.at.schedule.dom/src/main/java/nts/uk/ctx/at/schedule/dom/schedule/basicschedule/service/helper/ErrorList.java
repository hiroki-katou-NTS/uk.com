package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service.helper;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.arc.error.BusinessException;

public class ErrorList {

	@Getter
	private final List<String> errors = new ArrayList<>();
	
	public void addMessage(String messageId) {
		if (!errors.contains(messageId)) {
			errors.add(messageId);
		}
	}
	
	public void addMessage(BusinessException bizEx) {
		this.addMessage(bizEx.getMessageId());
	}
	
	public void addIfBusinessException(Exception ex) {
		if (ex.getCause() instanceof BusinessException) {
			this.addMessage((BusinessException) ex.getCause());
		}
	}
}
