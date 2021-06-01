package nts.uk.ctx.exio.dom.exi.canonicalize.specialedit;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.exio.dom.exi.condset.AcceptMode;
import nts.uk.ctx.exio.dom.input.csvimport.CsvItem;
@Stateless
public class ExternalAcceptCategoryItemService {

	public static SpecialEdit instance(String editedItemValue, Optional<AcceptMode> accMode, CsvItem csvItem) {
//		//特殊区分の判別
//		switch (csvItem.getAcceptItem().getSpecialFlg()) {
//		case NOTSPECIAL: //０：特殊項目ではない
//			return new SpecialEditNoEdit(false, editedItemValue, accMode);
//		case CID: //会社CD
//			return new SpecialEditCompanyID(false, editedItemValue, csvItem.getValue(), accMode);
//		case SID: //社員CD
//			return new SpecialEditEmployeeId(false, editedItemValue, csvItem.getValue(), accMode);
//		default:
//			break;
//		}
		return null;
	}

}
