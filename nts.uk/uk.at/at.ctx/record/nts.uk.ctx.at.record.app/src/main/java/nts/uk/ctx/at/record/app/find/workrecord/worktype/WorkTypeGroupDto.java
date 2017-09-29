/**
 * 
 */
package nts.uk.ctx.at.record.app.find.workrecord.worktype;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author danpv
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkTypeGroupDto {

	private int no;

	private String name;

	private Set<String> workTypeList;

}
