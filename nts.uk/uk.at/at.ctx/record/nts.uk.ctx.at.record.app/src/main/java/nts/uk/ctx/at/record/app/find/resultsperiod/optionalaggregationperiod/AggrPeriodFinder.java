package nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeAdapter;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeDto;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordAdapter;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordImport;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcution;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcutionRepository;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodInfor;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodInforRepository;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodTarget;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodTargetRepository;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriod;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriodRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class AggrPeriodFinder.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AggrPeriodFinder {
	
	/** The log repo. */
	@Inject
	private AggrPeriodExcutionRepository logRepo;
	
	/** The repository. */
	@Inject
	private AnyAggrPeriodRepository repository;

	/** The target repo. */
	@Inject
	private AggrPeriodTargetRepository targetRepo;

	/** The emp adapter. */
	@Inject
	private EmployeeRecordAdapter empRecordAdapter;

	/** The error info repo. */
	@Inject
	private AggrPeriodInforRepository errorInfoRepo;
	
	@Inject
	private EmployeeAdapter empAdapter;
	
	/**
	 * Gets the aggr period.
	 *
	 * @param excuteId the excute id
	 * @return the aggr period
	 */
	public AggrPeriodDto getAggrPeriod(String excuteId) {
		// 1. ドメインモデル「任意期間集計実行ログ」を取得する
		AggrPeriodExcutionDto aggrPeriodExcutionDto = this.findAggr(excuteId);
		// 2. ドメインモデル「任意集計期間」を取得する
		AnyAggrPeriodDto anyAggrPeriodDto = this.find(excuteId);
		// 3. ドメインモデル「任意期間集計対象人数」を取得する
		List<AggrPeriodTargetDto> periodTargetDto = this.findAll(aggrPeriodExcutionDto.getAggrFrameCode());
		// 4. ドメインモデル「エラーメッセージ情報」を取得する
		List<AggrPeriodErrorInfoDto> errorInfos = this.findAllErrorInfos(excuteId);
		// 5. Imported「社員」を取得する
		List<String> ids = errorInfos.stream()
				.map(AggrPeriodErrorInfoDto::getEmployeeId)
				.collect(Collectors.toList());
		List<EmployeeDto> listEmpDtos = this.empAdapter.getByListSID(ids);
		return new AggrPeriodDto(aggrPeriodExcutionDto, anyAggrPeriodDto, periodTargetDto, errorInfos, listEmpDtos);
	}
	
	/**
	 * Find aggr.
	 *
	 * @param aggrFrameCode the aggr frame code
	 * @return the aggr period excution dto
	 */
	public AggrPeriodExcutionDto findAggr(String aggrFrameCode){
		String companyId = AppContexts.user().companyId();
		String executionEmpId = AppContexts.user().employeeId();
		Optional<AggrPeriodExcution> data = this.logRepo.findExecution(companyId, executionEmpId, aggrFrameCode);

		if (data.isPresent()) {
			return AggrPeriodExcutionDto.fromDomain(data.get());
		}

		return new AggrPeriodExcutionDto();
		
	}
	
	/**
	 * Find.
	 *
	 * @param aggrFrameCode the aggr frame code
	 * @return the optional aggr period dto
	 */
	public AnyAggrPeriodDto find(String aggrFrameCode) {
		String companyId = AppContexts.user().companyId();
		AnyAggrPeriod domain = this.repository.findOne(companyId, aggrFrameCode).orElse(null);
		return AnyAggrPeriodDto.createFromDomain(domain);
	}
	
	/**
	 * Find all.
	 *
	 * @param aggrPeriodId the aggr period id
	 * @return the list
	 */
	public List<AggrPeriodTargetDto> findAll(String aggrPeriodId) {
		List<AggrPeriodTargetDto> result = new ArrayList<>();
		List<AggrPeriodTarget> listTarget = this.targetRepo.findAll(aggrPeriodId);
		List<String> listEmployeeId = listTarget.stream().map(l -> l.getEmployeeId()).collect(Collectors.toList());
		List<EmployeeRecordImport> lstEmpInfo = this.empRecordAdapter.getPersonInfor(listEmployeeId);
		for (AggrPeriodTarget a : listTarget) {
			EmployeeRecordImport empInfo = lstEmpInfo.stream().filter(e -> e.getEmployeeId().equals(a.getEmployeeId())).collect(Collectors.toList()).get(0);
			AggrPeriodTargetDto dto = new AggrPeriodTargetDto(a.getEmployeeId(), empInfo.getEmployeeCode(),
					empInfo.getPname(), a.getState().name);
			result.add(dto);
		}
		result.sort((AggrPeriodTargetDto c1, AggrPeriodTargetDto c2) -> c1.getEmployeeCode()
				.compareTo(c2.getEmployeeCode()));
		return result;
	}
	
	/**
	 * Find all error infos.
	 *
	 * @param aggrPeriodId the aggr period id
	 * @return the list
	 */
	public List<AggrPeriodErrorInfoDto> findAllErrorInfos(String aggrPeriodId) {
		List<AggrPeriodErrorInfoDto> result = new ArrayList<>();
		List<AggrPeriodInfor> listErr = this.errorInfoRepo.findAll(aggrPeriodId);
		List<String> listEmployeeId = listErr.stream().map(l -> l.getMemberId()).collect(Collectors.toList());
		List<EmployeeRecordImport> lstEmpInfo = this.empRecordAdapter.getPersonInfor(listEmployeeId);
		for (AggrPeriodInfor err : listErr) {
			EmployeeRecordImport empInfo = lstEmpInfo.stream().filter(e -> e.getEmployeeId().equals(err.getMemberId())).collect(Collectors.toList()).get(0);
			AggrPeriodErrorInfoDto dto = new AggrPeriodErrorInfoDto(err.getMemberId(), empInfo.getEmployeeCode(),
					empInfo.getPname(), err.getProcessDay(), err.getErrorMess().v());
			result.add(dto);
		}
		result.sort((AggrPeriodErrorInfoDto c1, AggrPeriodErrorInfoDto c2) -> c1.getEmployeeCode()
				.compareTo(c2.getEmployeeCode()));
		return result;
	}
}
