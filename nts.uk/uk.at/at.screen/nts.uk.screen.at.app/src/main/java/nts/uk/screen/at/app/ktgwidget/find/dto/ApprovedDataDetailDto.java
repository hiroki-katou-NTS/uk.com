package nts.uk.screen.at.app.ktgwidget.find.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 承認すべきデータの実行詳細
 * @author tutt
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApprovedDataDetailDto {
	
	//存在する
	private Boolean displayAtr;
	
	//年月
	private Integer yearMonth;
	
	//締めID
	private Integer closureId;
	
	//締名
	private String name;

}
