package nts.uk.cnv.app.command;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.cnv.app.dto.AddSourceResult;
import nts.uk.cnv.core.dom.conversiontable.ConversionSource;
import nts.uk.cnv.dom.conversiontable.ConversionSourcesRepository;
import nts.uk.cnv.dom.tabledesign.ErpTableDesignRepository;

@Stateless
public class RegistConversionSourceCommandHandler extends CommandHandlerWithResult<RegistConversionSourceCommand, AddSourceResult>{

	@Inject
	ConversionSourcesRepository repository;
	@Inject
	ErpTableDesignRepository erpTableDesignRepo;

	@Override
	protected AddSourceResult handle(CommandHandlerContext<RegistConversionSourceCommand> context) {
		RegistConversionSourceCommand command = context.getCommand();

		val domain = new ConversionSource(
				command.getSourceId(),
				command.getCategory(),
				command.getSourceTableName(),
				command.getCondition(),
				command.getMemo(),
				wrapOptional(command.getDateColumnName()),
				wrapOptional(command.getStartDateColumnName()),
				wrapOptional(command.getEndDateColumnName()),
				wrapOptional(command.getDateType()),
				null	//sourceの登録時には不要（取得時にinfraでScvmtErpColumnDesignからセットする）
			);

		if(command.getSourceId() != null && !command.getSourceId().isEmpty()) {
			val source = repository.get(command.getSourceId());
			if(source.isPresent()) {
				repository.update(domain);
				return new AddSourceResult(command.getSourceId());
			}
		}

		String sourceId = repository.insert(domain);

		return new AddSourceResult(sourceId);
	}

	private Optional<String> wrapOptional(String value){
		return (value == null || value.isEmpty()) ? Optional.empty() : Optional.of(value);
	}

}
