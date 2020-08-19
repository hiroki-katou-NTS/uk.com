package nts.uk.cnv.dom.service;

import java.util.Optional;

import nts.arc.error.BusinessException;
import nts.uk.cnv.dom.databasetype.DatabaseType;
import nts.uk.cnv.dom.tabledesign.TableDesign;

public class ExportDdlService {

	public String exportDdl(Require require, String tablename, String type) {
		DatabaseType dbtype = DatabaseType.valueOf(type);
		Optional<TableDesign> td = require.find(tablename);
		if(td.isPresent()) {
			throw new BusinessException("定義が見つかりません：" + tablename);
		}

		return td.get().createDdl(dbtype);
	}
	
	public interface Require {
		Optional<TableDesign> find(String tablename);
		
	}
}
