package nts.uk.ctx.at.function.dom.adapter.alarm;

import nts.arc.time.GeneralDate;

import java.util.List;
import java.util.Map;

public interface EmployeeWorkplaceAdapter {
    /**
     * [1]職場IDを取得する
     *
     * 社員IDリストから職場IDを取得する - Get a workplace ID from the employee ID list
     *
     * @param sIds   - 社員IDリスト
     * @param baseDate - 基準日
     * @return Map<社員ID、職場ID>
     */
    List<AffAtWorkplaceExport> getWorkplaceId(List<String> sIds, GeneralDate baseDate);
}
