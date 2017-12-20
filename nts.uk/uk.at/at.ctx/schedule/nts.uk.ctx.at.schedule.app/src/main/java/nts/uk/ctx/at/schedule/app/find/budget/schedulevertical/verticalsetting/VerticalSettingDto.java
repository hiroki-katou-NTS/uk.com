package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.VerticalCalSet;

/**
 * TanLV
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VerticalSettingDto {
	/** 会社ID */
    private String companyId;
    
    /**コード*/
    private String verticalCalCd;
    
    /**名称*/
    private String verticalCalName;
    
    /**単位*/
    private int unit;
    
    /**利用区分*/
    private int useAtr;
    
    /**応援集計区分*/
    private int assistanceTabulationAtr;
    
    private List<VerticalCalItemDto> verticalCalItems;

    /**
     * fromDomain
     * @param domain
     * @return
     */
    public static VerticalSettingDto fromDomain(VerticalCalSet domain){
    	List<VerticalCalItemDto> items = domain.getVerticalCalItems().stream()
				.map(x-> VerticalCalItemDto.fromDomain(x))
				.collect(Collectors.toList());
    	
		return new VerticalSettingDto(
				domain.getCompanyId(),
				domain.getVerticalCalCd().v(),
				domain.getVerticalCalName().v(),
				domain.getUnit().value,
				domain.getUseAtr().value,
				domain.getAssistanceTabulationAtr().value,
				items
		);
	}
}
