package nts.uk.ctx.pr.core.dom.wageprovision.salaryindividualamountname;


import lombok.AllArgsConstructor;

/**
* 個人金額カテゴリ区分
*/
@AllArgsConstructor
public enum PerValueCateCls
{
    SUPPLY(0),
    DEDUCTION(1);

    public final int value;
}
