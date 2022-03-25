using System;
using System.Collections.Generic;
using System.Data;

using WSISmile.Business.Common;

namespace WSISmile.Business.Category.Accept
{
    /// <summary>
    /// 外部受入-職制情報カテゴリ
    /// </summary>
    public class EmployeeInfo : AcceptCategoryBase
    {
        public EmployeeInfo() { }

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
                if (drSmile[EmployeeInfoItem.END_DAY].ToString() == "99999999")
                {
                    drSmile[EmployeeInfoItem.END_DAY] = new DateTime(9999, 12, 31).ToString(Format.Date);
                }

                /*
                 * 【Trimが必要な項目】 8桁未満の場合に、後ろスペースで埋めている
                 * 社員CD
                 * 組織コード、所属コード
                 * 分類０～分類９
                */

                // 社員コード
                drSmile[EmployeeInfoItem.EMPLOYEE_CD] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeInfoItem.EMPLOYEE_CD]);

                // 組織コード
                drSmile[EmployeeInfoItem.ORGANIZATION_CD] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeInfoItem.ORGANIZATION_CD]);

                // 所属コード
                drSmile[EmployeeInfoItem.BELONGS_CD] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeInfoItem.BELONGS_CD]);

                // 分類０
                drSmile[EmployeeInfoItem.CLASSIFICATION_0] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeInfoItem.CLASSIFICATION_0]);

                // 分類１
                drSmile[EmployeeInfoItem.CLASSIFICATION_1] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeInfoItem.CLASSIFICATION_1]);

                // 分類２
                drSmile[EmployeeInfoItem.CLASSIFICATION_2] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeInfoItem.CLASSIFICATION_2]);

                // 分類３
                drSmile[EmployeeInfoItem.CLASSIFICATION_3] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeInfoItem.CLASSIFICATION_3]);

                // 分類４
                drSmile[EmployeeInfoItem.CLASSIFICATION_4] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeInfoItem.CLASSIFICATION_4]);

                // 分類５
                drSmile[EmployeeInfoItem.CLASSIFICATION_5] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeInfoItem.CLASSIFICATION_5]);

                // 分類６
                drSmile[EmployeeInfoItem.CLASSIFICATION_6] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeInfoItem.CLASSIFICATION_6]);

                // 分類７
                drSmile[EmployeeInfoItem.CLASSIFICATION_7] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeInfoItem.CLASSIFICATION_7]);

                // 分類８
                drSmile[EmployeeInfoItem.CLASSIFICATION_8] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeInfoItem.CLASSIFICATION_8]);

                // 分類９
                drSmile[EmployeeInfoItem.CLASSIFICATION_9] = Toolbox.RemoveInvalidSymbol(drSmile[EmployeeInfoItem.CLASSIFICATION_9]);
            }
            #endregion
        }
    }

    /// <summary>
    /// 職制情報カテゴリ-項目定義
    /// </summary>
    public class EmployeeInfoItem
    {
        #region 職制情報カテゴリ-項目定義
        /// <summary>
        /// 社員コード
        /// </summary>
        public const string EMPLOYEE_CD = "001";

        /// <summary>
        /// 発令年月日
        /// </summary>
        public const string ANNOUNCE_DAY = "002";

        /// <summary>
        /// 終了年月日
        /// </summary>
        public const string END_DAY = "004";

        /// <summary>
        /// 組織コード
        /// </summary>
        public const string ORGANIZATION_CD = "007";

        /// <summary>
        /// 所属コード
        /// </summary>
        public const string BELONGS_CD = "040";

        /// <summary>
        /// 分類０
        /// </summary>
        public const string CLASSIFICATION_0 = "008";

        /// <summary>
        /// 分類１
        /// </summary>
        public const string CLASSIFICATION_1 = "009";

        /// <summary>
        /// 分類２
        /// </summary>
        public const string CLASSIFICATION_2 = "010";

        /// <summary>
        /// 分類３
        /// </summary>
        public const string CLASSIFICATION_3 = "011";

        /// <summary>
        /// 分類４
        /// </summary>
        public const string CLASSIFICATION_4 = "012";

        /// <summary>
        /// 分類５
        /// </summary>
        public const string CLASSIFICATION_5 = "013";

        /// <summary>
        /// 分類６
        /// </summary>
        public const string CLASSIFICATION_6 = "014";

        /// <summary>
        /// 分類７
        /// </summary>
        public const string CLASSIFICATION_7 = "015";

        /// <summary>
        /// 分類８
        /// </summary>
        public const string CLASSIFICATION_8 = "016";

        /// <summary>
        /// 分類９
        /// </summary>
        public const string CLASSIFICATION_9 = "017";

        #endregion 職制情報カテゴリ-項目定義
    }
}
