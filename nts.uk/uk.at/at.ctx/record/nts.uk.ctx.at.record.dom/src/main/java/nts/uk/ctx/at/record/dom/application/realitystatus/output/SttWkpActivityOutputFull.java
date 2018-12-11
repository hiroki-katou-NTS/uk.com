package nts.uk.ctx.at.record.dom.application.realitystatus.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public class SttWkpActivityOutputFull {

	private List<StatusWkpActivityOutput> lstData;
	private boolean error;
}
