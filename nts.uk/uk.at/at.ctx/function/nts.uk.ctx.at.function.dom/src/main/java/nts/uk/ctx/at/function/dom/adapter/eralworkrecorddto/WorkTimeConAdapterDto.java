package nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WorkTimeConAdapterDto {
	private boolean useAtr;
    private int comparePlanAndActual;
    private boolean planFilterAtr;
    private List<String> planLstWorkTime;
    private boolean actualFilterAtr;
    private List<String> actualLstWorkTime;
    
	public WorkTimeConAdapterDto(boolean useAtr, int comparePlanAndActual, boolean planFilterAtr,
			List<String> planLstWorkTime, boolean actualFilterAtr, List<String> actualLstWorkTime) {
		super();
		this.useAtr = useAtr;
		this.comparePlanAndActual = comparePlanAndActual;
		this.planFilterAtr = planFilterAtr;
		this.planLstWorkTime = planLstWorkTime;
		this.actualFilterAtr = actualFilterAtr;
		this.actualLstWorkTime = actualLstWorkTime;
	}
    
    
}
