package nts.uk.ctx.at.shared.dom.workmanagement.repo.worknarrowingdown;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.workmanagement.aggregateroot.worknarrowingdown.NarrowingDownWorkByWorkplace;

import java.util.List;
import java.util.Optional;

/**
 * 職場別作業の絞込 Repository
 * @author chinh.hm
 */
public interface NarrowingByWorkplaceRepository {
    /**
     * insert
     *
     * @param narrowing
     */
    void insert(NarrowingDownWorkByWorkplace narrowing);

    /**
     * update
     *
     * @param narrowing
     */
    void update(NarrowingDownWorkByWorkplace narrowing);

    /**
     * delete
     *
     * @param workPlaceId
     * @param taskFrameNo
     */
    void delete(String workPlaceId, TaskFrameNo taskFrameNo);

    /**
     * すべての職場別作業の絞込を取得する
     *
     * @param cid
     * @return
     */
    List<NarrowingDownWorkByWorkplace> getListWorkByCid(String cid);


    /**
     * 職場IDと作業枠NOを指定して職場別作業の絞込を取得する
     *
     * @param workPlaceId
     * @param taskFrameNo
     * @return
     */
    Optional<NarrowingDownWorkByWorkplace> getOptionalWork(String workPlaceId, TaskFrameNo taskFrameNo);

    /**
     * 職場リストと作業枠NOを指定して職場別作業の絞込を取得する
     *
     * @param workPlaceIds
     * @param taskFrameNo
     * @return
     */
    List<NarrowingDownWorkByWorkplace> getListWork(List<String> workPlaceIds, TaskFrameNo taskFrameNo);

    /**
     * 職場IDを指定して職場別作業の絞込をすべて取得する
     *
     * @param workPlaceId
     * @return
     */
    List<NarrowingDownWorkByWorkplace> getListWorkByWpl(String workPlaceId);


}
