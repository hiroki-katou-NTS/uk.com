package nts.uk.ctx.at.shared.dom.employmentrules.organizationmanagement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author laitv
 * Imported: 社員職位
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeePosition {

    /**
     * 社員ID
     **/
    private String empID;
    /**
     * 職位ID
     **/
    private String jobtitleID;

    private String jobtitleCode;

}