using System;
using System.Collections.Generic;
using System.Data;

using WSISmile.Business.Common;

namespace WSISmile.Business.Category.Accept
{
    /// <summary>
    /// 外部受入-人事基本情報カテゴリ
    /// </summary>
    public class EmployeeBase : AcceptCategoryBase
    {
        #region 外部受入-人事基本情報カテゴリ
        public EmployeeBase() { }

        /// <summary>
        /// Smile側からもらった連携データを編集
        /// </summary>
        /// <param name="dtSmile">Smile側の連携データ</param>
        public override void SmileDataEditing(DataTable dtSmile)
        {
            #region Smile側からもらった連携データを編集 : SmileDataEditing
            foreach (DataRow drSmile in dtSmile.Rows)
            {
                // フリガナ *全角が必要
                drSmile[EmployeeBaseItem.KANA_NAME] =
                    Microsoft.VisualBasic.Strings.StrConv(drSmile[EmployeeBaseItem.KANA_NAME].ToString().Trim(), Microsoft.VisualBasic.VbStrConv.Wide, 0);

                /*
                 * 【Trimが必要な項目】 8桁未満の場合に、後ろスペースで埋めている
                 * 社員CD
                 * 組織コード、所属コード
                 * 分類０～分類９
                */

                // 社員コード
                drSmile[EmployeeBaseItem.EMPLOYEE_CD] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeBaseItem.EMPLOYEE_CD]);

                // 退職年月日
                if (Toolbox.IsNull(drSmile[EmployeeBaseItem.RETIREMENT_DATE]) || decimal.Parse(drSmile[EmployeeBaseItem.RETIREMENT_DATE].ToString()) == 0m)
                {
                    drSmile[EmployeeBaseItem.RETIREMENT_DATE] = 99991231;
                }

                // 組織コード
                drSmile[EmployeeBaseItem.ORGANIZATION_CD] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeBaseItem.ORGANIZATION_CD]);

                // 所属コード
                drSmile[EmployeeBaseItem.BELONGS_CD] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeBaseItem.BELONGS_CD]);

                // 分類０
                drSmile[EmployeeBaseItem.CLASSIFICATION_0] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeBaseItem.CLASSIFICATION_0]);

                // 分類１
                drSmile[EmployeeBaseItem.CLASSIFICATION_1] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeBaseItem.CLASSIFICATION_1]);

                // 分類２
                drSmile[EmployeeBaseItem.CLASSIFICATION_2] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeBaseItem.CLASSIFICATION_2]);

                // 分類３
                drSmile[EmployeeBaseItem.CLASSIFICATION_3] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeBaseItem.CLASSIFICATION_3]);

                // 分類４
                drSmile[EmployeeBaseItem.CLASSIFICATION_4] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeBaseItem.CLASSIFICATION_4]);

                // 分類５
                drSmile[EmployeeBaseItem.CLASSIFICATION_5] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeBaseItem.CLASSIFICATION_5]);

                // 分類６
                drSmile[EmployeeBaseItem.CLASSIFICATION_6] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeBaseItem.CLASSIFICATION_6]);

                // 分類７
                drSmile[EmployeeBaseItem.CLASSIFICATION_7] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeBaseItem.CLASSIFICATION_7]);

                // 分類８
                drSmile[EmployeeBaseItem.CLASSIFICATION_8] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeBaseItem.CLASSIFICATION_8]);

                // 分類９
                drSmile[EmployeeBaseItem.CLASSIFICATION_9] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeBaseItem.CLASSIFICATION_9]);
            }
            #endregion

            #region *** 重複となる列を削除 ***
            // 氏名 ※重複
            if (dtSmile.Columns.Contains(EmployeeBaseItemDuplicate.NAME))
            {
                dtSmile.Columns.Remove(EmployeeBaseItemDuplicate.NAME);
            }
            // 略称 ※重複
            if (dtSmile.Columns.Contains(EmployeeBaseItemDuplicate.NAME_SIMPLE))
            {
                dtSmile.Columns.Remove(EmployeeBaseItemDuplicate.NAME_SIMPLE);
            }
            // フリガナ ※重複
            if (dtSmile.Columns.Contains(EmployeeBaseItemDuplicate.NAME_FURIGANA))
            {
                dtSmile.Columns.Remove(EmployeeBaseItemDuplicate.NAME_FURIGANA);
            }
            // ローマ字名 ※重複
            if (dtSmile.Columns.Contains(EmployeeBaseItemDuplicate.NAME_ROMA))
            {
                dtSmile.Columns.Remove(EmployeeBaseItemDuplicate.NAME_ROMA);
            }
            #endregion *** 重複となる列を削除 ***
        }

        #endregion 外部受入-人事基本情報カテゴリ
    }

    /// <summary>
    /// 人事基本情報カテゴリ-項目定義
    /// </summary>
    public class EmployeeBaseItem
    {
        #region 人事基本情報カテゴリ-項目定義
        /// <summary>
        /// 社員コード
        /// </summary>
        public const string EMPLOYEE_CD = "001";

        /// <summary>
        /// 社員名カナ
        /// </summary>
        public const string KANA_NAME = "004";

        /// <summary>
        /// 退職年月日
        /// </summary>
        public const string RETIREMENT_DATE = "033";

        /// <summary>
        /// 組織コード
        /// </summary>
        public const string ORGANIZATION_CD = "059";

        /// <summary>
        /// 所属コード
        /// </summary>
        public const string BELONGS_CD = "060";

        /// <summary>
        /// 分類０
        /// </summary>
        public const string CLASSIFICATION_0 = "061";

        /// <summary>
        /// 分類１
        /// </summary>
        public const string CLASSIFICATION_1 = "062";

        /// <summary>
        /// 分類２
        /// </summary>
        public const string CLASSIFICATION_2 = "063";

        /// <summary>
        /// 分類３
        /// </summary>
        public const string CLASSIFICATION_3 = "064";

        /// <summary>
        /// 分類４
        /// </summary>
        public const string CLASSIFICATION_4 = "065";

        /// <summary>
        /// 分類５
        /// </summary>
        public const string CLASSIFICATION_5 = "066";

        /// <summary>
        /// 分類６
        /// </summary>
        public const string CLASSIFICATION_6 = "067";

        /// <summary>
        /// 分類７
        /// </summary>
        public const string CLASSIFICATION_7 = "068";

        /// <summary>
        /// 分類８
        /// </summary>
        public const string CLASSIFICATION_8 = "069";

        /// <summary>
        /// 分類９
        /// </summary>
        public const string CLASSIFICATION_9 = "070";

        #endregion 人事基本情報カテゴリ-項目定義
    }

    /// <summary>
    /// 人事基本情報カテゴリ-項目定義 ※重複
    /// </summary>
    public class EmployeeBaseItemDuplicate
    {
        #region 人事基本情報カテゴリ-項目定義 ※重複
        /// <summary>
        /// 氏名 ※重複
        /// </summary>
        public const string NAME = "011";

        /// <summary>
        /// 略称 ※重複
        /// </summary>
        public const string NAME_SIMPLE = "012";

        /// <summary>
        /// フリガナ ※重複
        /// </summary>
        public const string NAME_FURIGANA = "013";

        /// <summary>
        /// ローマ字名 ※重複
        /// </summary>
        public const string NAME_ROMA = "014";
        #endregion 人事基本情報カテゴリ-項目定義 ※重複
    }
}
