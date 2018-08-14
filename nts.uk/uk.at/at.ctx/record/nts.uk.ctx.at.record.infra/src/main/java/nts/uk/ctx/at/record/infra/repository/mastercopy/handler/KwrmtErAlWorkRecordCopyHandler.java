package nts.uk.ctx.at.record.infra.repository.mastercopy.handler;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.mastercopy.CopyMethod;
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
public class KwrmtErAlWorkRecordCopyHandler extends JpaRepository implements DataCopyHandler {
    /**
     * The copy method.
     */
    private CopyMethod copyMethod;

    /**
     * The company Id.
     */
    private String companyId;

    /**
     * The insert query.
     */
    private String INSERT_QUERY = "";
    public static final String FIND_ALL_ERAL_WORK_RECORD = "SELECT w FROM KwrmtErAlWorkRecord w " +
            "WHERE w.kwrmtErAlWorkRecordPK.companyId =:cid;";

    /**
     * Instantiates a new kshst overtime frame data copy handler.
     *
     * @param copyMethod the copy method
     * @param companyId  the company cd
     */
    public KwrmtErAlWorkRecordCopyHandler(CopyMethod copyMethod, String companyId) {
        this.copyMethod = copyMethod;
        this.companyId = companyId;
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

    private List<KwrmtErAlWorkRecord> findAllByCid(String cid){
        return this.queryProxy().query(FIND_ALL_ERAL_WORK_RECORD, KwrmtErAlWorkRecord.class)
                .setParameter("cid", cid).getList();
    }

    public void copyMasterData(String sourceCid, String targetCid, boolean isReplace) {
        //find
        List<KwrmtErAlWorkRecord> weeklyWorkSetEntities = findAllByCid(sourceCid);

        //data copy
        final List<KwrmtErAlWorkRecord> sourceWeeklyWorkSets = new ArrayList<>();
        weeklyWorkSetEntities.forEach(entity -> {
            KwrmtErAlWorkRecord cloneObject = SerializationUtils.clone(entity);
            cloneObject.kwrmtErAlWorkRecordPK.setCompanyId(targetCid);
            sourceWeeklyWorkSets.add(cloneObject);
        });

        // Is replace all
        if (isReplace) {
            commandProxy().removeAll(weeklyWorkSetEntities);
            getEntityManager().flush();
        }

        List<KwrmtErAlWorkRecord> addWeeklyWorkSetEntities = sourceWeeklyWorkSets;
        // Is add new only
        if (!isReplace) {
            List<KwrmtErAlWorkRecord> targetWeeklyWorkSetEntities = findAllByCid(sourceCid);
            addWeeklyWorkSetEntities = sourceWeeklyWorkSets.stream()
                    .filter(item -> !targetWeeklyWorkSetEntities.contains(item))
                    .collect(Collectors.toList());
        }
        this.commandProxy().insertAll(addWeeklyWorkSetEntities);
    }
}
