package nts.uk.screen.at.app.find.ktg.ktg005.a;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author sonnlb
 *
 */
@NoArgsConstructor
@Data
@Setter
public class NumberOfAppDto {
	// 承認件数
	int numberApprovals = 0;

	// 未承認件数
	int numberNotApprovals = 0;

	// 否認件数
	int numberDenials = 0;

	// 差し戻し件数
	int numberRemand = 0;
}
