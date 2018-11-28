package nts.uk.ctx.pr.core.dom.adapter.employee.department;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Department {
    /*階層コード*/
    private String hierarchyCd;
    /** The workplace id. */
    // 部門ID
    private String departmentId;
}
