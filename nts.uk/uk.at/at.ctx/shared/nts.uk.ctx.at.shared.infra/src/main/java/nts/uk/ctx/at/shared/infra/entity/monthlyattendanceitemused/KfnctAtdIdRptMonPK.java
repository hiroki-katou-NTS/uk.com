package nts.uk.ctx.at.shared.infra.entity.monthlyattendanceitemused;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
@Data
public class KfnctAtdIdRptMonPK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/*会社ID*/
	@Column(name = "CID")
	private String companyId;
	
	/*勤怠項目ID*/
	@Column(name = "ATTENDANCE_ITEM_ID")
	private int attendanceItemId;

}