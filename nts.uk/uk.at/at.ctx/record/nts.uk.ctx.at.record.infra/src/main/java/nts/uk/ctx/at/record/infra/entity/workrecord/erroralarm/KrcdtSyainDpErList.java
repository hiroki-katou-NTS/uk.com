/**
 * 4:34:55 PM Aug 28, 2017
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * @author hungnm
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_SYAIN_DP_ER_LIST")
public class KrcdtSyainDpErList extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtSyainDpErListPK krcdtSyainDpErListPK;

	@Column(name = "ATTENDANCE_ITEM_ID")
	public Integer attendanceItemId;

	@Column(name = "ERROR_CANCELABLE")
	public Integer errorCancelable;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return this.krcdtSyainDpErListPK;
	}

	public static List<KrcdtSyainDpErList> toEntity(EmployeeDailyPerError employeeDailyPerError){
		 return employeeDailyPerError.getAttendanceItemList().stream().map(item -> {return new KrcdtSyainDpErList(
				new KrcdtSyainDpErListPK(employeeDailyPerError.getErrorAlarmWorkRecordCode().v(), 
						employeeDailyPerError.getEmployeeID(), 
						employeeDailyPerError.getDate(),
						employeeDailyPerError.getCompanyID()),
				         item,
						employeeDailyPerError.getErrorCancelAble());}).collect(Collectors.toList());
	}
}
