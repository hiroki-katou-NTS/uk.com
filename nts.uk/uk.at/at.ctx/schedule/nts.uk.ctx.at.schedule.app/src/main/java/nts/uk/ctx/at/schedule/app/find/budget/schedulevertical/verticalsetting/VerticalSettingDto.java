package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.VerticalCalSet;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VerticalSettingDto {
	/* 会社ID */
    private String companyId;
    
    /*コード*/
    private String verticalCalCd;
    
    /*名称*/
    private String verticalCalName;
    
    /*単位*/
    private int unit;
    
    /*利用区分*/
    private int useAtr;
    
    /*応援集計区分*/
    private int assistanceTabulationAtr;

    public static VerticalSettingDto fromDomain(VerticalCalSet domain){
		return new VerticalSettingDto(
				domain.getCompanyId(),
				domain.getVerticalCalCd(),
				domain.getVerticalCalName(),
				domain.getUnit().value,
				domain.getUseAtr().value,
				domain.getAssistanceTabulationAtr().value
		);
	}
}
