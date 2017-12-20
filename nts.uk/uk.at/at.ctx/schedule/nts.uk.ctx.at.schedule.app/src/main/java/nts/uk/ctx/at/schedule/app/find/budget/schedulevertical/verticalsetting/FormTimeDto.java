package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormTime;

/**
 * TanLV
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FormTimeDto {
	/* 会社ID */
    private String companyId;
    
    /*コード*/
    private String verticalCalCd;
    
    /* 汎用縦計項目ID */
    private String verticalCalItemId;
    
    /* カテゴリ区分 */
    private int categoryIndicator;
    
    /* 実績表示区分 */
    private int actualDisplayAtr;
    
    private List<FormTimeFuncDto> lstFormTimeFunc;

    /**
     * FormTimeDto
     * @param domain
     * @return
     */
	public static FormTimeDto fromDomain(FormTime domain) {
		List<FormTimeFuncDto> items = domain.getLstFormTimeFunc().stream()
				.map(x-> FormTimeFuncDto.fromDomain(x))
				.collect(Collectors.toList());
		
		return new FormTimeDto(
				domain.getCompanyId(),
				domain.getVerticalCalCd(),
				domain.getVerticalCalItemId(),
				domain.getCategoryIndicator().value,
				domain.getActualDisplayAtr().value,
				items
			);
	}
}
