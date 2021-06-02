package nts.uk.ctx.at.shared.dom.adapter.temporaryabsence;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author Le Huu Dat
 */
@AllArgsConstructor
@Getter
public class TempAbsenceImport {
    List<TempAbsenceHistoryImport> histories;
    List<TempAbsenceHisItemImport> historyItem;
}
