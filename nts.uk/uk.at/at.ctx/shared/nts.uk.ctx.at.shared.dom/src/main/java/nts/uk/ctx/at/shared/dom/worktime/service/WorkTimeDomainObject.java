package nts.uk.ctx.at.shared.dom.worktime.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.error.BundledBusinessException;
import nts.arc.error.BusinessException;
import nts.arc.validate.Validatable;

public class WorkTimeDomainObject implements Validatable {

	@Getter
	protected BundledBusinessExceptionBuffer bundledBusinessExceptions; 
	
	public WorkTimeDomainObject() {
		this.bundledBusinessExceptions = new BundledBusinessExceptionBuffer();
	}
	
	@Override
	public void validate() {
		WorkTimeValidationSevice.validate(this);
	}
	
	public static class BundledBusinessExceptionBuffer {
		
		private List<BusinessException> exceptions = new ArrayList<>();
		
		public void addMessage(String messageId, String... parameterIds) {
			this.add(new BusinessException(messageId, parameterIds));
		}
		
		public void add(BusinessException exception) {
			this.exceptions.add(exception);
		}
		
		public void addAll(Collection<BusinessException> exceptions) {
			this.exceptions.addAll(exceptions);
		}
		
		public boolean isEmpty() {
			return this.exceptions.isEmpty();
		}
		
		public BundledBusinessException bundle() {
			BundledBusinessException bbe = BundledBusinessException.newInstance();
			bbe.addMessage(this.exceptions);
			return bbe;
		}
	}
}
