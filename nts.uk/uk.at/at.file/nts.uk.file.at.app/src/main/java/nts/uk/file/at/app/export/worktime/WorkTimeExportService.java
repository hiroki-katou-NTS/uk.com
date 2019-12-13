package nts.uk.file.at.app.export.worktime;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class WorkTimeExportService extends ExportService<WorkTimExportDto> {

	private static final String COMPANY_ERROR = "Company is not found!!!!";

	@Inject
	private WorkTimeReportGenerator reportGenerator;

	@Inject
	private WorkTimeReportRepository reportRepository;

	@Inject
	private CompanyAdapter company;

	@Override
	protected void handle(ExportServiceContext<WorkTimExportDto> context) {
		LoginUserContext user = AppContexts.user();
		WorkTimExportDto workTimExportDto = context.getQuery();
		String programName = workTimExportDto.getProgramName();
		String langId = workTimExportDto.getLangId();
		String cid = user.companyId();
		String companyName = user.companyCode() + " "
				+ company.getCurrentCompany().orElseThrow(() -> new RuntimeException(COMPANY_ERROR)).getCompanyName();

		List<Object[]> normal = new ArrayList<>();
		List<Object[]> flow = new ArrayList<>();
		List<Object[]> flex = new ArrayList<>();
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		CountDownLatch countDownLatch = new CountDownLatch(3);

		executorService.submit(() -> {
			try {
				normal.addAll(reportRepository.findWorkTimeNormal(cid, langId));
			} finally {
				countDownLatch.countDown();
			}
		});

		executorService.submit(() -> {
			try {
				flow.addAll(reportRepository.findWorkTimeFlow(cid, langId));
			} finally {
				countDownLatch.countDown();
			}
		});

		executorService.submit(() -> {
			try {
				flex.addAll(reportRepository.findWorkTimeFlex(cid, langId));
			} finally {
				countDownLatch.countDown();
			}
		});

		try {
			countDownLatch.await();
		} catch (InterruptedException ie) {
			throw new RuntimeException(ie);
		} finally {
			executorService.shutdown();
		}
		val dataSource = new WorkTimeReportDatasource(programName, companyName, GeneralDateTime.now(), normal, flow, flex);
		reportGenerator.generate(context.getGeneratorContext(), dataSource);
	}

}
