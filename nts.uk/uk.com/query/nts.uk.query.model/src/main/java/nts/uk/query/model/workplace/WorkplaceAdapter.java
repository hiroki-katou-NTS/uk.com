package nts.uk.query.model.workplace;

import nts.arc.time.GeneralDate;

import java.util.List;

public interface WorkplaceAdapter {

    /**
     * [No.559]運用している職場の情報をすべて取得する (follow EA)
     *
     * @param companyId
     * @param baseDate
     * @return
     */
    List<WorkplaceInfoImport> getAllActiveWorkplace(String companyId, GeneralDate baseDate);

    /**
     * [No.560]職場IDから職場の情報をすべて取得する
     *
     * @param companyId
     * @param listWorkplaceId
     * @param baseDate
     * @return
     */
    List<WorkplaceInfoImport> getWorkplaceInfoByWkpIds(String companyId, List<String> listWorkplaceId, GeneralDate baseDate);

    /**
     * [No.561]過去の職場の情報を取得する
     *
     * @param companyId
     * @param historyId
     * @param listWorkplaceId
     * @return
     */
    List<WorkplaceInfoImport> getPastWorkplaceInfo(String companyId, String historyId, List<String> listWorkplaceId);
}
