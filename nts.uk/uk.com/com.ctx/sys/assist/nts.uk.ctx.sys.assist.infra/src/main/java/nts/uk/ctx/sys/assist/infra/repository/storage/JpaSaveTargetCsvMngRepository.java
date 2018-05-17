/**
 * 
 */
package nts.uk.ctx.sys.assist.infra.repository.storage;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.storage.SaveTargetCsv;
import nts.uk.ctx.sys.assist.dom.storage.SaveTargetCsvRepository;

/**
 * @author nam.lh
 *
 */
@Stateless
public class JpaSaveTargetCsvMngRepository extends JpaRepository implements SaveTargetCsvRepository {

	private static final String SELECT_BY_STORE_PROCESSING_ID = "select a.storeProcessingId, a.suppleExplanation, b.saveForm, b.saveSetCode, b.saveName, c.categoryId, c.timeStore, c.recoveryStorageRange, b.saveForInvest, c.otherCompanyCls, e.categoryId, e.tableJapanName, e.tableEnglishName, e.historyCls, e.defaultCondKeyQuery, e.fieldKeyQuery1, e.fieldKeyQuery2, e.fieldKeyQuery3, e.fieldKeyQuery4, e.fieldKeyQuery5, e.fieldKeyQuery6, e.fieldKeyQuery7, e.fieldKeyQuery8, e.fieldKeyQuery9, e.fieldKeyQuery10, e.clsKeyQuery1, e.clsKeyQuery2, e.clsKeyQuery3, e.clsKeyQuery4, e.clsKeyQuery5, e.clsKeyQuery6, e.clsKeyQuery7, e.clsKeyQuery8, e.clsKeyQuery9, e.clsKeyQuery10, e.filedKeyUpdate1, , e.filedKeyUpdate2, , e.filedKeyUpdate3,  e.filedKeyUpdate4,  e.filedKeyUpdate5,  e.filedKeyUpdate6, e.filedKeyUpdate7, e.filedKeyUpdate8, e.filedKeyUpdate9, e.filedKeyUpdate10, e.filedKeyUpdate11, e.filedKeyUpdate12, e.filedKeyUpdate13, e.filedKeyUpdate14, e.filedKeyUpdate15, e.filedKeyUpdate16, e.filedKeyUpdate17, e.filedKeyUpdate18, e.filedKeyUpdate19, e.filedKeyUpdate20, e.fieldDate1, , e.fieldDate2, , e.fieldDate3,  e.fieldDate4,  e.fieldDate5,  e.fieldDate6, e.fieldDate7, e.fieldDate8, e.fieldDate9, e.fieldDate10, e.fieldDate11, e.fieldDate12, e.fieldDate13, e.fieldDate14, e.fieldDate15, e.fieldDate16, e.fieldDate17, e.fieldDate18, e.fieldDate19, e.fieldDate20   from SspmtManualSetOfDataSave a"
			+ "JOIN SspmtResultOfSaving b" + "ON a.storeProcessingId = b.storeProcessingId" + "JOIN SspmtCategory c "
			+ "ON c.categoryId in (SELECT c.categoryId FROM SspmtTargetCategory d WHERE b.storeProcessingId =:storeProcessingId )"
			+ "JOIN SspmtCategoryFieldMt e" + "ON e.categoryId = c.categoryId";

	@Override
	public List<SaveTargetCsv> getSaveTargetCsvById(String storeProcessingId) {
		List<Object[]> listTemp = this.queryProxy().query(SELECT_BY_STORE_PROCESSING_ID, Object[].class)
				.setParameter("storeProcessingId", storeProcessingId).getList();

		if (listTemp == null || listTemp.isEmpty()) {
			return Collections.emptyList();
		}
		return listTemp.stream().map(i -> createDomainFromEntity(i)).collect(Collectors.toList());
	}

