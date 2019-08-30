package nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset;
 /**
 * 被保険者整理番号区分
 */
public enum InsurPersonNumDivision {
     DO_NOT_OUPUT(0, "Enum_InsurPersonNumDivision_DO_NOT_OUPUT"),
     OUTPUT_HEAL_INSUR_NUM(1, "Enum_InsurPersonNumDivision_OUTPUT_HEAL_INSUR_NUM"),
     OUTPUT_THE_WELF_PENNUMBER(2, "Enum_InsurPersonNumDivision_OUTPUT_THE_WELF_PENNUMBER"),
     OUTPUT_HEAL_INSUR_UNION(3, "Enum_InsurPersonNumDivision_OUTPUT_HEAL_INSUR_UNION"),
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
