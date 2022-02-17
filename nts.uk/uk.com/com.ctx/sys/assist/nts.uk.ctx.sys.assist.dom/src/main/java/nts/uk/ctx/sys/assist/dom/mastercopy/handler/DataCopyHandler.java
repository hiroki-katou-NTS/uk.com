package nts.uk.ctx.sys.assist.dom.mastercopy.handler;

import lombok.val;
import nts.uk.ctx.sys.assist.dom.mastercopy.CopyMethod;
import nts.uk.shr.com.company.CompanyId;
import nts.uk.shr.com.constants.DefaultSettingKeys;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Interface CopyHandler.
 */
public class DataCopyHandler {

    private final EntityManager entityManager;
    private final CopyMethod copyMethod;
    private final CompanyId companyId;
    private final String employeeCode;
    private final List<String> keys;
    private final KeyValueHolder keyValueHolder;
    private final String tableName;
    private final String selectQuery;
    private final String deleteQuery;

    public DataCopyHandler(
            EntityManager entityManager,
            CopyMethod copyMethod,
            CompanyId companyId,
            String employeeCode,
            List<String> keys,
            KeyValueHolder keyValueHolder,
            String tableName) {

        if (keys.isEmpty()) {
            throw new RuntimeException("キー列名が必要: " + tableName);
        }

        this.entityManager = entityManager;
        this.copyMethod = copyMethod;
        this.companyId = companyId;
        this.employeeCode = employeeCode;
        this.keys = keys;
        this.keyValueHolder = keyValueHolder;
        this.tableName = tableName;

        this.selectQuery = "SELECT CONTRACT_CD, CID, *, " + String.join("," + keys)
                + " FROM " + tableName
                + " WHERE CID = ?";

        this.deleteQuery = "DELETE FROM " + tableName + " WHERE CID  = ?";

    }

    /**
     * Do copy.
     */
    public void doCopy() {

    	// ゼロ契約ゼロ会社から初期値をSELECT
        List<TableRow> sourceObjects = getTableRows(DefaultSettingKeys.COMPANY_ID);
        if (sourceObjects.isEmpty()) {
            return;
        }

        // 既存データをSELECT
        List<TableRow> oldDatas = getTableRows(this.companyId.getValue());

        if (copyMethod == CopyMethod.REPLACE_ALL) {
            // 既存データをDELETE（会社IDのみ指定で全削除）
            this.entityManager.createNativeQuery(this.deleteQuery)
                    .setParameter(1, this.companyId.getValue())
                    .executeUpdate();
        }

        if (copyMethod == CopyMethod.DO_NOTHING && !oldDatas.isEmpty()) {
            // DO_NOTHINGの場合、既存データが１つでもあれば何もしない
            return;
        }

        for (val rowData : sourceObjects) {

            if (oldDatas.stream().anyMatch(o -> o.isSameKey(rowData))) {
                // 既存データがあれば無視する（ここにはADD_NEWモードでのみ到達可能）
                continue;
            }

            String insertQueryString = rowData.createInsertSql(tableName, keyValueHolder);
            this.entityManager.createNativeQuery(insertQueryString).executeUpdate();
        }
    }

    private List<TableRow> getTableRows(String selectCompanyId) {
        Query sq = this.entityManager.createNativeQuery(this.selectQuery)
                .setParameter(1, selectCompanyId);
        return ((List<Object>) sq.getResultList())
                .stream()
                .map(data -> createTableRow((Object[]) data))
                .collect(Collectors.toList());
    }

    private TableRow createTableRow(Object[] data) {
        return new TableRow(companyId, employeeCode, data, keys.size());
    }

}
