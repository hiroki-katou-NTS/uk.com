package nts.uk.ctx.bs.employee.app.find.wkpdep;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Data
@NoArgsConstructor
public class DepWkpInfoFindObject {

    /** The start mode (department or workplace). */
    private Integer startMode;

    /** The base date. */
    private GeneralDate baseDate;

    /** The system type. */
    private Integer systemType = 5;

    /** The restriction of reference range. */
    private Boolean restrictionOfReferenceRange;

}
