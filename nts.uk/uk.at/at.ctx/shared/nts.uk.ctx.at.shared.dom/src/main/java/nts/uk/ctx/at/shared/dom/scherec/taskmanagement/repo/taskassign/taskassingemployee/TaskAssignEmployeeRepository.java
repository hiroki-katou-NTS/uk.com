package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskassign.taskassingemployee;

import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskassign.taskassignemployee.TaskAssignEmployee;

import java.util.List;

/**
 * 社員別作業の絞込Repository
 */
public interface TaskAssignEmployeeRepository {
    void insert(TaskAssignEmployee domain);

    void update(TaskAssignEmployee domain);

    /**
     * @param companyId 会社ID
     * @param taskFrameNo 作業枠NO
     * @param taskCode 作業コード
     */
    void delete(String companyId, int taskFrameNo, String taskCode);

    /**
     * すべての社員別作業の絞込を取得する
     * @param companyId 会社ID
     * @return List<社員別作業の絞込>
     */
    List<TaskAssignEmployee> get(String companyId);

    /**
     * 社員IDと作業枠NOを指定して社員別作業の絞込を取得する
     * @param employeeId 社員ID
     * @param taskFrameNo 作業枠NO
     * @return List<社員別作業の絞込>
     */
    List<TaskAssignEmployee> get(String employeeId, int taskFrameNo);

    /**
     * 作業枠NOと作業コードを指定して社員別作業の絞込を取得する
     * @param companyId 会社ID
     * @param taskFrameNo 作業枠NO
     * @param taskCode 作業コード
     * @return List<社員別作業の絞込>
     */
    List<TaskAssignEmployee> get(String companyId, int taskFrameNo, String taskCode);

    /**
     * 作業枠NOを指定して全ての社員別作業の絞込を取得する
     * @param companyId 会社ID
     * @param taskFrameNo 作業枠NO
     * @return List<社員別作業の絞込>
     */
    List<TaskAssignEmployee> getAll(String companyId, int taskFrameNo);
}
