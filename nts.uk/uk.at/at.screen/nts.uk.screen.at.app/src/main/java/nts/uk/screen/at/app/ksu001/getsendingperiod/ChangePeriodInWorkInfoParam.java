package nts.uk.screen.at.app.ksu001.getsendingperiod;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.DateInMonth;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ChangePeriodInWorkInfoParam {
	
	public GeneralDate startDate;            	 
	public GeneralDate endDate;    	
	
	public int unit;
	public String workplaceId;     	         
	public String workplaceGroupId;
	public List<String> sids;
	public boolean getActualData;
	
	
	
	// ・集計したい個人計：Optional<個人計カテゴリ>
	public Integer personalCounterOp;
	
	//・集計したい職場計：Optional<職場計カテゴリ>
	
	public Integer workplaceCounterOp;
	// ・締め日：日付
	public DateInMonth closeDate;
	
}
