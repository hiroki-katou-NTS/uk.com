package nts.uk.ctx.exio.dom.exi.extcategory;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.exio.dom.exi.condset.AcceptMode;
import nts.uk.ctx.exio.dom.exi.extcategory.specialedit.SpecialEdit;
import nts.uk.ctx.exio.dom.exi.extcategory.specialedit.SpecialEditCompanyID;
import nts.uk.ctx.exio.dom.exi.extcategory.specialedit.SpecialEditEmployeeId;
import nts.uk.ctx.exio.dom.exi.extcategory.specialedit.SpecialEditNoEdit;
@Stateless
public class ExternalAcceptCategoryItemService {

	public static SpecialEdit instance(Object itemValue, Optional<AcceptMode> accMode,
			List<List<String>> lstLineData, ExternalAcceptCategoryItem categoryItem) {
		//特殊区分の判別
		switch (categoryItem.getSpecialFlg()) {
		case NOTSPECIAL: //０：特殊項目ではない
			return new SpecialEditNoEdit(false, itemValue, accMode, lstLineData);
		case CID: //会社CD
			return new SpecialEditCompanyID(false, itemValue, accMode, lstLineData);
		case SID: //社員CD
			return new SpecialEditEmployeeId(false, itemValue, accMode, lstLineData);
		default:
			break;
		}
		return null;
	}

}
