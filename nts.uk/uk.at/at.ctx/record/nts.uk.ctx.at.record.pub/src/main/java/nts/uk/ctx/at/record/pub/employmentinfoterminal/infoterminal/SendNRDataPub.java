package nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal;

import java.util.List;

public interface SendNRDataPub<T extends List<? extends SendNRInfoDataExport>> {

	public T send(String empInfoTerCode, String contractCode);

}
