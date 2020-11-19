package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.adapter.workplace;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 職場情報
 *
 * @author Le Huu Dat
 */
@AllArgsConstructor
@Getter
public class AlWorkPlaceInforImport {

    private String workplaceId;
    private String hierarchyCode;
    private String workplaceCode;
    private String workplaceName;
    private String workplaceDisplayName;
    private String workplaceGenericName;
    private String workplaceExternalCode;

}
