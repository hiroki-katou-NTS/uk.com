package nts.uk.cnv.dom.td.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.dom.td.tabledefinetype.TableDefineType;
import nts.uk.cnv.dom.td.tabledefinetype.UkDataType;
import nts.uk.cnv.dom.td.tabledefinetype.databasetype.DatabaseType;
import nts.uk.cnv.dom.td.tabledesign.TableDesign;

@Stateless
public class ExportDdlService {

	public String exportDdlAll(Require require, String type, boolean withComment, String feature, GeneralDateTime date) {
		List<TableDesign> tableDesigns = require.findAll(feature, date);

		List<String> sql = tableDesigns.stream()
				.map(td -> exportDdl(require, td, type, withComment).getDdl())
				.collect(Collectors.toList());

		return String.join("\r\n", sql);
	}

	public ExportDdlServiceResult exportDdl(Require require, String tableId, String type, boolean withComment, String feature, GeneralDateTime date) {
		Optional<TableDesign> tableDesign = require.find(tableId, feature, date);
		if(!tableDesign.isPresent()) {
			throw new BusinessException(new RawErrorMessage("定義が見つかりません：" + tableId));
		}

		return exportDdl(require, tableDesign.get(), type, withComment);
	}

	private ExportDdlServiceResult exportDdl(Require require, TableDesign tableDesign, String type, boolean withComment) {

		TableDefineType tableDefineType;
		if("uk".equals(type)) {
			tableDefineType = new UkDataType();
		}
		else {
			tableDefineType = DatabaseType.valueOf(type).spec();
		}

		String createTableSql;
		if(withComment) {
			createTableSql = tableDesign.createFullTableSql(tableDefineType);
		}
		else {
			createTableSql = tableDesign.createSimpleTableSql(tableDefineType);
		}

		return new ExportDdlServiceResult(createTableSql);
	}

	public interface Require {
		List<TableDesign> findAll(String feature, GeneralDateTime date);
		Optional<TableDesign> find(String tablename, String feature, GeneralDateTime date);

	}
}
