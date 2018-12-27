package nts.uk.ctx.exio.dom.exo.exoutsummaryservice;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Setter
@Getter
public class ExOutSetting {
	private String conditionSetCd;
	private String userId;
	private Integer categoryId;
	private GeneralDate startDate;
	private GeneralDate endDate;
	private GeneralDate referenceDate;
	private String processingId;
	private boolean standardType;
	private List<String> sidList;
	
	public ExOutSetting(String conditionSetCd, String userId, Integer categoryId, GeneralDate startDate, GeneralDate endDate,
			GeneralDate referenceDate, String processingId, boolean standardType, List<String> sidList) {
		this.conditionSetCd = conditionSetCd;
		this.userId = userId;
		this.categoryId = categoryId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.referenceDate = referenceDate;
		this.processingId = processingId;
		this.standardType = standardType;
		this.sidList = sidList;
	}
}
