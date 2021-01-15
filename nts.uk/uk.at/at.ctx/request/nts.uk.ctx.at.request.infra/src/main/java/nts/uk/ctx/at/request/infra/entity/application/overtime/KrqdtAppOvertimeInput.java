package nts.uk.ctx.at.request.infra.entity.application.overtime;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType_Update;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeApplicationSetting;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * Refactor5
 * @author hoangnd
 *
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "KRQDT_APP_OVERTIME_INPUT")
public class KrqdtOvertimeInput extends ContractUkJpaEntity implements Serializable{
	
	public static final long serialVersionUID = 1L;
	
	@EmbeddedId
    public KrqdtOvertimeInputPK krqdtOvertimeInputPK;
	
	
	@Column(name = "APPLICATION_TIME")
	public Integer applicationTime;
	
	@ManyToOne
    @PrimaryKeyJoinColumns({
    	@PrimaryKeyJoinColumn(name="CID", referencedColumnName="CID"),
    	@PrimaryKeyJoinColumn(name="APP_ID", referencedColumnName="APP_ID")
    })
	public KrqdtAppOverTime appOvertime;
	
	@Override
	protected Object getKey() {
		return krqdtOvertimeInputPK;
	}
	
	public OvertimeApplicationSetting toDomain() {
		if (getKey() == null) return null;
		return new OvertimeApplicationSetting(
				krqdtOvertimeInputPK.getFrameNo(),
				EnumAdaptor.valueOf(krqdtOvertimeInputPK.getAttendanceType(), AttendanceType_Update.class),
				applicationTime);
	}

}
