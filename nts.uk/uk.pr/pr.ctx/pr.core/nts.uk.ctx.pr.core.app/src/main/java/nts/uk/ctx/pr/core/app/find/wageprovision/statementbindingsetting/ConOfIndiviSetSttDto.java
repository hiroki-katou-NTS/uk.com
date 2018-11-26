package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class ConOfIndiviSetSttDto {
  /**
   * 給与明細書
   */
  private String salaryCode;

  /**
   * 給与明細書
   */
  private String salaryCodeMaster;
  /**
   * 給与明細書
   */
  private String salaryCodeIndividual;
  /**
   * 賞与明細書
   */
  private String bonusCodeCompany;
  /**
   * 賞与明細書
   */
  private String bonusCodeMaster;
  /**
   * 賞与明細書
   */
  private String bonusCodeIndividual;
  /**
   * 明細書名称
   */
  private String statementName;

  /**
   * マスタコード
   */
  private String masterCode;
  /**
   * 雇用名称
   */
  private String employmentName;
  /**
  * 部門名称
  */
  private String departmentName;
  /**
   * 分類名称
   */
  private String classificationName;
  /**
   * 職位名称
   */
  private String positionName;
  /**
   * 給与分類名称
   */
  private String salaryClassificationName;


}
