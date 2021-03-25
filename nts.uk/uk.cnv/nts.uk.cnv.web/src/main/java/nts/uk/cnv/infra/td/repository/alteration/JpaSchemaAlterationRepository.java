package nts.uk.cnv.infra.td.repository.alteration;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.dom.td.alteration.AlterationRepository;
import nts.uk.cnv.dom.td.alteration.schema.SchemaAlteration;
import nts.uk.cnv.dom.td.alteration.schema.SchemaAlterationRepository;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummaryRepository;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;

@Stateless
public class JpaSchemaAlterationRepository extends JpaRepository implements SchemaAlterationRepository {

	@Inject
	AlterationSummaryRepository summaryRepo;

	@Inject
	AlterationRepository alterRepo;

	@Override
	public List<SchemaAlteration> get(DevelopmentProgress progress) {

		val alterIdList = summaryRepo.get(progress)
				.stream()
				.map(s -> s.getAlterId())
				.collect(Collectors.toList());

		 val summaries = alterRepo.gets(alterIdList)
				.stream()
				.flatMap(s -> SchemaAlteration.create(s).stream())
				.collect(toList());

		return summaries;
	}

}
