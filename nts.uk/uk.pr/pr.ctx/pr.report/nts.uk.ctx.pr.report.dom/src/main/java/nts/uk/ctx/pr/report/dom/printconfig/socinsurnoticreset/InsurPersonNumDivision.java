package nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset;
 /**
 * 被保険者整理番号区分
 */
public enum InsurPersonNumDivision {
     //出力しない
     DO_NOT_OUPUT(0, "Enum_InsurPersonNumDivision_DO_NOT_OUPUT"),
     //健康保険番号を出力
     OUTPUT_HEAL_INSUR_NUM(1, "Enum_InsurPersonNumDivision_OUTPUT_HEAL_INSUR_NUM"),
     //厚生年金番号を出力
     OUTPUT_THE_WELF_PENNUMBER(2, "Enum_InsurPersonNumDivision_OUTPUT_THE_WELF_PENNUMBER"),
     //健保組合番号を出力
     OUTPUT_HEAL_INSUR_UNION(3, "Enum_InsurPersonNumDivision_OUTPUT_HEAL_INSUR_UNION"),
     //基金加入員番号を出力
     OUTPUT_THE_FUN_MEMBER(4, "Enum_InsurPersonNumDivision_OUTPUT_THE_FUN_MEMBER");

     /** The value. */
     public final int value;

     /** The name id. */
     public final String nameId;
     private InsurPersonNumDivision(int value, String nameId)
     {
         this.value = value;
         this.nameId = nameId;
     }
}
