package nts.uk.ctx.at.shared.app.workrule.workinghours;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContainsResultDto {

	private boolean check;
	
	private TimeSpanForCalcSharedDto timeSpan;
}
