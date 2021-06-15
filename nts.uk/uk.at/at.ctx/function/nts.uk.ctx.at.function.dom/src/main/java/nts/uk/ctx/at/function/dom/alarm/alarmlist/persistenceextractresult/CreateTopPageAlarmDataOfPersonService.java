package nts.uk.ctx.at.function.dom.alarm.alarmlist.persistenceextractresult;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.function.dom.adapter.toppagealarmpub.DeleteInfoAlarmImport;
import nts.uk.ctx.at.function.dom.adapter.toppagealarmpub.TopPageAlarmImport;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

/**
 * DomainService: 本人のトップページアラームデータを作成する
 *
 * @author viet.tx
 */
@Stateless
public class CreateTopPageAlarmDataOfPersonService {
    public static AtomTask create(Require require, String companyId, List<TopPageAlarmImport> alarmListInfos,
                                  Optional<DeleteInfoAlarmImport> deleteInfo) {

        return AtomTask.of(() -> require.create(companyId, alarmListInfos, deleteInfo));
    }

    public interface Require {
        /**
         * [R-1] トップページアラームデータを作成する : using TopPageAlarmAdapter
         *
         * @param companyId  会社ID
         * @param alarmInfos List トップアラームパラメータ
         * @param delInfoOpt 削除の情報
         */
        void create(String companyId, List<TopPageAlarmImport> alarmInfos, Optional<DeleteInfoAlarmImport> delInfoOpt);
    }
}
