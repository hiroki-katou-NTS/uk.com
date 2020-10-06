package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.qualificationhistory;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 資格保有者情報
 * @author chungnt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QualificationHolderInformation {

	// 保有者
	private List<Holder> holders;
	
	private String qualificationId;
}