package nts.uk.file.at.app.export.bento;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface ReservationMonthGenerator {
	
	void generate(FileGeneratorContext generatorContext, ReservationMonthDataSource dataSource);
	
}
