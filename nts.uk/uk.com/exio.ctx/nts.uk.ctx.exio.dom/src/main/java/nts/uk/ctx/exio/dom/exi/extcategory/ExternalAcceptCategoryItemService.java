package nts.uk.ctx.exio.dom.exi.extcategory;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.exio.dom.exi.condset.AcceptMode;
import nts.uk.ctx.exio.dom.exi.csvimport.CsvItem;
import nts.uk.ctx.exio.dom.exi.extcategory.specialedit.SpecialEdit;
import nts.uk.ctx.exio.dom.exi.extcategory.specialedit.SpecialEditCompanyID;
import nts.uk.ctx.exio.dom.exi.extcategory.specialedit.SpecialEditEmployeeId;
import nts.uk.ctx.exio.dom.exi.extcategory.specialedit.SpecialEditNoEdit;
@Stateless
public class ExternalAcceptCategoryItemService {

	public static SpecialEdit instance(String editedItemValue, Optional<AcceptMode> accMode, CsvItem csvItem) {
		//特殊区分の判別
		switch (csvItem.getAcceptItem().get().getSpecialFlg()) {
		case NOTSPECIAL: //０：特殊項目ではない
			return new SpecialEditNoEdit(false, editedItemValue, accMode);
		case CID: //会社CD
			return new SpecialEditCompanyID(false, editedItemValue, csvItem.getValue(), accMode);
		case SID: //社員CD
			return new SpecialEditEmployeeId(false, editedItemValue, csvItem.getValue(), accMode);
		default:
			break;
		}
		return null;
	}

}
