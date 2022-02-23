package nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal.editstate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AnyPeriodEditingStateRepository {
    void insert(AnyPeriodCorrectionEditingState state);

    void update(AnyPeriodCorrectionEditingState state);

    void delete(String employeeId, String anyPeriodFrameCode);

    void persist(AnyPeriodCorrectionEditingState state);

    Map<String, List<AnyPeriodCorrectionEditingState>> getByListEmployee(String anyPeriodFrameCode, List<String> employeeIds);

    List<AnyPeriodCorrectionEditingState> getByEmployee(String anyPeriodFrameCode, String employeeId);

    Optional<AnyPeriodCorrectionEditingState> get(String anyPeriodFrameCode, String employeeId, int attendanceItemId);
}
