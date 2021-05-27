package nts.uk.ctx.exio.dom.input.revise;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.PrimitiveValueConstraintException;
import nts.uk.ctx.exio.dom.exi.canonicalize.SpecialExternalItem;
import nts.uk.ctx.exio.dom.exi.canonicalize.specialedit.ExternalAcceptCategoryItemService;
import nts.uk.ctx.exio.dom.exi.canonicalize.specialedit.SpecialEdit;
import nts.uk.ctx.exio.dom.exi.codeconvert.AcceptCdConvert;
import nts.uk.ctx.exio.dom.exi.condset.AcceptMode;
import nts.uk.ctx.exio.dom.exi.execlog.ErrorOccurrenceIndicator;
import nts.uk.ctx.exio.dom.exi.execlog.ExacErrorLogManager;
import nts.uk.ctx.exio.dom.exi.extcategory.ExternalAcceptCategory;
import nts.uk.ctx.exio.dom.exi.extcategory.SpecialEditValue;
import nts.uk.ctx.exio.dom.exi.item.AcceptItemEditValueDto;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItem;
import nts.uk.ctx.exio.dom.input.csvimport.CsvItem;
import nts.uk.ctx.exio.dom.input.csvimport.CsvRecord;
import nts.uk.shr.com.i18n.TextResource;

@AllArgsConstructor
public class ReviseService {
	private CsvRecord csvRecord;
	private int line;
	private Optional<AcceptMode> acceptMode;
	
	public ItemCheck readLine(Require require) {
		boolean isLineError = true; //エラー行を数る
		boolean isCond = true; //True 受入条件がOK、False　受入条件がNOT　OK　　//受入条件をチェック

		//項目の編集値
		Map<Integer, Object> mapLineContent = new HashMap<>();
		//for(int items = 0; items < colHeader.columnLength(); items ++){ //item of line
		for(CsvItem csvItem : csvRecord.getItems()){ //item of line

			if(csvItem.getAcceptItem() == null) continue;
			
			//①　受付条件設定チェック			
			//アルゴリズム「取得した値を編集する」を実行する Execute the algorithm "Edit acquired value"
			//コード変換に選択があるか判別
			List<AcceptCdConvert> lstcdConvert = require.getAcceptCdConvertByCompanyId();
			AcceptItemEditValueDto condEditAndCheck = csvItem.convertCodeItem(lstcdConvert);
			val editedItemValue = condEditAndCheck.getEditValue();
			if(!condEditAndCheck.getEditError().isEmpty()) {
				require.getLogManager().addLog(
						csvItem, editedItemValue.toString(), line, TextResource.localize(condEditAndCheck.getEditError()),
						ErrorOccurrenceIndicator.EDIT);
				isLineError = condEditAndCheck.isResultCheck(); //
			}
			
			if(!condEditAndCheck.isResultCheck()) {
				isCond = false;
			} else {
				//② Check primitive value
				val opPrimitiveName = csvItem.getAcceptItem().getPrimitiveName();
				if(opPrimitiveName.isPresent() && !opPrimitiveName.get().isEmpty()) {
					String prvError = checkPrimitivalue(opPrimitiveName.get(), editedItemValue);
					if(!prvError.isEmpty()) {
						require.getLogManager().addLog(
								csvItem, editedItemValue.toString(), line, prvError,
								ErrorOccurrenceIndicator.EDIT);
						isLineError = false; //
					}
				}
				
				//③　TODO アルゴリズム「特殊区分項目の編集」を実行
				val specEdit = ExternalAcceptCategoryItemService.instance(editedItemValue.toString(), acceptMode,  csvItem);
				val speValue = require.get(csvItem.getAcceptItem().getSpecialFlg(), specEdit);
				
				if(speValue.isChkError()) {
					require.getLogManager().addLogByTableName(
							csvItem, editedItemValue.toString(), line, TextResource.localize(speValue.getErrorContent()),
							ErrorOccurrenceIndicator.EDIT);
					isLineError = false;
					continue;
				}
				val specEditedValue = speValue.getEditValue();
				
				//④　TODO　履歴区分
				
				
				mapLineContent.put(csvRecord.getItems().indexOf(csvItem), specEditedValue);
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
	
	public interface Require{
		SpecialEditValue get(SpecialExternalItem specialExternalItem, SpecialEdit spesialEdit);
		
		List<StdAcceptItem> getListStdAcceptItems();
		Optional<ExternalAcceptCategory> getAcceptCategory();
		List<AcceptCdConvert> getAcceptCdConvertByCompanyId();
		
		ExacErrorLogManager getLogManager();
	}
}
