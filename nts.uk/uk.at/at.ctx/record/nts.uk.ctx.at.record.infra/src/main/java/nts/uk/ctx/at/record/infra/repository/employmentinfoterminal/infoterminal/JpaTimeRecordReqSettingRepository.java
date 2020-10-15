package nts.uk.ctx.at.record.infra.repository.employmentinfoterminal.infoterminal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaTimeRecordReqSettingRepository extends JpaRepository implements TimeRecordReqSettingRepository {

	private static final String GET_BY_KEY;

	static {

		StringBuilder builderString = new StringBuilder();
		builderString
				.append("SELECT a.CONTRACT_CD, a.CID, a.COMPANY_CD, a.TIMERECORDER_CD, a.SID, a.SEND_OVERTIME_NAME,");
		builderString.append(
				"a.SEND_REASON_APP, a.SEND_SERVERTIME, a.RECV_ALL_STAMP, a.RECV_ALL_RESERVATION, a.RECV_ALL_APPLICATION,");
		builderString.append("b.WORKTYPE_CD, c.WORKTIME_CD, d.RESERVE_FRAME_NO, e.SID as EMPLOYEE");
		builderString.append(" FROM KRCMT_TR_REQUEST a");
		builderString.append(
				" LEFT JOIN KRCMT_TR_SEND_WORKTYPE b ON a.CONTRACT_CD = b.CONTRACT_CD AND a.TIMERECORDER_CD = b.TIMERECORDER_CD ");

		builderString.append(
				" LEFT JOIN KRCMT_TR_SEND_WORKTIME c ON a.CONTRACT_CD = c.CONTRACT_CD AND a.TIMERECORDER_CD = c.TIMERECORDER_CD ");

		builderString.append(
				" LEFT JOIN KRCMT_TR_SEND_RESERVATION d ON a.CONTRACT_CD = d.CONTRACT_CD AND a.TIMERECORDER_CD = d.TIMERECORDER_CD ");

		builderString.append(
				" LEFT JOIN KRCMT_TR_SEND_EMPLOYEE e ON a.CONTRACT_CD = e.CONTRACT_CD AND a.TIMERECORDER_CD = e.TIMERECORDER_CD ");

		builderString.append(" WHERE a.CONTRACT_CD = ? AND a.TIMERECORDER_CD = ?");
		GET_BY_KEY = builderString.toString();

	}

	@Override
	public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(EmpInfoTerminalCode empInfoTerCode,
			ContractCode contractCode) {
		try (PreparedStatement statement = this.connection().prepareStatement(GET_BY_KEY)) {
			statement.setString(1, contractCode.v());
			statement.setInt(2, empInfoTerCode.v());
			return createTimeReqSetting(statement.executeQuery());

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@SneakyThrows
	private Optional<TimeRecordReqSetting> createTimeReqSetting(ResultSet rs) {
		List<TimeRecordReqSetting> listFullData = new ArrayList<>();
		while (rs.next()) {
			TimeRecordReqSetting req = new TimeRecordReqSetting.ReqSettingBuilder(
					new EmpInfoTerminalCode(rs.getInt("TIMERECORDER_CD")),
					new ContractCode(rs.getString("CONTRACT_CD")), new CompanyId(rs.getString("CID")),
					String.valueOf(rs.getInt("COMPANY_CD")),
					rs.getString("EMPLOYEE") == null ? Collections.emptyList()
							: Arrays.asList(new EmployeeId(rs.getString("EMPLOYEE"))),
					rs.getString("RESERVE_FRAME_NO") == null ? null
							: Arrays.asList(Integer.parseInt(rs.getString("RESERVE_FRAME_NO"))),
					rs.getString("WORKTYPE_CD") == null ? Collections.emptyList()
							: Arrays.asList(new WorkTypeCode(rs.getString("WORKTYPE_CD"))))
									.workTime(rs.getString("WORKTIME_CD") == null ? Collections.emptyList()
											: Arrays.asList(new WorkTimeCode(rs.getString("WORKTIME_CD"))))
									.overTimeHoliday(rs.getInt("SEND_OVERTIME_NAME") == 1)
									.applicationReason(rs.getInt("SEND_REASON_APP") == 1)
									.stampReceive(rs.getInt("RECV_ALL_STAMP") == 1)
									.reservationReceive(rs.getInt("RECV_ALL_RESERVATION") == 1)
									.applicationReceive(rs.getInt("RECV_ALL_APPLICATION") == 1)
									.timeSetting(rs.getInt("SEND_SERVERTIME") == 1).build();
			listFullData.add(req);
		}

		if (listFullData.isEmpty())
			return Optional.empty();

		List<WorkTimeCode> wTimeCode = listFullData.stream().flatMap(x -> x.getWorkTimeCodes().stream()).distinct()
				.collect(Collectors.toList());
		List<WorkTypeCode> wTypeCode = listFullData.stream().flatMap(x -> x.getWorkTypeCodes().stream()).distinct()
				.collect(Collectors.toList());
		List<EmployeeId> employeeIds = listFullData.stream().flatMap(x -> x.getEmployeeIds().stream()).distinct()
				.collect(Collectors.toList());
		List<Integer> reservation = listFullData.stream().flatMap(x -> x.getBentoMenuFrameNumbers().stream()).distinct()
				.collect(Collectors.toList());

		TimeRecordReqSetting reqTemp = listFullData.get(0);
		return Optional
				.of(new TimeRecordReqSetting.ReqSettingBuilder(reqTemp.getTerminalCode(), reqTemp.getContractCode(),
						reqTemp.getCompanyId(), reqTemp.getCompanyCode(), employeeIds, reservation, wTypeCode)
								.workTime(wTimeCode).overTimeHoliday(reqTemp.isOverTimeHoliday())
								.applicationReason(reqTemp.isApplicationReason()).stampReceive(reqTemp.isStampReceive())
								.reservationReceive(reqTemp.isReservationReceive())
								.applicationReceive(reqTemp.isApplicationReceive()).timeSetting(reqTemp.isTimeSetting())
								.build());
	}

}
