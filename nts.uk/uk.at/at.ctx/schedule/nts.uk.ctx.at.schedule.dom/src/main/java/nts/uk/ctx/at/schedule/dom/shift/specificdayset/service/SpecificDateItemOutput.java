package nts.uk.ctx.at.schedule.dom.shift.specificdayset.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Value
@AllArgsConstructor
public class SpecificDateItemOutput {
	private GeneralDate date;
	private List<Integer> numberList;
}
