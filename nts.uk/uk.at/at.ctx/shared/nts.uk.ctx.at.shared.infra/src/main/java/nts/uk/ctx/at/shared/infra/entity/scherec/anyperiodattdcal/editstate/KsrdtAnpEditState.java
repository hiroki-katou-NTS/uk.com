package nts.uk.ctx.at.shared.infra.entity.scherec.anyperiodattdcal.editstate;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal.editstate.AnyPeriodCorrectionEditingState;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.anyaggrperiod.AnyAggrFrameCode;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.editstate.StateOfEditMonthly;
import nts.uk.shr.infra.data.entity.ContractCompanyUkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "KSRDT_ANP_EDIT_STATE")
@AllArgsConstructor
@NoArgsConstructor
public class KsrdtAnpEditState extends ContractCompanyUkJpaEntity {
    @EmbeddedId
    public KsrdtAnpEditStatePk pk;

    @Column(name = "STATE_OF_EDIT")
    public int editState;

    @Override
    protected Object getKey() {
        return pk;
    }

    public AnyPeriodCorrectionEditingState toDomain() {
        return new AnyPeriodCorrectionEditingState(
                this.pk.employeeId,
                new AnyAggrFrameCode(this.pk.frameCode),
                this.pk.attendanceItemId,
                EnumAdaptor.valueOf(this.editState, StateOfEditMonthly.class)
        );
    }
}
