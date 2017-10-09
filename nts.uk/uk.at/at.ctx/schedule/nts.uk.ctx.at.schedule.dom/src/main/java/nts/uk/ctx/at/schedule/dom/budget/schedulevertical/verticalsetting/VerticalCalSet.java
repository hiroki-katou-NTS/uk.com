package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

@Getter
public class VerticalCalSet extends AggregateRoot {
	/* 会社ID */
    private String companyId;
    
    /*コード*/
    private String verticalCalCd;
    
    /*名称*/
    private String verticalCalName;
    
    /*単位*/
    private Unit unit;
    
    /*利用区分*/
    private UseAtr useAtr;
    
    /*応援集計区分*/
    private AssistanceTabulationAtr assistanceTabulationAtr;
    
    public void validate(){
    	
    }

	public VerticalCalSet(String companyId, String verticalCalCd, String verticalCalName, Unit unit, UseAtr useAtr,
			AssistanceTabulationAtr assistanceTabulationAtr) {
		
		this.companyId = companyId;
		this.verticalCalCd = verticalCalCd;
		this.verticalCalName = verticalCalName;
		this.unit = unit;
		this.useAtr = useAtr;
		this.assistanceTabulationAtr = assistanceTabulationAtr;
	}
    
	public static VerticalCalSet createFromJavaType(String companyId, String verticalCalCd, String verticalCalName, int unit, int useAtr,
			int assistanceTabulationAtr) {
		return new VerticalCalSet(companyId, verticalCalCd, verticalCalName,
				EnumAdaptor.valueOf(unit, Unit.class),
				EnumAdaptor.valueOf(useAtr, UseAtr.class),
				EnumAdaptor.valueOf(assistanceTabulationAtr, AssistanceTabulationAtr.class));
	}
}
