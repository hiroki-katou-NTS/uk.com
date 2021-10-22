package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.reflectedperiod;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;

/**
 * 
 * @author tutk
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReflectedPeriodOutput {
	
	private ReflectedAtr reflectedAtr;
	
	private List<Stamp> listStamp = new ArrayList<>();
}
