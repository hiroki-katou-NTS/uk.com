package nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class HistoryIdParam {

	 private String historyId;
	 
	 private Boolean getRelatedMaster; 
}
