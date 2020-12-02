package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.service.beforecheck;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.shared.dom.adapter.employee.PersonEmpBasicInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.temporaryabsence.TempAbsenceHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.temporaryabsence.TempAbsenceImport;

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
    private List<PersonEmpBasicInfoImport> personInfos;

    /**
     * List＜休職・休業社員ID＞
     * List<休職休業履歴，休職休業履歴項目の名称>
     */
    private TempAbsenceImport tempAbsence;

    /**
     * Map＜職場ID、List＜未登録打刻カード＞＞
     * Map＜職場ID、List＜打刻日、未登録打刻カード＞＞
     */
    private Map<String, List<Object>> unregistedStampCardsByWpMap;

    /**
     * List＜日次の外部予算実績＞
     */
    private List<Object> dailyExtBudgets;

    /**
     * Map＜社員ID、List＜打刻＞＞
     */
    private Map<String, List<Stamp>> stampsByEmpMap;
}
