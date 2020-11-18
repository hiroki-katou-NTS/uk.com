package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.service.beforecheck;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.adapter.employee.PersonEmpBasicInfoImport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Le Huu Dat
 */
@Getter
@Setter
@NoArgsConstructor
public class DataBeforeCheckDto {
    /**
     * List＜個人社員基本情報＞
     */
    private List<PersonEmpBasicInfoImport> empInfos = new ArrayList<>();

    /**
     * Map＜職場ID、List＜未登録打刻カード＞＞
     */
    private Map<String, List<Object>> unregistedStampCards;
}
