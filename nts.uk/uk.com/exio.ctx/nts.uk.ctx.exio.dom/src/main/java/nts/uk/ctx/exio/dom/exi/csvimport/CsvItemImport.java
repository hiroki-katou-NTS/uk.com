package nts.uk.ctx.exio.dom.exi.csvimport;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.PrimitiveValueConstraintException;
import nts.arc.time.GeneralDateTime;
import nts.gul.csv.NtsCsvRecord;
import nts.uk.ctx.exio.dom.exi.codeconvert.AcceptCdConvert;
import nts.uk.ctx.exio.dom.exi.codeconvert.CdConvertDetails;
import nts.uk.ctx.exio.dom.exi.codeconvert.CodeConvertCode;
import nts.uk.ctx.exio.dom.exi.condset.AcceptMode;
import nts.uk.ctx.exio.dom.exi.condset.AcceptanceLineNumber;
import nts.uk.ctx.exio.dom.exi.dataformat.ChrDataFormatSet;
import nts.uk.ctx.exio.dom.exi.dataformat.ItemType;
import nts.uk.ctx.exio.dom.exi.dataformat.NumDataFormatSet;
import nts.uk.ctx.exio.dom.exi.execlog.ErrorOccurrenceIndicator;
import nts.uk.ctx.exio.dom.exi.execlog.ExacErrorLog;
import nts.uk.ctx.exio.dom.exi.extcategory.ExternalAcceptCategory;
import nts.uk.ctx.exio.dom.exi.extcategory.ExternalAcceptCategoryItem;
import nts.uk.ctx.exio.dom.exi.extcategory.ExternalAcceptCategoryItemService;
import nts.uk.ctx.exio.dom.exi.extcategory.SpecialEditValue;
import nts.uk.ctx.exio.dom.exi.extcategory.SpecialExternalItem;
import nts.uk.ctx.exio.dom.exi.extcategory.specialedit.SpecialEdit;
import nts.uk.ctx.exio.dom.exi.item.AcceptItemEditValueDto;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItem;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.i18n.TextResource;

@AllArgsConstructor
public class CsvItemImport {
	private String cid;
	private NtsCsvRecord colHeader;
	private NtsCsvRecord record; 
	private int categoryId;
	private List<List<String>> lstLineData;
	private int errItems;
	private String processId;
	private List<ExacErrorLog> lstExacErrorLog;
	private int lines;
	private Optional<AcceptMode> acceptMode;
	
	public ItemCheck readLine(Require require) {
		boolean isLineError = true; //エラー行を数る
		boolean isCond = true; //True 受入条件がOK、False　受入条件がNOT　OK　　//受入条件をチェック

		val optAcceptCategory = require.getAcceptCategory();
		if(!optAcceptCategory.isPresent()) {
			return ItemCheck.acceptCategoryNotFound();
		}
		List<ExternalAcceptCategoryItem> lstAcceptItem =  optAcceptCategory.get().getLstAcceptItem();

		val lstAccSetItem = require.getListStdAcceptItems();
		
		//項目の編集値
		Map<Integer, Object> mapLineContent = new HashMap<>();
		for(int items = 0; items < colHeader.columnLength(); items ++){ //item of line
			String csvItemName = colHeader.getColumn(items).toString();
			Object csvItemValue = record.getColumn(items);
			
			int count = items + 1;
			List<StdAcceptItem> lstAcceptSetItem = lstAccSetItem.stream()
					.filter(x -> x.getCsvItemName().get().equals(csvItemName) && x.getCsvItemNumber().get() == count)
					.collect(Collectors.toList());
			if(lstAcceptSetItem.isEmpty()) continue;
			StdAcceptItem accSetItem = lstAcceptSetItem.get(0);
			
			
			List<ExternalAcceptCategoryItem> accItems = lstAcceptItem.stream()
					.filter(x -> x.getCategoryId() == categoryId && x.getItemNo() == accSetItem.getAcceptItemNumber())
					.collect(Collectors.toList());
			if(accItems.isEmpty()) continue;
			ExternalAcceptCategoryItem acceptItem = accItems.get(0);
			
			lstLineData.add(Arrays.asList(csvItemName, csvItemValue.toString()));
			//①　受付条件設定チェック			
			//アルゴリズム「取得した値を編集する」を実行する Execute the algorithm "Edit acquired value"
			AcceptItemEditValueDto condEditAndCheck = accSetItem.checkCondition(csvItemValue.toString());
			//コード変換に選択があるか判別
			List<AcceptCdConvert> lstcdConvert = require.getAcceptCdConvertByCompanyId();
			condEditAndCheck = convertCodeItem(lstcdConvert, accSetItem, csvItemValue.toString(), condEditAndCheck);
			csvItemValue = condEditAndCheck.getEditValue();
			if(!condEditAndCheck.getEditError().isEmpty()) {
				errItems += 1;
				//エラー内容を編集してドメイン「外部受入エラーログ」に書き出す
				ExacErrorLog exLog = new ExacErrorLog(errItems, //ログ連番
						cid,
						processId,
						Optional.ofNullable(csvItemName),
						Optional.ofNullable(csvItemValue.toString()),
						Optional.ofNullable(TextResource.localize(condEditAndCheck.getEditError())),
						new AcceptanceLineNumber(lines),
						GeneralDateTime.now(),
						Optional.ofNullable(acceptItem.getItemName()),
						ErrorOccurrenceIndicator.EDIT);
				lstExacErrorLog.add(exLog);
				isLineError = condEditAndCheck.isResultCheck(); //
			}
			
			if(!condEditAndCheck.isResultCheck()) {
				isCond = false;
			} else {
				//② Check primitive value
				if(acceptItem.getPrimitiveName().isPresent() && !acceptItem.getPrimitiveName().get().isEmpty()) {
					String prvError = checkPrimitivalue(acceptItem.getPrimitiveName().get(), csvItemValue);
					if(!prvError.isEmpty()) {
						errItems += 1;
						ExacErrorLog exLog = new ExacErrorLog(errItems, //ログ連番
								cid,
								processId,
								Optional.ofNullable(csvItemName),
								Optional.ofNullable(csvItemValue.toString()),
								Optional.ofNullable(prvError),
								new AcceptanceLineNumber(lines),
								GeneralDateTime.now(),
								Optional.ofNullable(acceptItem.getItemName()),
								ErrorOccurrenceIndicator.EDIT);
						lstExacErrorLog.add(exLog);
						isLineError = false; //
					}
				}
				
					//③　TODO アルゴリズム「特殊区分項目の編集」を実行
					val specEdit = ExternalAcceptCategoryItemService.instance(csvItemValue, acceptMode, lstLineData, acceptItem);
					val speValue = require.get(acceptItem.getSpecialFlg(), specEdit);
					
					if(speValue.isChkError()) {
						errItems += 1;
						ExacErrorLog exLog = new ExacErrorLog(errItems, //ログ連番
								cid,
								processId,
								Optional.ofNullable(acceptItem.getTableName() + "[" + acceptItem.getColumnName() + "]"),
								Optional.ofNullable(csvItemValue.toString()),
								Optional.ofNullable(TextResource.localize(speValue.getErrorContent())),
								new AcceptanceLineNumber(lines),
								GeneralDateTime.now(),
								Optional.ofNullable(acceptItem.getItemName()),
								ErrorOccurrenceIndicator.EDIT);
						lstExacErrorLog.add(exLog);
						isLineError = false;
						continue;
					}
					csvItemValue = speValue.getEditValue();
//				}
				
				//④　TODO　履歴区分
				
				
				mapLineContent.put(items, csvItemValue);
			}
			
		} //item of line
		return new ItemCheck(isLineError, isCond, mapLineContent);
	}

