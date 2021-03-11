package nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WorkTypeConAdapterDto {

	private boolean useAtr;
    private int comparePlanAndActual;
    private boolean planFilterAtr;
    private List<String> planLstWorkType;
    private boolean actualFilterAtr;
    private List<String> actualLstWorkType;
    
    private int comparisonOperator;
    private BigDecimal compareStartValue;
	private BigDecimal compareEndValue;
	private int checkTimeType;
    
	public WorkTypeConAdapterDto(boolean useAtr, int comparePlanAndActual, boolean planFilterAtr,
			List<String> planLstWorkType, boolean actualFilterAtr, List<String> actualLstWorkType) {
		super();
		this.useAtr = useAtr;
		this.comparePlanAndActual = comparePlanAndActual;
		this.planFilterAtr = planFilterAtr;
		this.planLstWorkType = planLstWorkType;
		this.actualFilterAtr = actualFilterAtr;
		this.actualLstWorkType = actualLstWorkType;
	}
    
}
