package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.estimate;

import java.util.ArrayList;
import java.util.List;

import nts.arc.error.BusinessException;

public class AMain {

	public static void main(String[] args) {
		List<Integer> items = new ArrayList<>();
		
		items.add(1);
		items.add(2);
		items.add(3);
		items.add(4);
		
		items.stream().reduce(items.get(0), (pre, next) ->{
			
			if(pre.intValue() > next.intValue()) {
				throw new BusinessException("1234");
			}
			
			return next;
		});
	}

}
