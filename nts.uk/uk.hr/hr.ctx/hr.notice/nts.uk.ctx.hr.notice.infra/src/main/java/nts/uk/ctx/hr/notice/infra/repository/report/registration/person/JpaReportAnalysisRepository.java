package nts.uk.ctx.hr.notice.infra.repository.report.registration.person;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ReportAnalysis;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ReportAnalysisRepository;
import nts.uk.ctx.hr.notice.infra.entity.report.registration.person.JhndtRptAnalysis;
import nts.uk.ctx.hr.notice.infra.entity.report.registration.person.JhndtRptAnalysisPk;
@Stateless
public class JpaReportAnalysisRepository extends JpaRepository implements ReportAnalysisRepository {

	private static final String SEL_BY_CID_AND_COUNT_CLS_AND_COUNT_CLS_SMALL = "SELECT c FROM  JhndtRptAnalysis c "
			
			+ "WHERE c.pk.cid = :cid AND c.pk.countClass = :countClass AND c.pk.countClassSmall = :countClassSmall";

//	@Override
//	public List<ReportAnalysis> getListReportAnalysis(String cid, int countClass, int countClassSmall) {
//
//		return this.queryProxy().query(SEL_BY_CID_AND_COUNT_CLS_AND_COUNT_CLS_SMALL, JhndtRptAnalysis.class)
//				
//				.setParameter("cid", cid).setParameter("countClass", countClass)	
//				
//				.setParameter("countClassSmall", countClassSmall).getList(c -> toDomain(c));
//	}

	private ReportAnalysis toDomain(JhndtRptAnalysis entity) {
		
		return new ReportAnalysis(entity.pk.cid, entity.pk.reportYearMonth, 
				
				entity.pk.reportLayoutID,entity.pk.countClass, 
				
				entity.pk.countClassSmall, entity.reportCount);
		
	}

	private JhndtRptAnalysis toEntity(ReportAnalysis domain) {
		
		return new JhndtRptAnalysis(
				
				new JhndtRptAnalysisPk(domain.getReportLayoutID(), domain.getReportYearMonth(),
						
					domain.getCountClass(), domain.getCountClassSmall(), domain.getCid()),
				
				domain.getReportCount());
	}

	@Override
	public void updateAll(List<ReportAnalysis> domains) {

		List<JhndtRptAnalysis> entities = domains.stream().map(c -> {
			
			return toEntity(c);
			
		}).collect(Collectors.toList());

		this.commandProxy().updateAll(entities);

	}

	@Override
	public Optional<ReportAnalysis> getListReportAnalysis(String cid, int reportLayoutID, int countClass,
			
			int countClassSmall, int reportYearMonth) {
		
		Optional<JhndtRptAnalysis> entityOpt = this.queryProxy().find(new JhndtRptAnalysisPk(reportLayoutID, reportYearMonth,
						
				countClass, countClassSmall, cid), JhndtRptAnalysis.class);
		
		if(entityOpt.isPresent()) return Optional.of(toDomain(entityOpt.get()));
		
		return Optional.empty();
	}

	@Override
	public void update(ReportAnalysis domain) {
		
		this.commandProxy().update(toEntity(domain));
		
	}

	@Override
	public void insert(ReportAnalysis domain) {
		
		this.commandProxy().insert(toEntity(domain));
		
	}

}
