package nts.uk.cnv.infra.td.repository;

import java.util.List;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummaryRepository;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;
import nts.uk.cnv.dom.td.devstatus.DevelopmentStatus;

public class JpaAlterationSummaryRepository extends JpaRepository implements AlterationSummaryRepository {

	@Override
	public List<AlterationSummary> getAll(String featureId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<AlterationSummary> getAll(String featureId, DevelopmentStatus devStatus) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<AlterationSummary> getBefore(String featureId, DevelopmentProgress devProgress) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<AlterationSummary> getAfter(String featureId, DevelopmentProgress devProgress) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<AlterationSummary> getOlder(String alterId, DevelopmentProgress devProgress) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
