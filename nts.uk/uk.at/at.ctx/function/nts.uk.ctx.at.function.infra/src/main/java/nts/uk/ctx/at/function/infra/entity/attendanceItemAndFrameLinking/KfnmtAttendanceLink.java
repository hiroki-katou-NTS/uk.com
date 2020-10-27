package nts.uk.ctx.at.function.infra.entity.attendanceItemAndFrameLinking;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.attendanceitemframelinking.AttendanceItemLinking;
import nts.uk.ctx.at.function.dom.attendanceitemframelinking.enums.FrameCategory;
import nts.uk.ctx.at.function.dom.attendanceitemframelinking.enums.TypeOfItem;
import nts.uk.ctx.at.function.dom.attendanceitemframelinking.primitivevalue.FrameNo;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "KFNMT_ATTENDANCE_LINK")
@NoArgsConstructor
@Setter
public class KfnmtAttendanceLink extends ContractUkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/*主キー*/
	@EmbeddedId
	public KfnmtAttendanceLinkPK kfnmtAttendanceLinkPK;
	
	@Column(name = "PRELIMINARY_FRAME_NO", nullable = true)
	public Integer preliminaryFrameNO;
	
	@Override
	protected Object getKey() {
		return kfnmtAttendanceLinkPK;
	}

	public KfnmtAttendanceLink(KfnmtAttendanceLinkPK kfnmtAttendanceLinkPK) {
		super();
		this.kfnmtAttendanceLinkPK = kfnmtAttendanceLinkPK;
	}

	public KfnmtAttendanceLink(KfnmtAttendanceLinkPK kfnmtAttendanceLinkPK, Integer preliminaryFrameNO) {
		super();
		this.kfnmtAttendanceLinkPK = kfnmtAttendanceLinkPK;
		this.preliminaryFrameNO = preliminaryFrameNO;
	}
	
	public static KfnmtAttendanceLink toEntity(AttendanceItemLinking domain ) {
		return new KfnmtAttendanceLink(
					new KfnmtAttendanceLinkPK(
						domain.getAttendanceItemId(),
						BigDecimal.valueOf(domain.getFrameNo().v()),
						BigDecimal.valueOf(domain.getTypeOfAttendanceItem().value),
						BigDecimal.valueOf(domain.getFrameCategory().value)),
				domain.getPreliminaryFrameNO().isPresent()?domain.getPreliminaryFrameNO().get().v():null
				);
	}
	
	public AttendanceItemLinking toDomain() {
		return new AttendanceItemLinking(
				this.kfnmtAttendanceLinkPK.attendanceItemId,
				new FrameNo(this.kfnmtAttendanceLinkPK.frameNo.intValue()),
				EnumAdaptor.valueOf(this.kfnmtAttendanceLinkPK.typeOfItem.intValue(),TypeOfItem.class),
				EnumAdaptor.valueOf(this.kfnmtAttendanceLinkPK.frameCategory.intValue(),FrameCategory.class),
				this.preliminaryFrameNO==null?null:new FrameNo(this.preliminaryFrameNO.intValue())
				);
	}
}
