package nts.uk.ctx.sys.assist.dom.datarestoration.common;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareOperatingCondition;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;

@Stateless
public class TableItemValidation {
	
	@Inject
	private TableColumnNameNativeQueryRepository tableColumnRepository;
	// アルゴリズム「テーブル項目チェック」を実行する
	public ServerPrepareMng checkTableItem(ServerPrepareMng serverPrepareMng, List<TableList> tableList){
		if(compareItem(serverPrepareMng, tableList)) serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.TABLE_ITEM_DIFFERENCE);
		return serverPrepareMng;
	}
	// テーブル一覧のすべての行をアルゴリズム「項目の比較」でチェックする
	private boolean compareItem(ServerPrepareMng serverPrepareMng, List<TableList> tableList){
		String fileId = serverPrepareMng.getFileId().get();
		for(int i = 1; i < tableList.size(); i++){
			List<String> csvHeader = getActualCsvHeader(fileId, tableList.get(i).getInternalFileName());
			if (csvHeader.isEmpty()) return true;
			List<String> tableColumn = tableColumnRepository.getTableColumnName(tableList.get(i).getTableEnglishName());
			if (!tableColumn.containsAll(csvHeader)) return true;
		}
		return false;
	}
	private List<String> getActualCsvHeader(String fileId, String fileName){
		List<String> csvHeader = CsvFileUtil.getCsvHeader(fileName, fileId, 3);
		return csvHeader.subList(5, csvHeader.size());
	}
}
