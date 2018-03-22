/*
 * 
 */
package nts.uk.ctx.at.record.app.command.divergence.time.message;

import java.util.Optional;

import lombok.Data;
import nts.uk.ctx.at.record.dom.divergence.time.message.DivergenceTimeErrorAlarmMessageGetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.message.ErrorAlarmMessage;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.shr.com.context.AppContexts;

@Data
public class DivergenceTimeErrorAlarmMessageCommand{

	/** The divergence time no. */
	private Integer divergenceTimeNo;
	
	/** The divergence time name. */
	private String divergenceTimeName;
	
	/** The alarm message. */
	private String alarmMessage;
	
	/** The error message. */
	private String errorMessage;
	
	/**
	 * Instantiates a new divergence time error alarm command.
	 */
	public DivergenceTimeErrorAlarmMessageCommand(){
		super();
	}
}
