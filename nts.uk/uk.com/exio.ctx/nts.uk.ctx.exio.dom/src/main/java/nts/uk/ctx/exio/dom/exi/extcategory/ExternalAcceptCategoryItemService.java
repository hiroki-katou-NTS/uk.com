package nts.uk.ctx.exio.dom.exi.extcategory;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.exio.dom.exi.condset.AcceptMode;
import nts.uk.ctx.exio.dom.exo.exoutsummaryservice.EmployeeInforExoImport;
import nts.uk.ctx.exio.dom.exo.exoutsummaryservice.RegulationInfoEmployeeAdapter;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class ExternalAcceptCategoryItemService {
	@Inject
	private RegulationInfoEmployeeAdapter empAdapter;
	/**
	 * 特殊区分項目の編集
	 * @param itemValue
	 * @param accMode
	 * @return
	 */
	public SpecialEditValue editSpecial(Object itemValue, Optional<AcceptMode> accMode,
			List<List<String>> lstLineData, ExternalAcceptCategoryItem categoryItem) {
		SpecialEditValue result = new SpecialEditValue();
		result.setChkError(false);
		result.setEditValue(itemValue);
		
		//特殊区分の判別
		switch (categoryItem.getSpecialFlg()) {
		case NOTSPECIAL: //０：特殊項目ではない
			return result;
		case CID: //会社CD
			result = editCompanyID(itemValue, result, lstLineData);
			break;
		case SID: //社員CD
			result = editCompanyID(itemValue, result, lstLineData);
			result = editEmployeeID(itemValue, accMode, result);
			break;
		default:
			break;
		}
		
		return result;
	}
	/**
	 * 【２】社員CD⇒社員ID（SID）
	 * @param itemValue
	 * @param accMode
	 * @param result
	 * @return
	 */
	private SpecialEditValue editEmployeeID(Object itemValue, Optional<AcceptMode> accMode, SpecialEditValue result) {
		//会社IDを取得できる
		if(!result.isChkError()) {
			//RQ18.「会社ID」「社員コード」より社員基本情報を取得を実行する
			Optional<EmployeeInforExoImport> optEmpInfor = empAdapter.getEmployeeInforByCid(result.getEditValue().toString(), itemValue.toString());
			if(!optEmpInfor.isPresent()) {
				//INPUT.受入モード　＝　上書き　であるかをチェックする
				if(accMode.isPresent() && accMode.get() == AcceptMode.UPDATE_ONLY) {
					result.setChkError(true);
					result.setErrorContent(""); //TODO
					result.setEditValue("");
					return result;
				} else {
					result.setEditValue(IdentifierUtil.randomUniqueId());
					result.setSid(IdentifierUtil.randomUniqueId());
				}
			} else {
				EmployeeInforExoImport empInfor = optEmpInfor.get();
				result.setEditValue(empInfor.getPid());
				result.setSid(empInfor.getSid());
			}
		} else {
			result.setChkError(true);
			result.setErrorContent(""); //TODO
			result.setEditValue("");
		}
		return result;
	}
	
	/**
	 * 【１】会社CD⇒会社ID（CID）
	 * @param itemValue
	 * @param accMode
	 * @param result
	 */
	private SpecialEditValue editCompanyID(Object itemValue, SpecialEditValue result, List<List<String>> lstLineData) {
		if(itemValue == null || itemValue == "") {
			String columnName = "CID";
			for(List<String> lstData : lstLineData) {
				if(lstData.get(0).equals(columnName)) {
					itemValue = lstData.get(1);
				}
			}
			if(itemValue == null || itemValue == "") {
				result.setChkError(true);
				result.setErrorContent("Msg_2128");
				return result;
			}
		}
		String contractCode =  AppContexts.user().contractCode();
		if(contractCode.isEmpty()) {
			result.setChkError(true);
			result.setErrorContent("Msg_2127");
		} else {
			result.setEditValue(contractCode + "-" + itemValue);
		}
		return result;
	}
}
