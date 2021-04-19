package nts.uk.ctx.at.function.app.query.arbitraryperiodsummarytable;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.eclipse.persistence.internal.xr.ValueObject;

import java.util.List;

/**
 * 表示社員
 */
@Data
@AllArgsConstructor
public class DisplayedEmployee extends ValueObject {

    private List<DisplayContent> contentList;
    // 社員ID
    private String employeeId;
    // 社員コード
    private String employeeCode;
    // 社員名称
    private String employeeName;

}
