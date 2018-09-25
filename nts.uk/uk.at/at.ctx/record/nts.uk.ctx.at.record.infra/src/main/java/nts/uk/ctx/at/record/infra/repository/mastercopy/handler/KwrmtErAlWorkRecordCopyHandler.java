package nts.uk.ctx.at.record.infra.repository.mastercopy.handler;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.mastercopy.DataCopyHandler;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.KwrmtErAlWorkRecord;
import nts.uk.shr.com.context.AppContexts;
import org.apache.commons.lang3.SerializationUtils;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author locph
 */
public class KwrmtErAlWorkRecordCopyHandler extends DataCopyHandler {

    /** The insert query. */
    private final String INSERT_QUERY = "INSERT INTO KRCMT_ERAL_SET(CID, ERROR_ALARM_CD, ERROR_ALARM_NAME, FIXED_ATR, USE_ATR, REMARK_CANCEL_ERR_INP," +
        " REMARK_COLUMN_NO, ERAL_ATR, BOLD_ATR, MESSAGE_COLOR, CANCELABLE_ATR, ERROR_DISPLAY_ITEM, ERAL_CHECK_ID, CANCEL_ROLE_ID)" +
        " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    /**
     * The select query.
     */
    public static final String FIND_ALL_ERAL_WORK_RECORD = "SELECT w FROM KwrmtErAlWorkRecord w " +
            "WHERE w.kwrmtErAlWorkRecordPK.companyId = :cid";


    /**
     * Instantiates a new kshst overtime frame data copy handler.
     *
     * @param copyMethod the copy method
     * @param companyId  the company cd
     */
    public KwrmtErAlWorkRecordCopyHandler(JpaRepository repository, int copyMethod, String companyId) {
        this.copyMethod = copyMethod;
        this.companyId = companyId;
        this.entityManager = repository.getEntityManager();
        this.queryProxy = repository.queryProxy();
        this.commandProxy = repository.commandProxy();
    }

    /* (non-Javadoc)
     * @see nts.uk.ctx.sys.assist.dom.handler.handler.DataCopyHandler#doCopy()
     */
    @Override
    public void doCopy() {

        String sourceCid = AppContexts.user().zeroCompanyIdInContract();
        String targetCid = companyId;

        switch (copyMethod) {
            case REPLACE_ALL:
                // Delete all old data
                copyMasterData(sourceCid,targetCid,true);
                break;
            case ADD_NEW:
                // Insert Data
                copyMasterData(sourceCid,targetCid,false);
                break;
            case DO_NOTHING:
                // Do nothing
                break;
            default:
                break;
        }
    }

    /**
     * List all KwrmtErAlWorkRecord
     *
     * @param cid
     * @return
     */
    private List<KwrmtErAlWorkRecord> findAllByCid(String cid){
        return this.queryProxy.query(FIND_ALL_ERAL_WORK_RECORD, KwrmtErAlWorkRecord.class)
                .setParameter("cid", cid).getList();
    }

    /**
     * run copy
     *
     * @param sourceCid
     * @param targetCid
     * @param isReplace
     */
    public void copyMasterData(String sourceCid, String targetCid, boolean isReplace) {
        //find
        List<KwrmtErAlWorkRecord> weeklyWorkSetEntities = findAllByCid(sourceCid);
        List<KwrmtErAlWorkRecord> targetWeeklyWorkSetEntities = findAllByCid(targetCid);

        //data copy
        final List<KwrmtErAlWorkRecord> sourceWeeklyWorkSets = new ArrayList<>();
        weeklyWorkSetEntities.forEach(entity -> {
            KwrmtErAlWorkRecord cloneObject = SerializationUtils.clone(entity);
            cloneObject.kwrmtErAlWorkRecordPK.companyId = targetCid;
            sourceWeeklyWorkSets.add(cloneObject);
        });

        // Is replace all
        if (isReplace) {
            commandProxy.removeAll(targetWeeklyWorkSetEntities);
            entityManager.flush();
        }

        List<KwrmtErAlWorkRecord> addWeeklyWorkSetEntities = sourceWeeklyWorkSets;
        // Is add new only
        if (!isReplace) {
            addWeeklyWorkSetEntities = sourceWeeklyWorkSets.stream()
                    .filter(item -> !targetWeeklyWorkSetEntities.contains(item))
                    .collect(Collectors.toList());
        }

        for (KwrmtErAlWorkRecord en : addWeeklyWorkSetEntities) {
            Query insertQuery = this.entityManager.createNativeQuery(INSERT_QUERY);
            insertQuery.setParameter(1, this.companyId);
            insertQuery.setParameter(2, en.kwrmtErAlWorkRecordPK.errorAlarmCode);
            insertQuery.setParameter(3, en.errorAlarmName);
            insertQuery.setParameter(4, en.fixedAtr);
            insertQuery.setParameter(5, en.useAtr);
            insertQuery.setParameter(6, en.remarkCancelErrorInput);
            insertQuery.setParameter(7, en.remarkColumnNo);
            insertQuery.setParameter(8, en.typeAtr);
            insertQuery.setParameter(9, en.boldAtr);
            insertQuery.setParameter(10, en.messageColor);
            insertQuery.setParameter(11, en.cancelableAtr);
            insertQuery.setParameter(12, en.errorDisplayItem);
            insertQuery.setParameter(13, en.eralCheckId);
            insertQuery.setParameter(14, en.cancelRoleId);
            insertQuery.executeUpdate();
        }

//        this.commandProxy.insertAll(addWeeklyWorkSetEntities);
    }
}
