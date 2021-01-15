package nts.uk.ctx.at.request.infra.entity.application.holidaywork;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType_Update;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeApplicationSetting;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * Refactor5
 * @author huylq
 *
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "KRQDT_APP_HD_WORK_INPUT")
public class KrqdtHolidayWorkInput extends ContractUkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private KrqdtHolidayWorkInputPK krqdtHolidayWorkInputPK;
	
	@Column(name = "APPLICATION_TIME")
	public Integer applicationTime;
	
	@ManyToOne
    @PrimaryKeyJoinColumns({
    	@PrimaryKeyJoinColumn(name="CID", referencedColumnName="CID"),
    	@PrimaryKeyJoinColumn(name="APP_ID", referencedColumnName="APP_ID")
    })
	public KrqdtAppHolidayWork appHolidayWork;
	
	@Override
	protected Object getKey() {
		return krqdtHolidayWorkInputPK;
	}
	
	public OvertimeApplicationSetting toDomain() {
		if (getKey() == null) return null;
		return new OvertimeApplicationSetting(
				krqdtHolidayWorkInputPK.getFrameNo(),
				EnumAdaptor.valueOf(krqdtHolidayWorkInputPK.getAttendanceType(), AttendanceType_Update.class),
				applicationTime);
	}
}
