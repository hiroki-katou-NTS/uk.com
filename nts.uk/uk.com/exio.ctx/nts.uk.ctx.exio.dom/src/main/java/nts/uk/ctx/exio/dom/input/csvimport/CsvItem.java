package nts.uk.ctx.exio.dom.input.csvimport;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.exio.dom.exi.codeconvert.AcceptCdConvert;
import nts.uk.ctx.exio.dom.exi.codeconvert.CdConvertDetails;
import nts.uk.ctx.exio.dom.exi.codeconvert.CodeConvertCode;
import nts.uk.ctx.exio.dom.exi.extcategory.ExternalAcceptCategoryItem;
import nts.uk.ctx.exio.dom.exi.item.AcceptItemEditValueDto;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItem;
import nts.uk.ctx.exio.dom.input.revise.dataformat.ChrDataFormatSet;
import nts.uk.ctx.exio.dom.input.revise.dataformat.ItemType;
import nts.uk.ctx.exio.dom.input.revise.dataformat.NumDataFormatSet;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@AllArgsConstructor
@Getter
public class CsvItem {

	private String name;
	private String value;

	private StdAcceptItem accSetItem;
	
	private ExternalAcceptCategoryItem acceptItem;
	
	/**
	 * コード変換に選択があるか判別
	 * @param lstcdConvert
	 * @param accSetItem
	 * @param CSV受入値
	 * @param condEditAndCheck
	 */
	public AcceptItemEditValueDto convertCodeItem(List<AcceptCdConvert> lstcdConvert) {

		AcceptItemEditValueDto condEditAndCheck = accSetItem.checkCondition(this, value);
		
		if ((accSetItem.getItemType() == ItemType.CHARACTER
				|| accSetItem.getItemType() == ItemType.INT
				|| accSetItem.getItemType() == ItemType.REAL)
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
					.stream().filter(x -> x.getOutputItem().v().equals(this.value))
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
}
