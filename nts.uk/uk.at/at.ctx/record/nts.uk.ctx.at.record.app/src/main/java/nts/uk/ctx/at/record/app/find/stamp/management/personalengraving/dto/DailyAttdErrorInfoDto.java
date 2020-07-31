package nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.stamp.card.management.personalengraving.AppDispNameExp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.DailyAttdErrorInfo;

/**
 * @author anhdt
 *
 */
@Data
public class DailyAttdErrorInfoDto {
	
	private List<DailyAttdErrorInfoTemp> dailyAttdErrorInfos = new ArrayList<>();
	private List<AppDispNameExp> appDispNames;
	
	public DailyAttdErrorInfoDto(List<DailyAttdErrorInfo> list, List<AppDispNameExp> appDispNames) {
		for (DailyAttdErrorInfo e : list) {
			this.dailyAttdErrorInfos.add(new DailyAttdErrorInfoTemp(e));
		}
		this.appDispNames = appDispNames;
	}
	
	@Getter
	class DailyAttdErrorInfoTemp {
		private Integer checkErrorType;
		private String messageContent;
		private String messageColor;
		private String lastDateError;
		private List<Integer> listRequired;
		
		public DailyAttdErrorInfoTemp(DailyAttdErrorInfo dom) {
			this.checkErrorType = dom.getCheckErrorType().value;
			this.messageContent = dom.getPromptingMessage().getMessageContent().v();
			this.messageColor = dom.getPromptingMessage().getMessageColor().v();
			this.lastDateError = dom.getLastDateError().toString();
			this.listRequired  = dom.getListRequired();
		}
	}
	
}
