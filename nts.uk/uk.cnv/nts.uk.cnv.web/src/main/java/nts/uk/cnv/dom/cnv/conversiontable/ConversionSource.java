package nts.uk.cnv.dom.cnv.conversiontable;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.cnv.dom.cnv.conversionsql.Join;
import nts.uk.cnv.dom.cnv.conversionsql.JoinAtr;
import nts.uk.cnv.dom.cnv.conversionsql.TableFullName;
import nts.uk.cnv.dom.cnv.service.ConversionInfo;
import nts.uk.cnv.dom.constants.Constants;

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
