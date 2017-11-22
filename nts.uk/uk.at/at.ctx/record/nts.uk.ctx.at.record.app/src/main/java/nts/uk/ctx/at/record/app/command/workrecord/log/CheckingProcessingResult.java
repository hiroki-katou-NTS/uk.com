package nts.uk.ctx.at.record.app.command.workrecord.log;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.uk.ctx.at.record.app.find.log.dto.ErrMessageInfoDto;
import nts.uk.ctx.at.record.dom.workrecord.log.ExecutionLog;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionContent;

@Getter
public class CheckingProcessingResult {

	private boolean isComplete;
	
	@Setter
	private List<CheckingExecutionLogResult> lstCheckingExecutionLogResult;
	
	@Setter
	private List<ErrMessageInfoDto> errorMessageInfos;

	public CheckingProcessingResult() {
		this.isComplete = true;
		this.lstCheckingExecutionLogResult = new ArrayList<CheckingExecutionLogResult>();
		this.errorMessageInfos = new ArrayList<ErrMessageInfoDto>();
	}

	public void addLogResult(CheckingExecutionLogResult checkingExecutionLogResult) {
		this.lstCheckingExecutionLogResult.add(checkingExecutionLogResult);
	}

	public void increaseCount(ExecutionContent type) {
		val optLog = lstCheckingExecutionLogResult.stream().filter(c -> c.type == type.value).findFirst();
		if (optLog.isPresent()) {
			val log = optLog.get();
			log.increaseCount();
		}
	}
	
	public void notComplete() {
		this.isComplete = false;
	}

	@Getter
	@Setter
	public class CheckingExecutionLogResult {

		private int type;
		private int count;
		private int total;
		private String status;
		private String hasError;

		public CheckingExecutionLogResult() {
			this.type = 0;
			this.count = 0;
			this.total = 0;
			this.status = "";
			this.hasError = "";
		}

		public void updateStatusFromLog(ExecutionLog log) {
			this.type = log.getExecutionContent().value;
			this.status = log.getProcessStatus().name();
			if (log.isComplete()) {
				this.hasError = log.getExistenceError().nameId;
			}
		}

		protected void increaseCount() {
			this.total = this.total + 1;
		}

	}
}