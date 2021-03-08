package nts.uk.ctx.at.shared.dom.workmanagement.repo.workmaster;


import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.ctx.at.shared.dom.workmanagement.aggregateroot.workmaster.Work;

import java.util.List;
import java.util.Optional;

/**
 * 作業 Repository
 *
 * @author chinh.hm
 */
public interface WorkingRepository {
    /**
     * insert
     *
     * @param work
     */
    void insert(Work work);

    /**
     * update
     *
     * @param work
     */
    void update(Work work);

    /**
     * delete
     *
     * @param cid
     * @param taskFrameNo
     * @param code
     */
    void delete(String cid, TaskFrameNo taskFrameNo, TaskCode code);

    /**
     * すべての作業を取得する
     *
     * @param cid
     * @return
     */
    List<Work> getListWork(String cid);

    /**
     * 作業枠NOを指定して作業を取得する
     *
     * @param cid
     * @param taskFrameNo
     * @return
     */
    List<Work> getListWork(String cid, TaskFrameNo taskFrameNo);

    /**
     * 作業枠NOとコードを指定して作業を取得する
     *
     * @param cid
     * @param taskFrameNo
     * @param code
     * @return
     */
    Optional<Work> getOptionalWork(String cid, TaskFrameNo taskFrameNo, TaskCode code);

    /**
     * 作業枠リストを指定して作業を取得する
     *
     * @param cid
     * @param taskFrameNos
     * @return
     */
    List<Work> getListWork(String cid, List<TaskFrameNo> taskFrameNos);

    /**
     * 基準日を指定して使用可能な作業を全て取得する
     *
     * @param cid
     * @param referenceDate
     * @return
     */
    List<Work> getListWork(String cid, GeneralDate referenceDate);

    /**
     * 作業枠リストと基準日を指定して使用可能な作業を全て取得する
     *
     * @param cid
     * @param referenceDate
     * @param taskFrameNos
     * @return
     */

    List<Work> getListWork(String cid, GeneralDate referenceDate, List<TaskFrameNo> taskFrameNos);

    /**
     * 作業枠NOと基準日と作業コードリストを指定して使用可能な作業を全て取得する
     *
     * @param cid
     * @param referenceDate
     * @param taskFrameNo
     * @param codes
     * @return
     */
    List<Work> getListWork(String cid, GeneralDate referenceDate, TaskFrameNo taskFrameNo, List<TaskCode> codes);

    /**
     * 作業枠NOと作業コードを指定して、その子作業をすべて取得する
     *
     * @param cid
     * @param taskFrameNo
     * @param code
     * @return
     */
    List<Work> getListWork(String cid, TaskFrameNo taskFrameNo, TaskCode code);

    /**
     * 作業枠NOと作業コードリストを作業を全て取得する
     *
     * @param cid
     * @param taskFrameNo
     * @param codes
     * @return
     */
    List<Work> getListWork(String cid, TaskFrameNo taskFrameNo, List<TaskCode> codes);

    /**
     * @param cid
     * @param taskFrameNo
     * @param code
     * @return
     */
    boolean checkExit(String cid, TaskFrameNo taskFrameNo, TaskCode code);


}
