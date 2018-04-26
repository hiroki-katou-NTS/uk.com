package nts.uk.screen.at.app.dailyperformance.correction.dto.reasondiscrepancy;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShowColumnDependent {
  private int itemId;
  /*使用区分 */
  private boolean columnTimeUseSet;
  /*乖離理由入力設定 */
  private boolean columnSelectUseSet;
  /*乖離理由選択設定 */
  private boolean columnInputUseSet;
  
  private List<ReasonCodeName> reasons;
  
  public ShowColumnDependent(){
	  this.columnTimeUseSet = false;
	  this.columnInputUseSet = false;
	  this.columnSelectUseSet = false;
	  this.reasons = new ArrayList<>();
  }
  
  public static ShowColumnDependent createDefault(){
	  return new ShowColumnDependent();
  }
}
