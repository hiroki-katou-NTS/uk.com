using System;
using System.Collections.Generic;
using System.Data;

using WSISmile.Business.Common;

namespace WSISmile.Business.Category.Accept
{
    /// <summary>
    /// 外部受入-休職情報カテゴリ
    /// </summary>
    public class EmployeeSuspension : AcceptCategoryBase
    {
        public EmployeeSuspension() { }

        /// <summary>
        /// Smile側からもらった連携データを編集
        /// </summary>
        /// <param name="dtSmile">Smile側の連携データ</param>
        public override void SmileDataEditing(DataTable dtSmile)
        {
            #region Smile側からもらった連携データを編集 : SmileDataEditing
            foreach (DataRow drSmile in dtSmile.Rows)
            {
                // 終了年月日
                if (drSmile[EmployeeSuspensionItem.END_DAY].ToString() == "99999999")
                {
                    drSmile[EmployeeSuspensionItem.END_DAY] = new DateTime(9999, 12, 31).ToString(Format.Date);
                }

                /*
                 * 【Trimが必要な項目】 8桁未満の場合に、後ろスペースで埋めている
                 * 社員CD
                 * 理由コード
                */

                // 社員コード
                drSmile[EmployeeSuspensionItem.EMPLOYEE_CD] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeSuspensionItem.EMPLOYEE_CD]);

                // 理由コード
                drSmile[EmployeeSuspensionItem.REASON_CD] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeSuspensionItem.REASON_CD]);
            }
            #endregion
        }
    }

    /// <summary>
    /// 休職情報カテゴリ-項目定義
    /// </summary>
    public class EmployeeSuspensionItem
    {
        #region 休職情報カテゴリ-項目定義
        /// <summary>
        /// 社員コード
        /// </summary>
        public const string EMPLOYEE_CD = "001";

        /// <summary>
        /// 開始年月日
        /// </summary>
        public const string START_DAY = "002";

        /// <summary>
        /// 終了年月日
        /// </summary>
        public const string END_DAY = "005";

        /// <summary>
        /// 理由コード
        /// </summary>
        public const string REASON_CD = "008";

        /// <summary>
        /// 休職理由
        /// </summary>
        public const string REASON = "009";
        #endregion 休職情報カテゴリ-項目定義
    }
}
