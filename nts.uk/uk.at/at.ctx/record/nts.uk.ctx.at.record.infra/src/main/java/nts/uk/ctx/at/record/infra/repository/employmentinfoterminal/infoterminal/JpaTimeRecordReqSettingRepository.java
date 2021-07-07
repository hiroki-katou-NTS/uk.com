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
import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting.ReqSettingBuilder;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.infoterminal.KrcmtTrRequest;
import nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.infoterminal.KrcmtTrRequestPK;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaTimeRecordReqSettingRepository extends JpaRepository implements TimeRecordReqSettingRepository {

	private static final String GET_WORKTYPE;
	private static final String GET_WORKTIME;
	private static final String GET_RESERVATION;
	private static final String GET_EMPLOYEE;
	
	private static final String GET_BY_MASTER_TYPE;
	private static final String GET_TR_REQUEST;

	private static final String GET_CONTRACTCD_LISTCODE = "SELECT m FROM KrcmtTrRequest m WHERE m.pk.contractCode = :contractCode AND m.pk.timeRecordCode IN :listCode";

	static {

		StringBuilder builderString = new StringBuilder();
		builderString.append(
				" SELECT b.WORKTYPE_CD FROM KRCMT_TR_SEND_WORKTYPE b ");
		builderString.append(" WHERE b.CONTRACT_CD = ? AND b.TIMERECORDER_CD = ?");
		GET_WORKTYPE = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append(
				" SELECT  b.WORKTIME_CD FROM KRCMT_TR_SEND_WORKTIME b ");
		builderString.append(" WHERE b.CONTRACT_CD = ? AND b.TIMERECORDER_CD = ?");
		GET_WORKTIME = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append(
				" SELECT  b.RESERVE_FRAME_NO FROM KRCMT_TR_SEND_RESERVATION b ");
		builderString.append(" WHERE b.CONTRACT_CD = ? AND b.TIMERECORDER_CD = ?");
		GET_RESERVATION = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append(
				" SELECT  b.SID FROM KRCMT_TR_SEND_EMPLOYEE b ");
		builderString.append(" WHERE b.CONTRACT_CD = ? AND b.TIMERECORDER_CD = ?");
		GET_EMPLOYEE = builderString.toString();
		
		StringBuilder getTrRequest = new StringBuilder();
		getTrRequest.append("SELECT a.CONTRACT_CD, a.CID, a.COMPANY_CD, a.TIMERECORDER_CD, a.SEND_OVERTIME_NAME,");
		getTrRequest.append(
				"a.SEND_SID, a.SEND_RESERVATION, a.SEND_WORKTYPE, SEND_WORKTIME, a.REMOTE_SETTING, a.REBOOT,  a.SEND_SWITCH_DATE, a.SWITCH_DATE, ");
		getTrRequest.append(
				"a.SEND_REASON_APP, a.SEND_SERVERTIME, a.RECV_ALL_STAMP, a.RECV_ALL_RESERVATION, a.RECV_ALL_APPLICATION");
		getTrRequest.append(" FROM KRCMT_TR_REQUEST a");
		getTrRequest.append(" WHERE a.CONTRACT_CD = ? AND a.TIMERECORDER_CD = ?");
		GET_TR_REQUEST = getTrRequest.toString();
		
		StringBuilder getByMasterTypeBuilder = new StringBuilder();
		getByMasterTypeBuilder.append("SELECT a.CONTRACT_CD, a.CID, a.COMPANY_CD, a.TIMERECORDER_CD, a.SEND_OVERTIME_NAME,");
		getByMasterTypeBuilder.append(
				"a.SEND_SID, a.SEND_RESERVATION, a.SEND_WORKTYPE, SEND_WORKTIME, a.REMOTE_SETTING, a.REBOOT, a.SEND_SWITCH_DATE, a.SWITCH_DATE, ");
		getByMasterTypeBuilder.append(
				"a.SEND_REASON_APP, a.SEND_SERVERTIME, a.RECV_ALL_STAMP, a.RECV_ALL_RESERVATION, a.RECV_ALL_APPLICATION");
		getByMasterTypeBuilder.append(", @MASTER_COLUMN");
		getByMasterTypeBuilder.append(" FROM KRCMT_TR_REQUEST a");
		getByMasterTypeBuilder.append(
				" LEFT JOIN @MASTER_TABLE b ON a.CONTRACT_CD = b.CONTRACT_CD AND a.TIMERECORDER_CD = b.TIMERECORDER_CD ");
		getByMasterTypeBuilder.append(" WHERE a.CONTRACT_CD = ? AND a.TIMERECORDER_CD = ?");
		GET_BY_MASTER_TYPE = getByMasterTypeBuilder.toString();
	}

	@Override
	public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(EmpInfoTerminalCode terCode,
			ContractCode contractCode) {
	
		TimeRecordReqSetting resultMaster = getTrRequest(terCode, contractCode).orElse(null);
		if (resultMaster != null) {
			resultMaster = new ReqSettingBuilder(
					resultMaster.getTerminalCode(), 
					resultMaster.getContractCode(), 
					resultMaster.getCompanyId(), 
					resultMaster.getCompanyCode(),
					getEmployeeId(terCode, contractCode), 
					getReservation(terCode, contractCode), 
					getWorkType(terCode, contractCode))
					.workTime(getWorkTime(terCode, contractCode))
					.overTimeHoliday(resultMaster.isOverTimeHoliday())
					.applicationReason(resultMaster.isApplicationReason())
					.stampReceive(resultMaster.isStampReceive())
					.reservationReceive(resultMaster.isReservationReceive())
					.applicationReceive(resultMaster.isApplicationReceive())
					.timeSetting(resultMaster.isTimeSetting())
					.sendEmployeeId(resultMaster.isSendEmployeeId())
					.sendBentoMenu(resultMaster.isSendBentoMenu())
					.sendWorkType(resultMaster.isSendWorkType())
					.sendWorkTime(resultMaster.isSendWorkTime())
					.remoteSetting(resultMaster.isRemoteSetting())
					.reboot(resultMaster.isReboot())
					.sendTimeSwitchUKMode(resultMaster.isSendTimeSwitchUKMode())
					.timeSwitchUKMode(resultMaster.getTimeSwitchUKMode())
					.build();
		}
		return Optional.ofNullable(resultMaster);
	}
	
	private List<WorkTypeCode> getWorkType(EmpInfoTerminalCode terCode,
			ContractCode contractCode){
		 List<WorkTypeCode> result = new ArrayList<>();
		try (PreparedStatement stm = this.connection().prepareStatement(GET_WORKTYPE)) {
			stm.setString(1, contractCode.v());
			stm.setString(2,  terCode.v());
			ResultSet rs = stm.executeQuery();
			 while(rs.next()) {
				 result.add(new WorkTypeCode(rs.getString("WORKTYPE_CD")));
			 };
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	private List<WorkTimeCode> getWorkTime(EmpInfoTerminalCode terCode,
			ContractCode contractCode){
		 List<WorkTimeCode> result = new ArrayList<>();
		try (PreparedStatement stm = this.connection().prepareStatement(GET_WORKTIME)) {
			stm.setString(1, contractCode.v());
			stm.setString(2,  terCode.v());
			ResultSet rs = stm.executeQuery();
			 while(rs.next()) {
				 result.add(new WorkTimeCode(rs.getString("WORKTIME_CD")));
			 };
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	private List<Integer> getReservation(EmpInfoTerminalCode terCode,
			ContractCode contractCode){
		 List<Integer> result = new ArrayList<>();
		try (PreparedStatement stm = this.connection().prepareStatement(GET_RESERVATION)) {
			stm.setString(1, contractCode.v());
			stm.setString(2,  terCode.v());
			ResultSet rs = stm.executeQuery();
			 while(rs.next()) {
				 result.add(Integer.parseInt((rs.getString("RESERVE_FRAME_NO"))));
			 };
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	private List<EmployeeId> getEmployeeId(EmpInfoTerminalCode terCode,
			ContractCode contractCode){
		 List<EmployeeId> result = new ArrayList<>();
		try (PreparedStatement stm = this.connection().prepareStatement(GET_EMPLOYEE)) {
			stm.setString(1, contractCode.v());
			stm.setString(2,  terCode.v());
			ResultSet rs = stm.executeQuery();
			 while(rs.next()) {
				 result.add(new EmployeeId(rs.getString("SID")));
			 };
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	@Override
	public Optional<TimeRecordReqSetting> getTrRequest(EmpInfoTerminalCode terCode, ContractCode contractCode) {
		try (PreparedStatement stm = this.connection().prepareStatement(GET_TR_REQUEST)) {
			stm.setString(1, contractCode.v());
			stm.setString(2,  terCode.v());
			List<TimeRecordReqSetting> listFullData = createTimeReqSettingResult(stm.executeQuery(), 0);
			if (listFullData.isEmpty()) return Optional.empty();
			return getOneByList(listFullData);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public Optional<TimeRecordReqSetting> getTimeRecordEmployee(EmpInfoTerminalCode terCode, ContractCode contractCode) {
		String queryStr = GET_BY_MASTER_TYPE.replaceFirst("@MASTER_COLUMN", "b.SID as EMPLOYEE")
							.replaceFirst("@MASTER_TABLE", "KRCMT_TR_SEND_EMPLOYEE"); 
		try (PreparedStatement stm = this.connection().prepareStatement(queryStr)) {
			stm.setString(1, contractCode.v());
			stm.setString(2,  terCode.v());
			List<TimeRecordReqSetting> listFullData = createTimeReqSettingResult(stm.executeQuery(), 1);
			if (listFullData.isEmpty()) return Optional.empty();
			return getOneByList(listFullData);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public Optional<TimeRecordReqSetting> getTimeRecordWorkType(EmpInfoTerminalCode terCode, ContractCode contractCode) {
		String queryStr = GET_BY_MASTER_TYPE.replaceFirst("@MASTER_COLUMN", "b.WORKTYPE_CD")
				.replaceFirst("@MASTER_TABLE", "KRCMT_TR_SEND_WORKTYPE"); 
		try (PreparedStatement stm = this.connection().prepareStatement(queryStr)) {
			stm.setString(1, contractCode.v());
			stm.setString(2,  terCode.v());
			List<TimeRecordReqSetting> listFullData = createTimeReqSettingResult(stm.executeQuery(), 2);
			if (listFullData.isEmpty()) return Optional.empty();
			return getOneByList(listFullData);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public Optional<TimeRecordReqSetting> getTimeRecordWorkTime(EmpInfoTerminalCode terCode, ContractCode contractCode) {
		String queryStr = GET_BY_MASTER_TYPE.replaceFirst("@MASTER_COLUMN", "b.WORKTIME_CD")
				.replaceFirst("@MASTER_TABLE", "KRCMT_TR_SEND_WORKTIME"); 
		try (PreparedStatement stm = this.connection().prepareStatement(queryStr)) {
			stm.setString(1, contractCode.v());
			stm.setString(2,  terCode.v());
			List<TimeRecordReqSetting> listFullData = createTimeReqSettingResult(stm.executeQuery(), 3);
			if (listFullData.isEmpty()) return Optional.empty();
			return getOneByList(listFullData);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public Optional<TimeRecordReqSetting> getTimeRecordReservation(EmpInfoTerminalCode terCode, ContractCode contractCode) {
		String queryStr = GET_BY_MASTER_TYPE.replaceFirst("@MASTER_COLUMN", "b.RESERVE_FRAME_NO")
				.replaceFirst("@MASTER_TABLE", "KRCMT_TR_SEND_RESERVATION"); 
		try (PreparedStatement stm = this.connection().prepareStatement(queryStr)) {
			stm.setString(1, contractCode.v());
			stm.setString(2,  terCode.v());
			List<TimeRecordReqSetting> listFullData = createTimeReqSettingResult(stm.executeQuery(), 4);
			if (listFullData.isEmpty()) return Optional.empty();
			return getOneByList(listFullData);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @param rs
	 * @param masterType
	 * 			1: employee,
	 * 			2: workType
	 * 			3: workTime
	 * 			4: reservation
	 * @return
	 */
	@SneakyThrows
	private List<TimeRecordReqSetting> createTimeReqSettingResult(ResultSet rs, int masterType) {
		List<TimeRecordReqSetting> listFullData = new ArrayList<>();
		while (rs.next()) {
			val reqBuilder = new TimeRecordReqSetting.ReqSettingBuilder(
					new EmpInfoTerminalCode(rs.getString("TIMERECORDER_CD")),
					new ContractCode(rs.getString("CONTRACT_CD")), new CompanyId(rs.getString("CID")),
					String.valueOf(rs.getString("COMPANY_CD")),
					masterType != 1 || rs.getString("EMPLOYEE") == null ? Collections.emptyList()
							: Arrays.asList(new EmployeeId(rs.getString("EMPLOYEE"))),
					masterType != 4 || rs.getString("RESERVE_FRAME_NO") == null ? Collections.emptyList()
							: Arrays.asList(Integer.parseInt(rs.getString("RESERVE_FRAME_NO"))),
					masterType != 2 || rs.getString("WORKTYPE_CD") == null ? Collections.emptyList()
							: Arrays.asList(new WorkTypeCode(rs.getString("WORKTYPE_CD"))))
									.overTimeHoliday(rs.getInt("SEND_OVERTIME_NAME") == 1)
									.applicationReason(rs.getInt("SEND_REASON_APP") == 1)
									.stampReceive(rs.getInt("RECV_ALL_STAMP") == 1)
									.reservationReceive(rs.getInt("RECV_ALL_RESERVATION") == 1)
									.applicationReceive(rs.getInt("RECV_ALL_APPLICATION") == 1)
									.timeSetting(rs.getInt("SEND_SERVERTIME") == 1)
									.sendEmployeeId(rs.getInt("SEND_SID") == 1)
									.sendBentoMenu(rs.getInt("SEND_RESERVATION") == 1)
									.sendWorkType(rs.getInt("SEND_WORKTYPE") == 1)
									.sendWorkTime(rs.getInt("SEND_WORKTIME") == 1)
									.remoteSetting(rs.getInt("REMOTE_SETTING") == 1).reboot(rs.getInt("REBOOT") == 1)
									.sendTimeSwitchUKMode(rs.getInt("SEND_SWITCH_DATE") == 1)
									.timeSwitchUKMode(rs.getTimestamp("SWITCH_DATE") == null ? Optional.empty()
											: Optional.of(
													GeneralDateTime.localDateTime(rs.getTimestamp("SWITCH_DATE").toLocalDateTime())));
			reqBuilder.workTime(masterType != 3 || rs.getString("WORKTIME_CD") == null 
					? Collections.emptyList() : Arrays.asList(new WorkTimeCode(rs.getString("WORKTIME_CD"))));
											
			TimeRecordReqSetting req = reqBuilder.build();
			listFullData.add(req);
		}

		if (listFullData.isEmpty()) {
			return Collections.emptyList();
		}
		
		return listFullData;
	}


	private Optional<TimeRecordReqSetting> getOneByList(List<TimeRecordReqSetting> listFullData) {
		if(listFullData.isEmpty()) {
			return Optional.empty();
		}
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
								.applicationReason(reqTemp.isApplicationReason())
								.stampReceive(reqTemp.isStampReceive())
								.reservationReceive(reqTemp.isReservationReceive())
								.applicationReceive(reqTemp.isApplicationReceive())
								.timeSetting(reqTemp.isTimeSetting())
								.sendEmployeeId(reqTemp.isSendEmployeeId())
								.sendWorkType(reqTemp.isSendWorkType())
								.sendWorkTime(reqTemp.isSendWorkTime())
								.sendBentoMenu(reqTemp.isSendBentoMenu())
								.reboot(reqTemp.isReboot())
								.remoteSetting(reqTemp.isRemoteSetting())
								.timeSwitchUKMode(reqTemp.getTimeSwitchUKMode())
								.sendTimeSwitchUKMode(reqTemp.isSendTimeSwitchUKMode())
								.build());
	}

	@Override
	public void updateSetting(TimeRecordReqSetting setting) {
		this.commandProxy().update(toEntity(setting));
	}

	private KrcmtTrRequest toEntity(TimeRecordReqSetting setting) {

		return new KrcmtTrRequest(new KrcmtTrRequestPK(setting.getContractCode().v(), setting.getTerminalCode().v()),
				setting.getCompanyId().v(), setting.getCompanyCode(), setting.isOverTimeHoliday() ? 1 : 0,
				setting.isApplicationReason() ? 1 : 0, setting.isTimeSetting() ? 1 : 0,
				setting.isSendEmployeeId() ? 1 : 0, setting.isSendBentoMenu() ? 1 : 0, setting.isSendWorkType() ? 1 : 0,
				setting.isSendWorkTime() ? 1 : 0, setting.isStampReceive() ? 1 : 0,
				setting.isReservationReceive() ? 1 : 0, setting.isApplicationReceive() ? 1 : 0,
				setting.isRemoteSetting() ? 1 : 0, setting.isReboot() ? 1 : 0, setting.isSendTimeSwitchUKMode()? 1 : 0, 
				setting.getTimeSwitchUKMode().orElse(null));
	}

	@Override
	public List<TimeRecordReqSetting> get(ContractCode contractCode, List<EmpInfoTerminalCode> listCode) {
		List<TimeRecordReqSetting> results = new ArrayList<>();
		List<KrcmtTrRequest> listEntity = this.queryProxy().query(GET_CONTRACTCD_LISTCODE, KrcmtTrRequest.class)
											.setParameter("contractCode", contractCode.v())
											.setParameter("listCode", listCode)
											.getList();
		if (listEntity.isEmpty()) {
			return Collections.emptyList();
		}
		results = listEntity.stream().map(e -> new TimeRecordReqSetting.ReqSettingBuilder(
												new EmpInfoTerminalCode(e.pk.timeRecordCode),
												new ContractCode(e.pk.contractCode), new CompanyId(e.cid),
												e.companyCode,
												Collections.emptyList(),
												Collections.emptyList(),
												Collections.emptyList())
													.workTime(Collections.emptyList())
													.overTimeHoliday(e.sendOverTime == 1)
													.applicationReason(e.sendReasonApp == 1)
													.stampReceive(e.recvStamp == 1)
													.reservationReceive(e.recvReservation == 1)
													.applicationReceive(e.recbApplication == 1)
													.timeSetting(e.sendServerTime == 1)
													.sendEmployeeId(e.sendSid == 1)
													.sendBentoMenu(e.sendReservation == 1)
													.sendWorkType(e.sendWorkType == 1)
													.sendWorkTime(e.sendWorkTime == 1)
													.remoteSetting(e.remoteSetting == 1).reboot(e.reboot == 1)
													.sendTimeSwitchUKMode(e.sendSwitchDate == 1)
													.timeSwitchUKMode(Optional.ofNullable(e.switchDate))
													.build())
									.collect(Collectors.toList());
		
		
		return results;
	}
//	CollectionUtil.split(listCode, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
//	results.addAll(getSubList(contractCode, subList));
//});	
//	private List<TimeRecordReqSetting> getSubList(ContractCode contractCode, List<EmpInfoTerminalCode> codes) {
//		String listCodes = codes.stream().map(e -> "?").collect(Collectors.joining(","));
//		GET_CONTRACTCD_LISTCODE += listCodes + ")" ;
//		System.out.println("----- " + JpaTimeRecordReqSettingRepository.GET_CONTRACTCD_LISTCODE);
//		
//		try (PreparedStatement statement = this.connection().prepareStatement(GET_CONTRACTCD_LISTCODE.toString())) {
//			statement.setString(1, contractCode.v());
//			for (int i = 0; i < codes.size(); i++) {
//				statement.setString(i + 2, codes.get(i).v());
//			}
//			return createTimeReqSetting(statement.executeQuery());
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		}
//	}

	@Override
	public List<EmployeeId> getEmployeeIdList(ContractCode contractCode, EmpInfoTerminalCode code) {
		Optional<TimeRecordReqSetting> timeRecordReqSetting = getTimeRecordEmployee(code, contractCode);
		if (!timeRecordReqSetting.isPresent()) {
			return Collections.emptyList();
		}
		return timeRecordReqSetting.get().getEmployeeIds();
	}

	@Override
	public List<WorkTypeCode> getWorkTypeCodeList(ContractCode contractCode, EmpInfoTerminalCode code) {
		Optional<TimeRecordReqSetting> timeRecordReqSetting = getTimeRecordWorkType(code, contractCode);
		if (!timeRecordReqSetting.isPresent()) {
			return Collections.emptyList();
		}
		return timeRecordReqSetting.get().getWorkTypeCodes();
	}

	@Override
	public List<WorkTimeCode> getWorkTimeCodeList(ContractCode contractCode, EmpInfoTerminalCode code) {
		Optional<TimeRecordReqSetting> timeRecordReqSetting = getTimeRecordWorkTime(code, contractCode);
		if (!timeRecordReqSetting.isPresent()) {
			return Collections.emptyList();
		}
		return timeRecordReqSetting.get().getWorkTimeCodes();
	}

	@Override
	public List<Integer> getbentoMenuFrameNumbers(ContractCode contractCode, EmpInfoTerminalCode code) {
		Optional<TimeRecordReqSetting> timeRecordReqSetting = getTimeRecordReservation(code, contractCode);
		if (!timeRecordReqSetting.isPresent()) {
			return Collections.emptyList();
		}
		return timeRecordReqSetting.get().getBentoMenuFrameNumbers();
	}
	
	@Override
	public void insert(TimeRecordReqSetting reqSetting) {
		this.commandProxy().insert(toEntity(reqSetting));
	}

	@Override
	public void insert(EmpInfoTerminalCode terCode, ContractCode contractCode) {
		
		String companyId   = AppContexts.user().companyId();
		String companyCode = AppContexts.user().companyCode();
		
		KrcmtTrRequest entity = new KrcmtTrRequest(
				new KrcmtTrRequestPK(contractCode.v(), terCode.v()),
				companyId, companyCode,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, null);
		this.commandProxy().insert(entity);
	}

}
