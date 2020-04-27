package nts.uk.ctx.at.request.pub.appdisplayname.export;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class AppDispNameExport {
	// 会社ID
	private String companyId;
	// 申請種類
	private int appType;
	// 表示名
	private String dispName;

}
