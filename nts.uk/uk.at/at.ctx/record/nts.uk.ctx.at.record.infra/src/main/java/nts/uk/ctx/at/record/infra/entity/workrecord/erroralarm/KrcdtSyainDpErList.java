/**
 * 4:34:55 PM Aug 28, 2017
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
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

	@Id
	@Column(name = "ID")
	public String id;

	@Column(name = "ERROR_CODE")
	public String errorCode;

	@Column(name = "SID")
	public String employeeId;

	@Column(name = "PROCESSING_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate processingDate;

	@Column(name = "CID")
	public String companyID;

	@Column(name = "ERROR_CANCELABLE")
	public Integer errorCancelable;

	@Column(name = "ERROR_MESSAGE")
	public String errorAlarmMessage;

	@OneToMany(mappedBy = "erAttendanceItem", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<KrcdtErAttendanceItem> erAttendanceItem;
	
	@Transient
	public KrcdtErAttendanceItem krcdtErAttendanceItem;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return this.id;
	}

	public KrcdtSyainDpErList(String id, String errorCode, String employeeId, GeneralDate processingDate,
			String companyID, Integer errorCancelable, String errorAlarmMessage,
			List<KrcdtErAttendanceItem> erAttendanceItem) {
		super();
		this.id = id;
		this.errorCode = errorCode;
		this.employeeId = employeeId;
		this.processingDate = processingDate;
		this.companyID = companyID;
		this.errorCancelable = errorCancelable;
		this.errorAlarmMessage = errorAlarmMessage;
		this.erAttendanceItem = erAttendanceItem;
	}

	public static KrcdtSyainDpErList toEntity(EmployeeDailyPerError employeeDailyPerError) {
		String id = IdentifierUtil.randomUniqueId();
		return new KrcdtSyainDpErList(id, employeeDailyPerError.getErrorAlarmWorkRecordCode().v(),
				employeeDailyPerError.getEmployeeID(), employeeDailyPerError.getDate(),
				employeeDailyPerError.getCompanyID(), employeeDailyPerError.getErrorCancelAble(),
				employeeDailyPerError.getErrorAlarmMessage().isPresent()
						? employeeDailyPerError.getErrorAlarmMessage().get().v() : null,
				employeeDailyPerError.getAttendanceItemList().stream()
						.map(item -> KrcdtErAttendanceItem.toEntity(id, item)).collect(Collectors.toList()));
	}

	public EmployeeDailyPerError toDomain() {
		return new EmployeeDailyPerError(this.companyID, this.employeeId,
				this.processingDate, this.errorCode, erAttendanceItem.stream()
						.map(c -> c.krcdtErAttendanceItemPK.attendanceItemId).collect(Collectors.toList()),
				this.errorCancelable, this.errorAlarmMessage);
	}

	public static EmployeeDailyPerError toDomainForRes(List<KrcdtSyainDpErList> entities) {
		return new EmployeeDailyPerError(entities.get(0).companyID, entities.get(0).employeeId,
				entities.get(0).processingDate, entities.get(0).errorCode, entities.stream()
						.filter(item -> item.krcdtErAttendanceItem != null)
						.map(c -> c.krcdtErAttendanceItem.krcdtErAttendanceItemPK.attendanceItemId).collect(Collectors.toList()),
						entities.get(0).errorCancelable, entities.get(0).errorAlarmMessage);
	}
}