	/**
	 * @param i
	 * @return
	 */
	private SaveTargetCsv createDomainFromEntity(Object[] i) {
		String storeProcessingId = String.valueOf(i[0]);
		int saveForm = Integer.parseInt(String.valueOf(i[1]));
		String saveSetCode = String.valueOf(i[2]);
		String saveName = String.valueOf(i[3]);
		String suppleExplanation = String.valueOf(i[4]);
		String categoryId = String.valueOf(i[5]);
		String categoryName = String.valueOf(i[6]);
		int timeStore = Integer.parseInt(String.valueOf(i[7]));
		int recoveryStorageRange = Integer.parseInt(String.valueOf(i[8]));
		int saveForInvest = Integer.parseInt(String.valueOf(i[9]));
		int otherCompanyCls = Integer.parseInt(String.valueOf(i[10]));
		String tableJapanName = String.valueOf(i[11]);
		String tableEnglishName = String.valueOf(i[12]);
		int historyCls = Integer.parseInt(String.valueOf(i[13]));
		String defaultCondKeyQuery = String.valueOf(i[14]);
		String fieldKeyQuery1 = String.valueOf(i[15]);
		String fieldKeyQuery2 = String.valueOf(i[16]);
		String fieldKeyQuery3 = String.valueOf(i[17]);
		String fieldKeyQuery4 = String.valueOf(i[18]);
		String fieldKeyQuery5 = String.valueOf(i[19]);
		String fieldKeyQuery6 = String.valueOf(i[20]);
		String fieldKeyQuery7 = String.valueOf(i[21]);
		String fieldKeyQuery8 = String.valueOf(i[22]);
		String fieldKeyQuery9 = String.valueOf(i[23]);
		String fieldKeyQuery10 = String.valueOf(i[24]);
		String clsKeyQuery1 = String.valueOf(i[25]);
		String clsKeyQuery2 = String.valueOf(i[26]);
		String clsKeyQuery3 = String.valueOf(i[27]);
		String clsKeyQuery4 = String.valueOf(i[28]);
		String clsKeyQuery5 = String.valueOf(i[29]);
		String clsKeyQuery6 = String.valueOf(i[30]);
		String clsKeyQuery7 = String.valueOf(i[31]);
		String clsKeyQuery8 = String.valueOf(i[32]);
		String clsKeyQuery9 = String.valueOf(i[33]);
		String clsKeyQuery10 = String.valueOf(i[34]);
		String filedKeyUpdate1 = String.valueOf(i[35]);
		String filedKeyUpdate2 = String.valueOf(i[36]);
		String filedKeyUpdate3 = String.valueOf(i[37]);
		String filedKeyUpdate4 = String.valueOf(i[38]);
		String filedKeyUpdate5 = String.valueOf(i[39]);
		String filedKeyUpdate6 = String.valueOf(i[40]);
		String filedKeyUpdate7 = String.valueOf(i[41]);
		String filedKeyUpdate8 = String.valueOf(i[42]);
		String filedKeyUpdate9 = String.valueOf(i[43]);
		String filedKeyUpdate10 = String.valueOf(i[44]);
		String filedKeyUpdate11 = String.valueOf(i[45]);
		String filedKeyUpdate12 = String.valueOf(i[46]);
		String filedKeyUpdate13 = String.valueOf(i[47]);
		String filedKeyUpdate14 = String.valueOf(i[48]);
		String filedKeyUpdate15 = String.valueOf(i[49]);
		String filedKeyUpdate16 = String.valueOf(i[50]);
		String filedKeyUpdate17 = String.valueOf(i[51]);
		String filedKeyUpdate18 = String.valueOf(i[52]);
		String filedKeyUpdate19 = String.valueOf(i[53]);
		String filedKeyUpdate20 = String.valueOf(i[54]);
		String fieldDate1 = String.valueOf(i[55]);
		String fieldDate2 = String.valueOf(i[56]);
		String fieldDate3 = String.valueOf(i[57]);
		String fieldDate4 = String.valueOf(i[58]);
		String fieldDate5 = String.valueOf(i[59]);
		String fieldDate6 = String.valueOf(i[60]);
		String fieldDate7 = String.valueOf(i[61]);
		String fieldDate8 = String.valueOf(i[62]);
		String fieldDate9 = String.valueOf(i[63]);
		String fieldDate10 = String.valueOf(i[64]);
		String fieldDate11 = String.valueOf(i[65]);
		String fieldDate12 = String.valueOf(i[66]);
		String fieldDate13 = String.valueOf(i[67]);
		String fieldDate14 = String.valueOf(i[68]);
		String fieldDate15 = String.valueOf(i[69]);
		String fieldDate16 = String.valueOf(i[70]);
		String fieldDate17 = String.valueOf(i[71]);
		String fieldDate18 = String.valueOf(i[72]);
		String fieldDate19 = String.valueOf(i[73]);
		String fieldDate20 = String.valueOf(i[74]);
		SaveTargetCsv saveTargetCsv = new SaveTargetCsv(storeProcessingId, saveForm, saveSetCode, saveName,
				suppleExplanation, categoryId, categoryName, timeStore, recoveryStorageRange, saveForInvest,
				otherCompanyCls, tableJapanName, tableEnglishName, historyCls, defaultCondKeyQuery, fieldKeyQuery1,
				fieldKeyQuery2, fieldKeyQuery3, fieldKeyQuery4, fieldKeyQuery5, fieldKeyQuery6, fieldKeyQuery7,
				fieldKeyQuery8, fieldKeyQuery9, fieldKeyQuery10, clsKeyQuery1, clsKeyQuery2, clsKeyQuery3, clsKeyQuery4,
				clsKeyQuery5, clsKeyQuery6, clsKeyQuery7, clsKeyQuery8, clsKeyQuery9, clsKeyQuery10, filedKeyUpdate1,
				filedKeyUpdate2, filedKeyUpdate3, filedKeyUpdate4, filedKeyUpdate5, filedKeyUpdate6, filedKeyUpdate7,
				filedKeyUpdate8, filedKeyUpdate9, filedKeyUpdate10, filedKeyUpdate11, filedKeyUpdate12,
				filedKeyUpdate13, filedKeyUpdate14, filedKeyUpdate15, filedKeyUpdate16, filedKeyUpdate17,
				filedKeyUpdate18, filedKeyUpdate19, filedKeyUpdate20, fieldDate1, fieldDate2, fieldDate3, fieldDate4,
				fieldDate5, fieldDate6, fieldDate7, fieldDate8, fieldDate9, fieldDate10, fieldDate11, fieldDate12,
				fieldDate13, fieldDate14, fieldDate15, fieldDate16, fieldDate17, fieldDate18, fieldDate19, fieldDate20);
		return saveTargetCsv;
	}

}
