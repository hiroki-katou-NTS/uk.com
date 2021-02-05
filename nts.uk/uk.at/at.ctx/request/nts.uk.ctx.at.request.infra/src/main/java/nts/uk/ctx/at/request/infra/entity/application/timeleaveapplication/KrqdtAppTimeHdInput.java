package nts.uk.ctx.at.request.infra.entity.application.timeleaveapplication;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "KRQDT_APP_TIME_HD_INPUT")
public class KrqdtAppTimeHdInput extends ContractUkJpaEntity {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    public KrqdtAppTimeHdInputPK pk;

    @Column(name = "HOUR_OF_SIXTY_OVERTIME")
    public Integer sixtyHOvertime;

    @Column(name = "HOUR_OF_CARE")
    public Integer nursingTime;

    @Column(name = "HOUR_OF_CHILD_CARE")
    public Integer childNursingTime;

    @Column(name = "HOUR_OF_HDCOM")
    public Integer hoursOfSubHoliday;

    @Column(name = "HOUR_OF_HDPAID")
    public Integer hoursOfHoliday;

    @Column(name = "HOUR_OF_HDSP")
    public Integer timeSpecialVacation;

    @Column(name = "FRAME_NO_OF_HDSP")
    public Integer specialHdFrameNo;

    @Override
    protected Object getKey() {
        return this.pk;
    }

}
