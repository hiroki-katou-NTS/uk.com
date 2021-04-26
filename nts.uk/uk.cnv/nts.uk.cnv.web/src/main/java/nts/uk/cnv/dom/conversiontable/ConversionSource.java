package nts.uk.cnv.dom.conversiontable;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.cnv.dom.constants.Constants;
import nts.uk.cnv.dom.conversionsql.Join;
import nts.uk.cnv.dom.conversionsql.JoinAtr;
import nts.uk.cnv.dom.conversionsql.TableFullName;
import nts.uk.cnv.dom.service.ConversionInfo;

@Getter
@AllArgsConstructor
public class ConversionSource {

	String sourceId;
	String category;
	String sourceTableName;
	String condition;
	String memo;

	public Join getJoin(ConversionInfo info) {
		return new Join(
						new TableFullName(
							info.getSourceDatabaseName(),
							info.getSourceSchema(),
							this.sourceTableName,
							Constants.BaseTableAlias
						),
					JoinAtr.Main,
					new ArrayList<>()
				);
	}
}
