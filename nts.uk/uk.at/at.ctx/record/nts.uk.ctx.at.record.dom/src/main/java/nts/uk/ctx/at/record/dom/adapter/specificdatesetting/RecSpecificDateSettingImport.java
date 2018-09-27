package nts.uk.ctx.at.record.dom.adapter.specificdatesetting;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecSpecificDateSettingImport {

	private GeneralDate date;
	private List<Integer> numberList;
}
