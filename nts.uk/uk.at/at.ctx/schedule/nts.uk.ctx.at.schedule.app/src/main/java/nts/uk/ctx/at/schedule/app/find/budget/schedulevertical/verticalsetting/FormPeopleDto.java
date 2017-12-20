package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormPeople;

/**
 * TanLV
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FormPeopleDto {
	/* 会社ID */
    private String companyId;
    
    /*コード*/
    private String verticalCalCd;
    
    /* 汎用縦計項目ID */
    private String verticalCalItemId;
    
    /* 実績表示区分 */
    private int actualDisplayAtr;
    
    private List<FormPeopleFuncDto> lstPeopleFunc;

    /**
     * FormPeopleDto
     * @param domain
     * @return
     */
	public static FormPeopleDto fromDomain(FormPeople domain) {
		List<FormPeopleFuncDto> items = domain.getLstPeopleFunc().stream()
				.map(x-> FormPeopleFuncDto.fromDomain(x))
				.collect(Collectors.toList());
		
		return new FormPeopleDto(
				domain.getCompanyId(),
				domain.getVerticalCalCd(),
				domain.getVerticalCalItemId(),
				domain.getActualDisplayAtr().value,
				items
			);
	}
}
