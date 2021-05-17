package nts.uk.ctx.exio.dom.exi.extcategory.specialedit;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import lombok.val;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.exio.dom.exi.condset.AcceptMode;
import nts.uk.ctx.exio.dom.exi.extcategory.SpecialEditValue;
import nts.uk.ctx.exio.dom.exo.exoutsummaryservice.EmployeeInforExoImport;
import nts.uk.ctx.exio.dom.exo.exoutsummaryservice.RegulationInfoEmployeeAdapter;

public class SpecialEditEmployeeId extends SpecialEdit{
	@Inject
	private RegulationInfoEmployeeAdapter empAdapter;
	
	public SpecialEditEmployeeId(boolean chkError, Object itemValue, Optional<AcceptMode> accMode,
			List<List<String>> lstLineData) {
		super(chkError, itemValue, accMode, lstLineData);
	}
	
	/**
	 * 【２】社員CD⇒社員ID（SID）
	 * @param itemValue
	 * @param accMode
	 * @param result
	 * @return
	 */
	public SpecialEditValue edit() {
		val specialEditCompanyID = new SpecialEditCompanyID(chkError, itemValue, accMode, lstLineData);
		SpecialEditValue result = specialEditCompanyID.edit();
		
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
}
