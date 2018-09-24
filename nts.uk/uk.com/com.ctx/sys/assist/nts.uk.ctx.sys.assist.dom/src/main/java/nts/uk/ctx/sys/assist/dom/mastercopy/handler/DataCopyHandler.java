package nts.uk.ctx.sys.assist.dom.mastercopy.handler;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.sys.assist.dom.mastercopy.CopyMethod;
import nts.uk.shr.com.context.AppContexts;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

/**
 * The Interface CopyHandler.
 */
@Setter
@Getter
public class DataCopyHandler {

    /**
     * The entity manager.
     */
    protected EntityManager entityManager;
    /**
     * The copy method.
     */
    protected CopyMethod copyMethod;
    /**
     * The company Id.
     */
    protected String companyId;
    /**
     * The lít key.
     */
    private List<String> keys = new ArrayList<>();
    /**
     * The table Name.
     */
    private String tableName;
    /**
     * The select by cid query.
     */
    private String selectQuery;
    /**
     * The delete by cid query.
     */
    private String deleteQuery;

    /**
     * Do copy.
     */
    @SuppressWarnings("unchecked")
    public void doCopy() {
        // Get all company zero data
        Query sq = this.entityManager.createNativeQuery(this.selectQuery)
                .setParameter(1, AppContexts.user().zeroCompanyIdInContract());
        List<Object> sourceObjects = sq.getResultList();
        if (sourceObjects.isEmpty()) return;

        int sourceSize = sourceObjects.size();
        int keySize = keys.size();

        Query selectQueryTarget = this.entityManager.createNativeQuery(this.selectQuery).setParameter(1,
                this.companyId);
        List<Object> oldDatas = selectQueryTarget.getResultList();

        switch (copyMethod) {
            case REPLACE_ALL:
                Query dq = this.entityManager.createNativeQuery(this.deleteQuery).setParameter(1,
                        this.companyId);
                dq.executeUpdate();
            case DO_NOTHING:
                if(copyMethod != CopyMethod.REPLACE_ALL && !oldDatas.isEmpty()){
                    return;
                }
            case ADD_NEW:
                if (copyMethod == CopyMethod.ADD_NEW) {
                    // ignore data existed
                    for (int i = 0; i < sourceSize; i++) {
                        Object[] dataAttr = (Object[]) sourceObjects.get(i);
                        for (int j = 0; j < oldDatas.size(); j++) {
                            Object[] targetAttr = (Object[]) oldDatas.get(j);
                            // compare keys and remove
                            if ((dataAttr[1] == null && targetAttr[1] == null) || (dataAttr[1] != null && dataAttr[1].equals(targetAttr[1]))
                                    || (targetAttr[1] != null && targetAttr[1].equals(dataAttr[1]))) {
                                sourceObjects.remove(i);
                                i--;
                                sourceSize--;
                                break;
                            }
                        }
                    }
                }

                String insertQueryString = "";
                for (int i = 0; i < sourceSize; i++) {
                    Object[] rowData = (Object[]) sourceObjects.get(i);

                    if (i == 0) {
                        StringJoiner joiner = new StringJoiner(",");
                        for (int j = keySize; j < rowData.length; j++) {
                            joiner.add("?");
                        }
                        insertQueryString = "INSERT INTO " + this.tableName + " VALUES (" + joiner.toString() + ")";
                    }

                    if (!StringUtils.isEmpty(insertQueryString)) {
                        Query iq = this.entityManager.createNativeQuery(insertQueryString);
                        // Run insert query
                        for (int k = keySize; k < rowData.length; k++) {
                            if (rowData[0].equals(rowData[k])) {
                                rowData[k] = companyId;
                            }
                            for (int n = 1; n < keySize; n++) {
                                if(rowData[n].equals(rowData[k])){
                                    rowData[k] = UUID.randomUUID().toString();
                                }
                            }
                            iq.setParameter(k - keySize + 1, rowData[k]);
                        }
                        iq.executeUpdate();
                    }
                }

            default:
                break;
        }
    }

    public static final class DataCopyHandlerBuilder {
        protected EntityManager entityManager;
        protected CopyMethod copyMethod;
        protected String companyId;
        private List<String> keys = new ArrayList<>();
        private String tableName;
        private boolean condKey = false;
        private boolean condTable= false;
        private String selectQuery;
        private String deleteQuery;

        private DataCopyHandlerBuilder() {
        }

        public static DataCopyHandlerBuilder aDataCopyHandler() {
            return new DataCopyHandlerBuilder();
        }

        public DataCopyHandlerBuilder withEntityManager(EntityManager entityManager) {
            this.entityManager = entityManager;
            return this;
        }

        public DataCopyHandlerBuilder withCopyMethod(CopyMethod copyMethod) {
            this.copyMethod = copyMethod;
            return this;
        }

        public DataCopyHandlerBuilder withCompanyId(String companyId) {
            this.companyId = companyId;
            return this;
        }

        public DataCopyHandlerBuilder withKeys(List<String> keys) {
            this.keys = keys;
            this.condKey = true;
            return this;
        }

        public DataCopyHandlerBuilder withTableName(String tableName) {
            this.tableName = tableName;
            this.condTable = true;
            return this;
        }

        public DataCopyHandlerBuilder buildQuery() {
            if (condKey && condTable) {
                StringJoiner joiner = new StringJoiner(",");
                for (String key : keys) {
                    joiner.add(key);
                }

                this.selectQuery = "SELECT " + joiner.toString() + " , * FROM " + tableName + " WHERE CID = ?";
                this.deleteQuery = "DELETE FROM " + tableName + " WHERE CID = ?";
            }
            return this;
        }

        public DataCopyHandler build() {
            DataCopyHandler dataCopyHandler = new DataCopyHandler();
            dataCopyHandler.setEntityManager(entityManager);
            dataCopyHandler.setCopyMethod(copyMethod);
            dataCopyHandler.setCompanyId(companyId);
            dataCopyHandler.setTableName(tableName);
            dataCopyHandler.setKeys(keys);
            dataCopyHandler.setSelectQuery(selectQuery);
            dataCopyHandler.setDeleteQuery(deleteQuery);
            return dataCopyHandler;
        }
    }
}
