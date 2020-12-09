package nts.uk.cnv.dom.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.uk.cnv.dom.tabledefinetype.databasetype.DatabaseType;
import nts.uk.cnv.dom.tabledesign.TableDesign;

@Stateless
public class ExportDdlService {

	public String exportDdlAll(Require require, String type, boolean withComment) {
		List<TableDesign> tableDesigns = require.findAll();

		List<String> sql = tableDesigns.stream()
				.map(td -> exportDdl(require, td, type, withComment))
				.collect(Collectors.toList());

		return String.join("\r\n", sql);
	}

	public String exportDdl(Require require, String tablename, String type, boolean withComment) {
		Optional<TableDesign> tableDesign = require.find(tablename);
		if(!tableDesign.isPresent()) {
			throw new BusinessException(new RawErrorMessage("定義が見つかりません：" + tablename));
		}

		return exportDdl(require, tableDesign.get(), type, withComment);
	}

	private String exportDdl(Require require, TableDesign tableDesign, String type, boolean withComment) {

		if("uk".equals(type)) {
			return tableDesign.createTableSql();
		}

		DatabaseType dbtype = DatabaseType.valueOf(type);

		String createTableSql;
		if(withComment) {
			createTableSql = tableDesign.createFullTableSql(dbtype.spec());
		}
		else {
			createTableSql = tableDesign.createSimpleTableSql(dbtype.spec());
		}

		return createTableSql;
	}

	public interface Require {
		List<TableDesign> findAll();
		Optional<TableDesign> find(String tablename);

	}
}
