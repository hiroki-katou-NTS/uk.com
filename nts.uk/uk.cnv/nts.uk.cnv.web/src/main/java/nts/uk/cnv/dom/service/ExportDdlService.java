package nts.uk.cnv.dom.service;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.uk.cnv.dom.tabledefinetype.databasetype.DatabaseType;
import nts.uk.cnv.dom.tabledesign.TableDesign;

@Stateless
public class ExportDdlService {

	public String exportDdl(Require require, String tablename, String type) {
		Optional<TableDesign> td = require.find(tablename);
		if(!td.isPresent()) {
			throw new BusinessException(new RawErrorMessage("定義が見つかりません：" + tablename));
		}

		if("uk".equals(type)) {
			return td.get().createTableSql();
		}

		DatabaseType dbtype = DatabaseType.valueOf(type);
		String createTableSql = td.get().createFullTableSql(dbtype.spec());

		return createTableSql;
	}

	public interface Require {
		Optional<TableDesign> find(String tablename);

	}
}
