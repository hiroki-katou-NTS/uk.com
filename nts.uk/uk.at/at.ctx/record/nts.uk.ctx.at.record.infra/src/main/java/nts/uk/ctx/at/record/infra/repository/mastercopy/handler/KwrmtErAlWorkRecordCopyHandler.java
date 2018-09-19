package nts.uk.ctx.at.record.infra.repository.mastercopy.handler;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.mastercopy.DataCopyHandler;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.KwrmtErAlWorkRecord;
import nts.uk.shr.com.context.AppContexts;
import org.apache.commons.lang3.SerializationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author locph
 */
public class KwrmtErAlWorkRecordCopyHandler extends DataCopyHandler {
    /**
     * The select query.
     */
    public static final String FIND_ALL_ERAL_WORK_RECORD = "SELECT w FROM KwrmtErAlWorkRecord w LEFT JOIN FETCH w.krcstErAlApplication LEFT JOIN FETCH w.krcmtErAlCondition  " +
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
            en.krcstErAlApplication = null;
            en.krcmtErAlCondition = null;
        }

        this.commandProxy.insertAll(addWeeklyWorkSetEntities);
    }
}
