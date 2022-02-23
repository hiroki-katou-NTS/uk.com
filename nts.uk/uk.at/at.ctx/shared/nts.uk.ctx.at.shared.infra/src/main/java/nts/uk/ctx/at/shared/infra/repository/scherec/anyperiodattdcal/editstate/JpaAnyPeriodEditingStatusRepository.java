package nts.uk.ctx.at.shared.infra.repository.scherec.anyperiodattdcal.editstate;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal.editstate.AnyPeriodCorrectionEditingState;
import nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal.editstate.AnyPeriodEditingStateRepository;
import nts.uk.ctx.at.shared.infra.entity.scherec.anyperiodattdcal.editstate.KsrdtAnpEditState;
import nts.uk.ctx.at.shared.infra.entity.scherec.anyperiodattdcal.editstate.KsrdtAnpEditStatePk;

import javax.ejb.Stateless;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaAnyPeriodEditingStatusRepository extends JpaRepository implements AnyPeriodEditingStateRepository {
    @Override
    public void insert(AnyPeriodCorrectionEditingState state) {
        KsrdtAnpEditState entity = new KsrdtAnpEditState(
                new KsrdtAnpEditStatePk(
                        state.getEmployeeId(),
                        state.getAnyPeriodFrameCode().v(),
                        state.getAttendanceItemId()
                ),
                state.getState().value
        );
        this.commandProxy().insert(entity);
    }

    @Override
    public void update(AnyPeriodCorrectionEditingState state) {
        this.queryProxy()
                .find(new KsrdtAnpEditStatePk(state.getAnyPeriodFrameCode().v(), state.getEmployeeId(), state.getAttendanceItemId()), KsrdtAnpEditState.class)
                .ifPresent(entity -> this.updateEntity(entity, state));
    }

    private void updateEntity(KsrdtAnpEditState entity, AnyPeriodCorrectionEditingState state) {
        entity.editState = state.getState().value;
        this.commandProxy().update(entity);
    }

    @Override
    public void delete(String employeeId, String anyPeriodFrameCode) {
        List<KsrdtAnpEditState> entities = this.getEntitiesByEmployee(employeeId, anyPeriodFrameCode);
        this.commandProxy().removeAll(entities);
    }

    @Override
    public void persist(AnyPeriodCorrectionEditingState state) {
        Optional<KsrdtAnpEditState> existState = this.queryProxy()
                .find(new KsrdtAnpEditStatePk(state.getAnyPeriodFrameCode().v(), state.getEmployeeId(), state.getAttendanceItemId()), KsrdtAnpEditState.class);
        if (existState.isPresent()) {
            this.updateEntity(existState.get(), state);
        } else {
            this.insert(state);
        }
    }

    private final String QUERY_BY_LIST_EMPLOYEE = "select a from KsrdtAnpEditState a where a.pk.employeeId in :employeeIds and a.pk.frameCode = :frameCode";
    @Override
    public Map<String, List<AnyPeriodCorrectionEditingState>> getByListEmployee(String anyPeriodFrameCode, List<String> employeeIds) {
        if (CollectionUtil.isEmpty(employeeIds)) return Collections.emptyMap();
        List<AnyPeriodCorrectionEditingState> states = this.queryProxy().query(QUERY_BY_LIST_EMPLOYEE, KsrdtAnpEditState.class)
                .setParameter("employeeIds", employeeIds)
                .setParameter("frameCode", anyPeriodFrameCode)
                .getList(KsrdtAnpEditState::toDomain);
        return states.stream().collect(Collectors.groupingBy(AnyPeriodCorrectionEditingState::getEmployeeId));
    }

    @Override
    public List<AnyPeriodCorrectionEditingState> getByEmployee(String anyPeriodFrameCode, String employeeId) {
        return this.getEntitiesByEmployee(employeeId, anyPeriodFrameCode)
                .stream().map(KsrdtAnpEditState::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<AnyPeriodCorrectionEditingState> get(String anyPeriodFrameCode, String employeeId, int attendanceItemId) {
        return this.queryProxy()
                .find(new KsrdtAnpEditStatePk(employeeId, anyPeriodFrameCode, attendanceItemId), KsrdtAnpEditState.class)
                .map(KsrdtAnpEditState::toDomain);
    }

    private final String QUERY_BY_EMPLOYEE = "select a from KsrdtAnpEditState a where a.pk.employeeId = :employeeId and a.pk.frameCode = :frameCode";
    private List<KsrdtAnpEditState> getEntitiesByEmployee(String employeeId, String frameCode) {
        List<KsrdtAnpEditState> entities = this.queryProxy().query(QUERY_BY_EMPLOYEE, KsrdtAnpEditState.class)
                .setParameter("employeeIds", employeeId)
                .setParameter("frameCode", frameCode)
                .getList();
        return entities;
    }
}
