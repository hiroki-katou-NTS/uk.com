package nts.uk.screen.at.app.dailyperformance.correction.monthflex;

import java.util.concurrent.Callable;

public class ExecutorMonthFlex implements Callable<DPMonthResult>{

	private DPMonthFlexParam param;
	
	private DPMonthFlexProcessor processor;
	
	public ExecutorMonthFlex(DPMonthFlexParam param, DPMonthFlexProcessor processor) {
		super();
		this.param = param;
		this.processor = processor;
	} 
	
	@Override
	public DPMonthResult call() throws Exception {
		return processor.getDPMonthFlex(param);
	}

}
