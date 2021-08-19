package nts.uk.ctx.at.function.dom.adapter.employment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.layer.dom.DomainObject;

/**
 *
 * 	社員所属チーム情報Imported
 * @author rafiqul.islam
 */
@Getter
@AllArgsConstructor
public class EmpTeamInfoImport extends DomainObject {
    /**
     * 社員ID
     **/
    private String employeeID;

    /**
     * チームコード
     **/
    private String scheduleTeamCd;

    /**
     * チーム名称
     **/
    private String scheduleTeamName;
}