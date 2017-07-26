package nts.uk.ctx.at.shared.infra.entity.attendanceitem;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.infra.data.query.DBCharPaddingAs;
import nts.uk.ctx.at.shared.dom.attendanceitem.primitives.BusinessTypeCode;
@Setter
@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KdwstDAIControlOfAttendanceItemsPK implements Serializable {

	private static final long serialVersionUID = 1L;
	@DBCharPaddingAs(BusinessTypeCode.class)
	@Column(name = "BUSINESS_TYPE_CD")
	public String businessTypeCode;
	@Column(name = "ATTENDANCE_ITEM_ID")
	public String attendanceItemId;
}
