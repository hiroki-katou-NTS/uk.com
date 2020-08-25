package nts.uk.ctx.at.request.infra.repository.application.stamp;

import java.util.Optional;

import javax.ejb.Stateless;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Worksheet;

import nts.arc.i18n.I18NText;
import nts.uk.ctx.at.request.dom.application.common.service.print.PrintContentOfApp;
import nts.uk.ctx.at.request.dom.application.stamp.AppRecordImage;
import nts.uk.ctx.at.request.dom.application.stamp.output.AppStampOutput;
@Stateless
public class AsposeAppStamp {
	public static final int APPSTAMP = 1;
	
	public static final int APPSTAMP_ACTUAL_DATA = 2;
	
	public static final int APP_RECORDER_IMAGE = 3;
	
	public void printAppStampContent(Worksheet worksheet, PrintContentOfApp printContentOfApp) {
		// check type print
		int type = 3;
		if (type == APP_RECORDER_IMAGE) {
			Optional<AppStampOutput> appStampOutputOp= printContentOfApp.getOpAppStampOutput();
			if (!appStampOutputOp.isPresent()) return;
			Optional<AppRecordImage> appRecordImageOp = appStampOutputOp.get().getAppRecordImage();
			if(!appRecordImageOp.isPresent()) return;
			AppRecordImage appRecordImage = appRecordImageOp.get();
			Cells cells = worksheet.getCells();
			Cell cellB8 = cells.get("B8");
			cellB8.setValue(I18NText.getText("KAF002_77"));
			Cell cellB9 = cells.get("B9");
			cellB9.setValue(I18NText.getText("KAF007_79"));
			
			// set content right side
			
			Cell cellD8 = cells.get("D8");
			cellD8.setValue(appRecordImage.getAppStampGoOutAtr().isPresent() ? (appRecordImage.getAppStampCombinationAtr().name + "("+appRecordImage.getAppStampGoOutAtr().get().name+")")  : appRecordImage.getAppStampCombinationAtr().name);
			Cell cellD9 = cells.get("D9");
			cellD9.setValue(appRecordImage.getAttendanceTime().v());
		}
	}
}
