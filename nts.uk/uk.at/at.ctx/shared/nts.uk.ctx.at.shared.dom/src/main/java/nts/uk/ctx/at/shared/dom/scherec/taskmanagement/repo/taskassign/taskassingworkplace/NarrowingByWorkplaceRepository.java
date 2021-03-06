package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskassign.taskassingworkplace;

import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskassign.taskassignworkplace.NarrowingDownTaskByWorkplace;

import java.util.List;
import java.util.Optional;

/**
 * 職場別作業の絞込 Repository
 *
 * @author chinh.hm
 */
public interface NarrowingByWorkplaceRepository {
    /**
     * insert
     *
     * @param narrowing
     */
    void insert(NarrowingDownTaskByWorkplace narrowing);

    /**
     * update
     *
     * @param narrowing
     */
    void update(NarrowingDownTaskByWorkplace narrowing);

    /**
     * delete
     *
     * @param cid
     * @param workPlaceId
     * @param taskFrameNo
     */
    void delete(String cid, String workPlaceId, TaskFrameNo taskFrameNo);

    void delete(String cid, String workPlaceId);

    /**
     * すべての職場別作業の絞込を取得する
     *
     * @param cid
     * @return
     */
    List<NarrowingDownTaskByWorkplace> getListWorkByCid(String cid);


    /**
     * 職場IDと作業枠NOを指定して職場別作業の絞込を取得する
     *
     * @param workPlaceId
     * @param taskFrameNo
     * @return
     */
    Optional<NarrowingDownTaskByWorkplace> getOptionalWork(String workPlaceId, TaskFrameNo taskFrameNo);

    /**
     * 職場リストと作業枠NOを指定して職場別作業の絞込を取得する
     *
     * @param workPlaceIds
     * @param taskFrameNo
     * @return
     */
    List<NarrowingDownTaskByWorkplace> getListWork(List<String> workPlaceIds, TaskFrameNo taskFrameNo);

    /**
     * 職場IDを指定して職場別作業の絞込をすべて取得する
     *
     * @param workPlaceId
     * @return
     */
    List<NarrowingDownTaskByWorkplace> getListWorkByWpl(String cid,String workPlaceId);

    /**
     *作業枠NOを指定して会社で登録されている職場別作業の絞込を取得する
     *
     * @param cid
     * @param taskFrameNo
     * @return
     */

    List<NarrowingDownTaskByWorkplace> getListWorkByCidAndFrameNo(String cid,TaskFrameNo taskFrameNo);


}
