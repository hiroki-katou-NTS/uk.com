package nts.uk.ctx.at.function.app.find.processexecution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.function.app.command.processexecution.ProcessStartupCommand;
import nts.uk.ctx.at.function.app.find.processexecution.dto.AppDataInfoDailyDto;
import nts.uk.ctx.at.function.app.find.processexecution.dto.AppDataInfoMonthlyDto;
import nts.uk.ctx.at.function.app.find.processexecution.dto.ProcessStartupDto;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogHistRepository;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeAdapter;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance.AppDataInfoDailyRepository;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.monthlyperformance.AppDataInfoMonthlyRepository;

/**
 * UKDesign.UniversalK.就業.KBT_更新処理自動実行.KBT002_更新処理自動実行.I：承認中間データエラー一覧.アルゴリズム.I：起動時処理.I：起動時処理
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ProcessStartupFinder {

	@Inject
	private AppDataInfoMonthlyRepository appDataInfoMonthlyRepository;

	@Inject
	private AppDataInfoDailyRepository appDataInfoDailyRepository;

	@Inject
	private ProcessExecutionLogHistRepository processExecutionLogHistRepository;

	@Inject
	private EmployeeAdapter employeeAdapter;

	public ProcessStartupDto init(ProcessStartupCommand command) {
		ProcessStartupDto dto = new ProcessStartupDto();
		List<String> sids = new ArrayList<>();

		// パラメータ.実施内容を確認する
		if (command.isDaily()) {
			// 承認中間データ作成（日別実績）
			// ドメインモデル「承認中間データエラーメッセージ情報（月別実績）」を取得する
			dto.setDailyDtos(appDataInfoDailyRepository.getAppDataInfoDailyByExeID(command.getExecutionId()).stream()
					.map(AppDataInfoDailyDto::fromDomain)
					.sorted(Comparator.comparing(AppDataInfoDailyDto::getEmployeeId)).collect(Collectors.toList()));
			dto.getDailyDtos().forEach(item -> processExecutionLogHistRepository.getByExecId(item.getExecutionId())
					.ifPresent(hist -> hist.getLastExecDateTime().ifPresent(date -> item.setDate(date))));
			sids = dto.getDailyDtos().stream().map(AppDataInfoDailyDto::getEmployeeId).collect(Collectors.toList());

		} else {
			// 承認中間データ作成（月別実績）
			// ドメインモデル「承認中間データエラーメッセージ情報（日別実績）」を取得する
			dto.setMonthlyDtos(appDataInfoMonthlyRepository.getAppDataInfoMonthlyByExeID(command.getExecutionId())
					.stream().map(AppDataInfoMonthlyDto::fromDomain)
					.sorted(Comparator.comparing(AppDataInfoMonthlyDto::getEmployeeId)).collect(Collectors.toList()));
			dto.getMonthlyDtos().forEach(item -> processExecutionLogHistRepository.getByExecId(item.getExecutionId())
					.ifPresent(hist -> hist.getLastExecDateTime().ifPresent(date -> item.setDate(date))));
			sids = dto.getMonthlyDtos().stream().map(AppDataInfoMonthlyDto::getEmployeeId).collect(Collectors.toList());
		}
		// Imported「社員」を取得する
		dto.setEmployees(employeeAdapter.getByListSID(sids));
		return dto;
	}
}
