package nts.uk.file.at.app.export.worktime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.task.AsyncTask;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class WorkTimeExportService extends ExportService<WorkTimeExportRequest> {

	@Inject
	private WorkTimeReportGenerator reportGenerator;

	@Inject
	private WorkTimeReportRepository reportRepository;
	
	@Inject
	private CompanyRepository companyRepository;

	@Override
	protected void handle(ExportServiceContext<WorkTimeExportRequest> context) {
		String cid = AppContexts.user().companyId();
		String companyName =  cid + " " + companyRepository.find(cid).map(i -> i.getCompanyName().v()).orElse("");
		String exportTime = GeneralDateTime.now().toString();

		List<Object[]> normal = Collections.synchronizedList(new ArrayList<>());
		List<Object[]> flow = Collections.synchronizedList(new ArrayList<>());
		List<Object[]> flex = Collections.synchronizedList(new ArrayList<>());
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		CountDownLatch countDownLatch = new CountDownLatch(3);
		AsyncTask taskNormal = AsyncTask.builder().withContexts().keepsTrack(true).threadName(this.getClass().getName())
				.build(() -> {
					try {
						normal.addAll(reportRepository.findWorkTimeNormal(cid));			
					} finally {
						countDownLatch.countDown();
					}
				});
		executorService.submit(taskNormal);

		AsyncTask taskFlow = AsyncTask.builder().withContexts().keepsTrack(true).threadName(this.getClass().getName())
				.build(() -> {
					try {
						flow.addAll(reportRepository.findWorkTimeFlow(cid));
					} finally {
						countDownLatch.countDown();
					}
				});
		executorService.submit(taskFlow);

		AsyncTask taskFlex = AsyncTask.builder().withContexts().keepsTrack(true).threadName(this.getClass().getName())
				.build(() -> {
					try {
						flex.addAll(reportRepository.findWorkTimeFlex(cid));
					} finally {
						countDownLatch.countDown();
					}
				});
		executorService.submit(taskFlex);

		try {
			countDownLatch.await();
			WorkTimeReportDatasource dataSource = new WorkTimeReportDatasource(companyName, exportTime, normal, flow, flex);
			reportGenerator.generate(context.getGeneratorContext(), dataSource);
		} catch (InterruptedException ie) {
			throw new RuntimeException(ie);
		} finally {
			// Force shut down executor services.
			executorService.shutdown();
		}
	}

}
