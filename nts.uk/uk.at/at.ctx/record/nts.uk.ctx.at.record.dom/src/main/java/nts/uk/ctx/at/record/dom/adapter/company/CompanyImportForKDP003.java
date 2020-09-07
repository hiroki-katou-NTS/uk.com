package nts.uk.ctx.at.record.dom.adapter.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyImportForKDP003 {
	private String companyCode;      // 会社コード
	private String companyName;      // 会社名
	private String companyId;        // 会社ID
	private String contractCd;       // 契約コード
}

