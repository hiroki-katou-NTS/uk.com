/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.file.pr.app.export.detailpaymentsalary.data;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.report.dom.salarydetail.SalaryCategory;

/**
 * The Class CategoryItem.
 *
 * @author duongnd
 */

@Setter
@Getter
public class CategoryItem {
    
    private SalaryCategory salaryCategory;
    
    /** The row items. */
    private List<RowItemDto> rowItems;
    
    public String getHeaderCategory () {
        String header;
        switch (salaryCategory) {
            case Payment :
                header = "【支給項目】";
                break;
            case Deduction :
                header = "【控除項目】";
                break;
            case Attendance :
                header = "";
                break;
            case ArticleOthers :
                header = "【記事項目】";
                break;
            default :
                header = "";
        }
        return header;
    }
}
