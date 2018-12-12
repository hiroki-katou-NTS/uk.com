package nts.uk.shr.infra.file.report.masterlist;

import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

import lombok.SneakyThrows;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.infra.file.report.masterlist.annotation.NamedAnnotation;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.generator.MasterListExportSource;
import nts.uk.shr.infra.file.report.masterlist.generator.MasterListReportGenerator;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;
import nts.uk.shr.infra.i18n.loading.LanguageMasterRepository;

@Stateless
public class MasterListExportService extends ExportService<MasterListExportQuery> {

	@Inject
	private MasterListReportGenerator generator;

	@Inject
	private LanguageMasterRepository languageRepo;

	@Inject
	private CompanyAdapter company;

	@Override
	@SneakyThrows
	protected void handle(ExportServiceContext<MasterListExportQuery> context) {

		MasterListExportQuery query = context.getQuery();

		MasterListData domainData = CDI.current().select(MasterListData.class, new NamedAnnotation(query.getDomainId())).get();

		Map<String, String> headers = this.getHeaderInfor(query);

		this.generator.generate(context.getGeneratorContext(), new MasterListExportSource(headers, query.getReportType(), domainData, query));
	}

	private Map<String, String> getHeaderInfor(MasterListExportQuery query) {
		Map<String, String> headers = new LinkedHashMap<>();

		LoginUserContext context = AppContexts.user();
		String companyname = this.company.getCurrentCompany()
				.orElseThrow(() -> new RuntimeException("Company is not found!!!!")).getCompanyName();

		String createReportDate = GeneralDateTime.now().localDateTime()
				.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
		String language = languageRepo.getSystemLanguage(query.getLanguageId()).get().getLanguageName();
		headers.put("【会社】", context.companyCode() + " " + companyname);
		headers.put("【種類】", query.getDomainType());
		headers.put("【日時】", createReportDate);
		headers.put("【選択言語】 ", language);

		return headers;
	}

}
