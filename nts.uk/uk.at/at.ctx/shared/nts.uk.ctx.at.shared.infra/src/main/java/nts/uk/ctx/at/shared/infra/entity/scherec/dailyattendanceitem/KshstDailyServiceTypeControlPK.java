package nts.uk.ctx.at.shared.infra.entity.scherec.dailyattendanceitem;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.infra.data.query.DBCharPaddingAs;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.primitivevalue.BusinessTypeCode;
@Setter
@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KshstDailyServiceTypeControlPK implements Serializable {

	private static final long serialVersionUID = 1L;
	@DBCharPaddingAs(BusinessTypeCode.class)
	@Column(name = "BUSINESS_TYPE_CD")
	public String businessTypeCode;
	@Column(name = "ATTENDANCE_ITEM_ID")
	public BigDecimal attendanceItemId;
}
