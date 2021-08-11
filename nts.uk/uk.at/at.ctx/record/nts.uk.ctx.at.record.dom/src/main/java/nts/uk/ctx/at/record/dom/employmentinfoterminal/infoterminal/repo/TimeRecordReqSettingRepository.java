package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * @author ThanhNX
 *
 *         就業情報端末のリクエスト一覧Repository
 */
public interface TimeRecordReqSettingRepository {

	/**
	 * 就業情報端末のリクエスト一覧を取得する
	 * 
	 * @param empInfoTerCode
	 * @return
	 */
	public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(EmpInfoTerminalCode empInfoTerCode,
			ContractCode contractCode);
	
	Optional<TimeRecordReqSetting> getTrRequest(EmpInfoTerminalCode terCode, ContractCode contractCode);
	
	Optional<TimeRecordReqSetting> getTimeRecordEmployee(EmpInfoTerminalCode terCode, ContractCode contractCode);
	
	Optional<TimeRecordReqSetting> getTimeRecordWorkType(EmpInfoTerminalCode terCode, ContractCode contractCode);
	
	Optional<TimeRecordReqSetting> getTimeRecordWorkTime(EmpInfoTerminalCode terCode, ContractCode contractCode);
	
	Optional<TimeRecordReqSetting> getTimeRecordReservation(EmpInfoTerminalCode terCode, ContractCode contractCode);

	/**
	 * 就業情報端末のリクエスト一覧を更新する
	 * 
	 * @param empInfoTerCode
	 * @param contractCode
	 */
	public void updateSetting(TimeRecordReqSetting setting);
    
    // [3]取得する
    List<TimeRecordReqSetting> get(ContractCode contractCode, List<EmpInfoTerminalCode> listCode);
    
    // [4]社員IDListを取得する
    List<EmployeeId> getEmployeeIdList(ContractCode contractCode, EmpInfoTerminalCode code);
    
    // [5]勤務種類コードListを取得する
    List<WorkTypeCode> getWorkTypeCodeList(ContractCode contractCode, EmpInfoTerminalCode code);
    
    // [6]就業時間帯コードListを取得する
    List<WorkTimeCode> getWorkTimeCodeList(ContractCode contractCode, EmpInfoTerminalCode code);
    
    // [7]弁当メニュー枠番Listを取得する
    List<Integer> getbentoMenuFrameNumbers(ContractCode contractCode, EmpInfoTerminalCode code);

    void insert(TimeRecordReqSetting reqSetting);
    
    void insert(EmpInfoTerminalCode terCode, ContractCode contractCode);
    
}
