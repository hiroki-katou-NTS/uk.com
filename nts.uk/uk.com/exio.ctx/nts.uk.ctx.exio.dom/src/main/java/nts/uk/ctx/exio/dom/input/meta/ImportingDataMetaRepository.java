package nts.uk.ctx.exio.dom.input.meta;

import java.util.Optional;

import nts.uk.ctx.exio.dom.input.ExecutionContext;

public interface ImportingDataMetaRepository {
	
	void setup(ExecutionContext context);
	
	void save(ImportingDataMeta meta);

	Optional<ImportingDataMeta> find(ExecutionContext context);
}
