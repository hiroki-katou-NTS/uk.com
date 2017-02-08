package nts.uk.shr.sample.report.ws;

import java.io.Serializable;

import lombok.Value;

@Value
public class SampleReportResult implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private String taskId;
}
