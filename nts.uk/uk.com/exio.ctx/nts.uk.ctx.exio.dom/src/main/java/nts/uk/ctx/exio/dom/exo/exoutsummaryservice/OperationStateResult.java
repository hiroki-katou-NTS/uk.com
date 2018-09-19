package nts.uk.ctx.exio.dom.exo.exoutsummaryservice;

import lombok.Getter;
import nts.uk.ctx.exio.dom.exo.executionlog.ExIoOperationState;

@Getter
public class OperationStateResult {
	private ExIoOperationState state;
	private String fileId;

	public OperationStateResult(ExIoOperationState state) {
		super();
		this.state = state;
		this.fileId = null;
	}

	public OperationStateResult(ExIoOperationState state, String fileId) {
		super();
		this.state = state;
		this.fileId = fileId;
	}
}
