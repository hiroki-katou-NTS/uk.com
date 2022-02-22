package nts.uk.screen.at.app.query.kmp.kmp001.j;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.aggregation.dom.common.ExportQRCodeToExcel;
import nts.uk.ctx.at.aggregation.dom.common.QRStampCardDto;

/**
 * UKDesign.UniversalK.就業.KDP_打刻.KMP001_IDカードの登録.J：QRコード出力.メニュー別OCD.選択社員のカードNOをQRコード出力する
 * 
 * @author tutt
 *
 */
@Stateless
public class ExportQrcodeByCardNumber {

	@Inject
	private GetExtractedEmployeeCardNo getCardNo;

	@Inject
	private ExportQRCodeToExcel exportQRCodeToExcel;

	public void exportQRCode(GetExtractedEmployeeCardNoInput input) {

		// 1. call query 抽出した社員のカードNOを取得する
		List<QRStampCardDto> stampCards = getCardNo.get(input);

		// 2. Check <List>打刻カード is empty
		if (stampCards.isEmpty()) {
			throw new BusinessException("Msg_37");
		}

		// 3. Report EXCELファイルを出力する
		try {
			exportQRCodeToExcel.printData(Integer.parseInt(input.getSetRow()), Integer.parseInt(input.getSetCol()),
					stampCards, input.getQrSize());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
