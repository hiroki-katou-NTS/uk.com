package nts.uk.cnv.app.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import nts.uk.cnv.app.dto.TableDesignExportDto;
import nts.uk.cnv.dom.service.ExportDdlService;
import nts.uk.cnv.dom.tabledesign.TableDesign;
import nts.uk.cnv.dom.tabledesign.TableDesignRepository;

@Stateless
public class TableDesignerService {

	@Inject
	TableDesignRepository tableDesignRepository;
	
	@Inject
	ExportDdlService exportDdlService;

	public String exportDdl(TableDesignExportDto params) {
		RequireImpl require = new RequireImpl(tableDesignRepository);
		return exportDdlService.exportDdl(require, params.getTableName(), params.getType());
	}

	@RequiredArgsConstructor
	private static class RequireImpl implements ExportDdlService.Require {

		private final TableDesignRepository tableDesignRepository;
		
		@Override
		public Optional<TableDesign> find(String tablename) {
			return tableDesignRepository.find(tablename);
		}
	};
}
