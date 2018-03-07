package nts.uk.ctx.at.record.dom.adapter.specificdatesetting;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
@AllArgsConstructor
public class RecSpecificDateSettingImport {

	private GeneralDate date;
	private List<Integer> numberList;
}
