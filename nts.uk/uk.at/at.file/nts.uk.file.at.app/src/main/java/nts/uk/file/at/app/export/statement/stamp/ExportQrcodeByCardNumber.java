package nts.uk.file.at.app.export.statement.stamp;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;

/**
 * UKDesign.UniversalK.就業.KDP_打刻.KMP001_IDカードの登録.J：QRコード出力.メニュー別OCD.選択社員のカードNOをQRコード出力する
 * 
 * @author tutt
 *
 */
@Stateless
public class ExportQrcodeByCardNumber extends ExportService<GetExtractedEmployeeCardNoInput> {

	@Inject
	private GetExtractedEmployeeCardNo getCardNo;

	@Inject
	private IExportQRCodeToExcel exportQRCodeToExcel;

	@Override
	protected void handle(ExportServiceContext<GetExtractedEmployeeCardNoInput> context) {
		GetExtractedEmployeeCardNoInput input = context.getQuery();

		// 1. call query 抽出した社員のカードNOを取得する
		List<QRStampCardDto> stampCards = getCardNo.get(input);

		// 2. Check <List>打刻カード is empty
		if (stampCards.isEmpty()) {
			throw new BusinessException("Msg_37");
		}

		// 3. Report EXCELファイルを出力する
		exportQRCodeToExcel.export(context.getGeneratorContext(), input);

	}

}
