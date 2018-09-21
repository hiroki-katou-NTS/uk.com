package nts.uk.ctx.sys.assist.dom.mastercopy.handler;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.dom.mastercopy.CopyMethod;
import nts.uk.shr.com.context.AppContexts;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.Timestamp;
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

    private static final int INS_DATE_COLL = 1;
    private static final int INS_CCD_COLL = 2;
    private static final int INS_SCD_COLL = 3;
    private static final int INS_PG_COLL = 4;
    private static final int UDP_DATE_COLL = 5;
    private static final int UDP_CCD_COLL = 6;
    private static final int UDP_SCD_COLL = 7;
    private static final int UDP_PG_COLL = 8;

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DataCopyHandler.class);

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
     *
     */
    private boolean isOnlyCid;
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
        int keyCheck = keySize - 1;

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
                            int countDiff = 0;
                            for (int k = dataAttr.length - keyCheck; k < dataAttr.length; k++) {
                                if (dataAttr[k] == null && targetAttr[k] == null || (dataAttr[k] != null && dataAttr[k].equals(targetAttr[k]))
                                        || (targetAttr[k] != null && targetAttr[k].equals(dataAttr[k]))) {
                                    countDiff++;
                                }
                                if (countDiff == keyCheck) {
                                    sourceObjects.remove(i);
                                    i--;
                                    sourceSize--;
                                    break;
                                }
                            }
                        }
                    }
                }

                String insertQueryString = "";
                for (int i = 0; i < sourceSize; i++) {
                    Object[] rowData = (Object[]) sourceObjects.get(i);

                    if (i == 0) {
                        StringJoiner joiner = new StringJoiner(",");
                        for (int j = 1; j < rowData.length - keyCheck; j++) {
                            joiner.add("?");
                        }
                        insertQueryString = "INSERT INTO " + this.tableName + " VALUES (" + joiner.toString() + ")";
                    }

                    if (!StringUtils.isEmpty(insertQueryString)) {
                        Query iq = this.entityManager.createNativeQuery(insertQueryString);
                        // Run insert query
                        for (int k = 1; k < rowData.length - keyCheck; k++) {
                            if (rowData[0].equals(rowData[k])) {
                                rowData[k] = companyId;
                            }
                            if (k == INS_DATE_COLL || k == UDP_DATE_COLL) {
                                rowData[k] = Timestamp.valueOf(GeneralDateTime.now().localDateTime());
                            } else if (k == INS_CCD_COLL || k == UDP_CCD_COLL) {
                                rowData[k] = AppContexts.user().companyCode();
                            } else if (k == INS_SCD_COLL || k == UDP_SCD_COLL) {
                                rowData[k] = AppContexts.user().employeeCode();
                            } else if (k == INS_PG_COLL || k == UDP_PG_COLL) {
                                rowData[k] = "CMM001";
                            }

                            if (!isOnlyCid) {
                                for (int n = rowData.length - keyCheck; n < rowData.length; n++) {
                                    if (rowData[n].equals(rowData[k])) {
                                        rowData[k] = UUID.randomUUID().toString();
                                    }
                                }
                            }
                            iq.setParameter(k, rowData[k]);
                        }
                        iq.executeUpdate();
                    }
                }

            default:
                break;
        }
    }

    public static final class DataCopyHandlerBuilder {
        EntityManager entityManager;
        protected CopyMethod copyMethod;
        protected String companyId;
        private List<String> keys = new ArrayList<>();
        private String tableName;
        private boolean condKey = false;
        private boolean condTable= false;
        private String selectQuery;
        private String deleteQuery;
        private boolean isOnlyCid;

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

        public DataCopyHandlerBuilder withOnlyCid(boolean isOnlyCid) {
            this.isOnlyCid = isOnlyCid;
            return this;
        }

        public DataCopyHandlerBuilder buildQuery() {
            if (condKey && condTable) {
                StringJoiner joinerTail = new StringJoiner(",");
                String tail = "";
                if (keys.size() > 1) {
                    for (int i=1;i<keys.size();i++){
                        joinerTail.add(keys.get(i));
                    }
                    tail +=", "+joinerTail.toString();
                }

                this.selectQuery = "SELECT " + keys.get(0) + " , *" + tail + " FROM " + tableName + " WHERE CID = ?";
                this.deleteQuery = "DELETE FROM " + tableName + " WHERE CID  = ?";
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
            dataCopyHandler.setOnlyCid(isOnlyCid);
            return dataCopyHandler;
        }
    }
}