	private String checkPrimitivalue(String prvName, Object prvValue) {
		Class<?> pvClass;
		try {
			pvClass = Class.forName(prvName);
		} catch (ClassNotFoundException e) {
			return "not found: " + prvName;
		}
		
		Constructor<?> pvConst = pvClass.getConstructors()[0];
		
		PrimitiveValue<?> pv;
		try {
			pv = (PrimitiveValue<?>) pvConst.newInstance(prvValue);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			return e.getMessage();
		}
		
		try {
			pv.validate();
		} catch (PrimitiveValueConstraintException ex) {
			return ex.getMessage();
		}
		
		return "";
	}
	
	/**
	 * コード変換に選択があるか判別
	 * @param lstcdConvert
	 * @param accSetItem
	 * @param CSV受入値
	 * @param condEditAndCheck
	 */
	private AcceptItemEditValueDto convertCodeItem(List<AcceptCdConvert> lstcdConvert,
			StdAcceptItem accSetItem,
			String CSV受入値,
			AcceptItemEditValueDto condEditAndCheck) {
		if ((accSetItem.getItemType() == ItemType.CHARACTER
				|| accSetItem.getItemType() == ItemType.NUMERIC)
				&& accSetItem.getDataFormatSetting().isPresent()) {
			Optional<CodeConvertCode> cdConvertSt;
			if(accSetItem.getItemType() == ItemType.CHARACTER) {
				ChrDataFormatSet chrSet = (ChrDataFormatSet) accSetItem.getDataFormatSetting().get();
				cdConvertSt = chrSet.getCdConvertCd();
			} else {
				NumDataFormatSet numSet = (NumDataFormatSet) accSetItem.getDataFormatSetting().get();
				cdConvertSt = numSet.getCdConvertCd();
			}
			if(!cdConvertSt.isPresent()) return condEditAndCheck;
			//コード変換コード
			List<AcceptCdConvert> cdConverts = lstcdConvert.stream()
					.filter(x -> x.getConvertCd().v().equals(cdConvertSt.get().v()))
					.collect(Collectors.toList());
			if(cdConverts.isEmpty()) return condEditAndCheck;
			
			AcceptCdConvert cdConvert = cdConverts.get(0);
			List<CdConvertDetails> listConvertDetails = cdConvert.getListConvertDetails()
					.stream().filter(x -> x.getOutputItem().v().equals(CSV受入値))
					.collect(Collectors.toList());
			if(!listConvertDetails.isEmpty()) {
				condEditAndCheck.setEditValue(listConvertDetails.get(0).getSystemCd().v());	
			} else {
				//「設定のないコードの受入」を判別
				if(cdConvert.getAcceptWithoutSetting() == NotUseAtr.USE) {
					condEditAndCheck.setEditError("Msg_1018");
					condEditAndCheck.setResultCheck(false);
				}
			}						
		}
		return condEditAndCheck;
	}
	
	public interface Require{
		SpecialEditValue get(SpecialExternalItem specialExternalItem, SpecialEdit spesialEdit);
		
		List<StdAcceptItem> getListStdAcceptItems();
		Optional<ExternalAcceptCategory> getAcceptCategory();
		List<AcceptCdConvert> getAcceptCdConvertByCompanyId();
	}
}
